package ru.gb.lk_loyality.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.dto.UserDto;
import ru.gb.lk_loyality.entities.Counter;
import ru.gb.lk_loyality.entities.User;
import ru.gb.lk_loyality.repositories.UserRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<UserDto> getUserDtoById(Long id) {
        return userRepository.findById(id).map(UserDto::new);
    }

    /*
    Получение активных бонусов. Сначала смотрим в таблице bonses, если там нет, то получаем сумму движений
     */
    public Float getBalanceByIdUser(Long id) {
        Float balance = 0f;
        try {
            balance = userRepository.getById(id).getCard().getBonus().getActiveBonus();
            return balance;
        } catch (NullPointerException e) {
            balance = getCountersByUserId(id);
            return balance;
        }
    }

    /*
    Метод по Id пользователя проходит по таблице counters, и возвращает сумму бонусов у которых дата активации
    меньше текущей даты или не заполнена
     */
    private Float getCountersByUserId(Long id) {
        Float sum = 0f;
        try {
            List<Counter> counters = userRepository.getById(id).getCard().getCounters();
            for (Counter c: counters) {
                if (c.getActiveDate() == null) {
                    sum += c.getDelta();
                } else if (c.getActiveDate().before(Calendar.getInstance())) {
                    sum += c.getDelta();
                }
            }
        } catch (NullPointerException e) {
            return 0f;
        }
        return sum;
    }
}

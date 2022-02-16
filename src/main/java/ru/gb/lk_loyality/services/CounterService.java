package ru.gb.lk_loyality.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.entities.Counter;
import ru.gb.lk_loyality.repositories.CounterRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CounterService {

    private final CounterRepository repository;

    /**
     * Метод по CardId возвращает сумму бонусов у которых дата активации
     * меньше текущей даты или не заполнена
     *
     * @param cardId идентификатор карты
     * @return сумма активных бонусов пользователя
     */
    public double getSumBonusByCardId(Long cardId) {
        double sum = 0f;
        try {
            List<Counter> counters = repository.findByCardId(cardId);
            sum = counters.stream()
                    .filter(c -> c.getActiveDate() == null || c.getActiveDate().isBefore(LocalDate.now()))
                    .mapToDouble(c -> c.getDelta())
                    .sum();
        } catch (NullPointerException e) {
            return 0d;
        }
        return sum;
    }


}

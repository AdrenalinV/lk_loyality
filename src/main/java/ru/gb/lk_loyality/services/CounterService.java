package ru.gb.lk_loyality.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.dto.CounterDto;
import ru.gb.lk_loyality.dto.CounterResponseDto;
import ru.gb.lk_loyality.entities.Counter;
import ru.gb.lk_loyality.repositories.CounterRepository;
import ru.gb.lk_loyality.utils.CounterMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CounterService {

    private final CounterRepository repository;
    private final CounterMapper counterMapper;

    /**
     * Метод по CardId возвращает сумму бонусов у которых дата активации
     * меньше текущей даты или не заполнена
     *
     * @param cardId идентификатор карты
     * @return сумма активных бонусов пользователя
     */
    public double getSumBonusByCardId(Long cardId) {
        double sum;
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

    /**
     * Метод по CardId возвращает сумму бонусов у которых дата активации
     * ещё не наступила
     *
     * @param cardId идентификатор карты
     * @return сумма ожидающих активации бонусов
     */
    public double getSumNoActiveBonusByCardId(Long cardId) {
        double sum;
        try {
            List<Counter> counters = repository.findByCardId(cardId);
            sum = counters.stream()
                    .filter(c -> c.getActiveDate() != null && c.getActiveDate().isAfter(LocalDate.now()))
                    .mapToDouble(Counter::getDelta)
                    .sum();
        } catch (NullPointerException e) {
            return 0d;
        }
        return sum;
    }

    /**
     * Метод по CardId и интервалу возвращает список движений бонусов
     *
     * @param cardId Id карты
     * @param begin дата начала периода
     * @param end дата окончания периода
     * @return List ДТО движений по счёту
     */
    public List<CounterDto> getListCountersByPeriod(Long cardId, LocalDate begin, LocalDate end) {
        LocalDateTime beginDate, endDate;
        beginDate = LocalDateTime.of(begin, LocalTime.MIN);
        endDate = LocalDateTime.of(end, LocalTime.MAX);
        return repository.findByCardIdAndDeltaDateTimeBetweenOrderByDeltaDateTime(cardId, beginDate, endDate).stream()
                .map(CounterDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Метод добавления счётчиков в базу
     * @param counterResponseDtos список CounterResponseDto для добавления
     * @return список CounterResponseDto которые не удалось добавить
     */
    public List<CounterResponseDto> addCounters(List<CounterResponseDto> counterResponseDtos) {
        List<CounterResponseDto> errors = new ArrayList<>();
        for (CounterResponseDto counterDto : counterResponseDtos) {
            Counter counter = counterMapper.mapToCounter(counterDto);
            if (!isDoubleCounter(counter)) {
                repository.save(counter);
            } else {
                errors.add(counterDto);
            }
        }
        return errors;
    }

    /**
     * Метод проверки наличия счётчика в базе
     * @param counter счётчик
     * @return true или false
     */
    public boolean isDoubleCounter(Counter counter) {
        return repository.findCounterByCardIdAndDeltaAndDocumentId(counter.getCardId(), counter.getDelta(), counter.getDocumentId()).isPresent();
    }

}

package ru.gb.lk_loyality.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.gb.lk_loyality.entities.Counter;
import ru.gb.lk_loyality.repositories.CounterRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


public class CounterServiceTest {
    private final CounterRepository repository = Mockito.mock(CounterRepository.class);
    private final CounterService service = new CounterService(repository);
    @Test
    @DisplayName("Получение суммы бонусов")
    void getSumBonusByCardIdTest() {
        Mockito.when(repository.findByCardId(any(Long.class))).thenReturn(generateListCounter());

        Double expect = service.getSumBonusByCardId(1L);

        Assertions.assertEquals(expect,3.6, 0.0001);

    }

    @Test
    @DisplayName("Получение суммы не активных бонусов")
    void getSumNoActiveBonusByCardIdTest() {
        Mockito.when(repository.findByCardId(any(Long.class))).thenReturn(generateListCounter());
        double expect = service.getSumNoActiveBonusByCardId(1L);
        Assertions.assertEquals(expect, 2.2, 0.0001);
    }

    @Test
    @DisplayName("Список движений бонусов")
    void getListCountersByPeriod() {
        Mockito
                .when(repository.findByCardIdAndDeltaDateTimeBetweenOrderByDeltaDateTime(any(Long.class),any(LocalDateTime.class),any(LocalDateTime.class) ))
                .thenReturn(generateListCounter());
        Integer expect = service.getListCountersByPeriod(1L, LocalDate.parse("2022-02-01"), LocalDate.parse("2022-02-20")).size();
        Assertions.assertEquals(expect, 4);
    }

    private List<Counter> generateListCounter() {
        Counter count1 = new Counter();
        count1.setId(1L);
        count1.setCardId(1L);
        count1.setDelta(1.1);
        count1.setDeltaDateTime(LocalDateTime.of(2022,02,01,12,00));
        count1.setActiveDate(LocalDate.now().minusDays(1));
        Counter count2 = new Counter();
        count2.setId(2L);
        count2.setCardId(1L);
        count2.setDelta(2.2);
        count2.setDeltaDateTime(LocalDateTime.of(2022,02,01,12,00));
        count2.setActiveDate(LocalDate.now().plusDays(1));
        Counter count3 = new Counter();
        count3.setId(3L);
        count3.setCardId(1L);
        count3.setDelta(3.3);
        count3.setDeltaDateTime(LocalDateTime.of(2022,02,01,12,00));
        count3.setActiveDate(LocalDate.now().minusDays(1));
        Counter count4 = new Counter();
        count4.setId(4L);
        count4.setCardId(1L);
        count4.setDelta(-0.8);
        count4.setDeltaDateTime(LocalDateTime.of(2022,02,01,12,00));
        count4.setActiveDate(LocalDate.now().minusDays(1));
        List<Counter> counters = new ArrayList<>();
        counters.add(count1);
        counters.add(count2);
        counters.add(count3);
        counters.add(count4);
        return counters;
    }
}

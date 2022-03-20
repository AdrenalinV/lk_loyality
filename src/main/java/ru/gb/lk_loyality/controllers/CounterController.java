package ru.gb.lk_loyality.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gb.lk_loyality.dto.CounterDto;
import ru.gb.lk_loyality.dto.CounterResponseDto;
import ru.gb.lk_loyality.exceptions.InvalidDateException;
import ru.gb.lk_loyality.exceptions.ResourceNotFoundException;
import ru.gb.lk_loyality.services.CounterService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/counters")
@RequiredArgsConstructor
public class CounterController {

    private final CounterService counterService;

    /**
     * Возвращает историю движений бонусов за период.
     * Если период не задан, то по умолчанию возвращаются движения за последний месяц
     * @param cardId ID карты
     * @param beginDate дата начало периода
     * @param endDate дата окончания периода
     * @return Список ДТО движений
     */
    @GetMapping("/history")
    public List<CounterDto> getListCountersByPeriod(
                @RequestParam Long cardId,
                @RequestParam (defaultValue = "") String beginDate,
                @RequestParam (defaultValue = "") String endDate)
    {
        LocalDate from, to;
        if (beginDate.isEmpty() && endDate.isEmpty()) {
            from = LocalDate.now().minusMonths(1);
            to = LocalDate.now();
        } else {
            try {
                from = LocalDate.parse(beginDate);
                to = LocalDate.parse(endDate);
            } catch (DateTimeParseException e) {
                throw new InvalidDateException("Неверный формат даты. Требуется yyyy-MM-dd");
            }
        }
        return counterService.getListCountersByPeriod(cardId, from, to);
    }

    /**
     * Добавление движений бонусов под правами администратора
     * @param requestDto список CounterResponseDto для добавления
     * @return список CounterResponseDto которые не удалось добавить
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CounterResponseDto> addCounters(@RequestBody List<CounterResponseDto> requestDto) {
        try {
            return counterService.addCounters(requestDto);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("Ошибка входных данных.");
        }
    }
}

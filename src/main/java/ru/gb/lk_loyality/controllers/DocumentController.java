package ru.gb.lk_loyality.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.lk_loyality.exceptions.InvalidDateException;
import ru.gb.lk_loyality.services.DocumentService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    /**
     * Возвращает сумму покупок за выбранный период.
     * Если период не указан, то по умолчанию берётся с 1 числа текущего месяца по текущий момент.
     * @param cardId ИД карты
     * @param beginDate начало периода
     * @param endDate конец периода
     * @return сумма покупок за период
     */
    @GetMapping("/sum")
    public Double getSumDocuments(
            @RequestParam Long cardId,
            @RequestParam(defaultValue = "") String beginDate,
            @RequestParam(defaultValue = "") String endDate) {
        LocalDate from, to;
        if (beginDate.isEmpty() && endDate.isEmpty()) {
            from = LocalDate.now().withDayOfMonth(1);
            to = LocalDate.now();
        } else {
            try {
                from = LocalDate.parse(beginDate);
                to = LocalDate.parse(endDate);
            } catch (DateTimeParseException e) {
                throw new InvalidDateException("Неверный формат даты. Требуется yyyy-MM-dd");
            }
        }
        return documentService.getSumAmount(cardId, from, to);
    }
}

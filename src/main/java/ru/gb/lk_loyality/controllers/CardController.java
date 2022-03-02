package ru.gb.lk_loyality.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.lk_loyality.dto.CardDto;
import ru.gb.lk_loyality.exceptions.ResourceNotFoundException;
import ru.gb.lk_loyality.services.CardService;


@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    /**
     * Возвращает ДТО карты по номеру
     * @param cardNumber номер карты
     * @return ДТО карты
     */
    @GetMapping("/{cardNumber}")
    public CardDto getCardByNumber(@PathVariable(name = "cardNumber") Integer cardNumber) {
        return cardService.getCardDtoByNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Карта с номером = %d не найдена.", cardNumber))
                );
    }

    /**
     * Обновляет количество активных бонусов и записывает в соответствующее поле в таблице
     * @param cardNumber номер карты
     * @return количество активных бонусов
     */
    @GetMapping("/balance/{cardNumber}")
    public Double getBalanceByNumber(@PathVariable(name = "cardNumber") Integer cardNumber) {
        return cardService.updateActiveBonusByCardNumber(cardNumber);
    }

    /**
     * Возвращает количество ещё не активных бонусов
     * @param cardNumber номер карты
     * @return количество бонусов, дата активации которых ещё не наступила
     */
    @GetMapping("/noactivebalance/{cardNumber}")
    public Double getNoActiveBalanceByNumber(@PathVariable(name = "cardNumber") Integer cardNumber) {
        return cardService.getNoActiveBonusByCardNumber(cardNumber);
    }
}

package ru.gb.lk_loyality.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gb.lk_loyality.dto.CardDto;
import ru.gb.lk_loyality.exceptions.ResourceNotFoundException;
import ru.gb.lk_loyality.services.CardService;

import java.util.List;

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

    /**
     * Добавляет карты в бд
     * @param requestDto список ДТО карт
     * @return список не добавленных карт
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CardDto> addCards(@RequestBody List<CardDto> requestDto) {
        return cardService.addCards(requestDto);
    }
}

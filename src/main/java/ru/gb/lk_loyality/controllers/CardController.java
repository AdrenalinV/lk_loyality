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


    @GetMapping("/{cardNumber}")
    public CardDto getCardByNumber(@PathVariable(name = "cardNumber") Integer cardNumber){
        CardDto cardDto = cardService.getCardDtoByNumber(cardNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Карта с номером = %d не найдена.", cardNumber))
                );
        return cardDto;
    }
    @GetMapping("/balance/{cardNumber}")
    public Double getBalanceByNumber(@PathVariable(name = "cardNumber") Integer cardNumber){
        Double balance = cardService.updateActiveBonusByCardNumber(cardNumber);
        return balance;
    }



}

package ru.gb.lk_loyality.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.entities.Card;
import ru.gb.lk_loyality.repositories.CardRepository;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    public Card getCardByNumber(Integer cardNumber) {
        return cardRepository.findCardByCardNumber(cardNumber);
    }
}

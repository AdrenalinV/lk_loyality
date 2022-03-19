package ru.gb.lk_loyality.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.dto.CardDto;
import ru.gb.lk_loyality.entities.Card;
import ru.gb.lk_loyality.repositories.CardRepository;
import ru.gb.lk_loyality.utils.QRGenerator;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository repository;
    private final CounterService counterService;


    public Optional<Card> getCardByNumber(Integer cardNumber) {
        return repository.findCardByCardNumber(cardNumber);
    }

    /**
     * Получение активных бонусов по номеру карты.
     *
     * @param cardNumber номер карты
     * @return сумма бонусов пользователя
     */
    public double getBalanceByNumber(Integer cardNumber) {
        AtomicReference<Double> balance = new AtomicReference<>((double) 0f);
        repository.findCardByCardNumber(cardNumber).ifPresent((card) -> balance.set(card.getActiveBonus()));
        return balance.get();
    }

    /**
     * Получение Dto объекта по номеру карты
     *
     * @param cardNumber номер карты
     * @return dto карты
     */
    public Optional<CardDto> getCardDtoByNumber(Integer cardNumber) {
        return repository.findCardByCardNumber(cardNumber).map(CardDto::new);
    }

    /**
     * метод обновления бонусов
     *
     * @param cardNumber номер карты
     * @return обновленный баланс
     */
    public Double updateActiveBonusByCardNumber(Integer cardNumber) {
        Card tmpCard = repository.findCardByCardNumber(cardNumber).orElseThrow(() -> new IllegalArgumentException(""));
        Double newBonus = counterService.getSumBonusByCardId(tmpCard.getId());
        tmpCard.setActiveBonus(newBonus);
        repository.save(tmpCard);
        return newBonus;
    }

    /**
     * метод создание новой карты
     *
     * @return возвращает созданный объект
     */
    public Card createCard() {
        Integer cardNumber = repository.lastCardNumber();
        cardNumber = cardNumber == null ? 1000001 : cardNumber + 1;
        Card newCard = new Card();
        newCard.setIsUsed(true);
        newCard.setQrCode(QRGenerator.generateQR(Integer.toString(cardNumber)));
        newCard.setCardNumber(cardNumber);
        newCard.setActiveBonus(0.0);
        return repository.save(newCard);
    }

    /**
     * метод получения неактивных бонусов
     *
     * @param cardNumber номер карты
     * @return количество неактивных бонусов
     */
    public Double getNoActiveBonusByCardNumber(Integer cardNumber) {
        Card tmpCard = repository.findCardByCardNumber(cardNumber).orElseThrow(() -> new IllegalArgumentException(""));
        return counterService.getSumNoActiveBonusByCardId(tmpCard.getId());
    }
}

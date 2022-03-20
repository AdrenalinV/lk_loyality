package ru.gb.lk_loyality.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.gb.lk_loyality.dto.CardDto;
import ru.gb.lk_loyality.entities.Card;
import ru.gb.lk_loyality.entities.Counter;
import ru.gb.lk_loyality.repositories.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class CardServiceTest {
    private final CardRepository repository = Mockito.mock(CardRepository.class);
//    private final CounterService counterService = Mockito.mock(CounterService.class);
    private final CardService service = new CardService(repository);

    @Test
    @DisplayName("Проверка возврата баланса по номеру карты")
    void getBalanceByNumberTest(){

        Mockito.when(repository.findCardByCardNumber(any(Integer.class))).thenReturn(Optional.of(generateCard()));
        Double expect = service.getBalanceByNumber(123456);

        Assertions.assertEquals(expect,12.45);
        Mockito.verify(repository).findCardByCardNumber(eq(123456));
    }

    @Test
    @DisplayName("Получение Card по номеру карты")
    void getCardByNumber(){
        Mockito.when(repository.findCardByCardNumber(any(Integer.class))).thenReturn(Optional.of(generateCard()));

        Card expect = service.getCardByNumber (123456).get();

        Assertions.assertEquals(expect,generateCard());
        Mockito.verify(repository).findCardByCardNumber(eq(123456));
    }

    @Test
    @DisplayName("Получение CardDto по номеру карты")
    void getCardDtoByNumber(){
        Mockito.when(repository.findCardByCardNumber(any(Integer.class))).thenReturn(Optional.of(generateCard()));

        Optional<CardDto> expect = service.getCardDtoByNumber(123456);
        Optional<CardDto> actual = Optional.of(new CardDto(generateCard()));

        Assertions.assertEquals(expect,actual);
        Mockito.verify(repository).findCardByCardNumber(eq(123456));
    }

    @Test
    @DisplayName("Обновление баланса по карте")
    void updateBonusByCardNumber(){
        Mockito.when(repository.findCardByCardNumber(any(Integer.class))).thenReturn(Optional.of(generateCard()));
        Mockito.when(repository.getSumBonusByCardId(any(Long.class))).thenReturn(1234.56);

        Double expect = service.updateActiveBonusByCardNumber(123456);


        Assertions.assertEquals(expect,1234.56);
        Mockito.verify(repository).findCardByCardNumber(eq(123456));
        Mockito.verify(repository).getSumBonusByCardId(eq(123L));
        Mockito.verify(repository).save(any(Card.class));
    }

    @Test
    @DisplayName("Проверка создания новой карты")
    void createCardTest(){
        Mockito.when(repository.lastCardNumber()).thenReturn(123456);
        Mockito.when(repository.save(any(Card.class))).thenReturn(generateCard());

        service.createCard();
        Mockito.verify(repository).lastCardNumber();
        Mockito.verify(repository).save(any(Card.class));

    }

    private Card generateCard(){
        Card card = new Card();
        List<Counter> counters = new ArrayList<>();
        card.setCardNumber(123456);
        card.setQrCode("qwerty");
        card.setIsUsed(true);
        card.setActiveBonus(12.45);
        card.setId(123L);
        card.setCounters(counters);
        return card;
    }
}

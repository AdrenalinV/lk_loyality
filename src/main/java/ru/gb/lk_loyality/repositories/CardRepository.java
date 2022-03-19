package ru.gb.lk_loyality.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.lk_loyality.entities.Card;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card>     findCardByCardNumber(Integer cardNumber);
    @Query(value="Select cardnumber From cards ORDER BY cardnumber DESC LIMIT 1"
            , nativeQuery = true)
    Integer lastCardNumber();

    @Query(value="Select cardnumber From cards"
            , nativeQuery = true)
    List<Integer> findAllCardsNumbers();

    @Query(value="select sum(delta) from counters where card_id = ?1 AND (active_date is null OR CURRENT_DATE > active_date )"
            , nativeQuery = true)
    Double getSumBonusByCardId(Long cardId);

    @Query(value="select sum(delta) from counters where card_id = ?1 AND active_date > CURRENT_DATE"
            , nativeQuery = true)
    Double getSumNoActiveBonusByCardId(Long cardId);
}

package ru.gb.lk_loyality.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.lk_loyality.entities.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findCardByCardNumber(Integer cardNumber);
}

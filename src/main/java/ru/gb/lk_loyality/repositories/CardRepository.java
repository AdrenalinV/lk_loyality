package ru.gb.lk_loyality.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.lk_loyality.entities.Card;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card>     findCardByCardNumber(Integer cardNumber);
}

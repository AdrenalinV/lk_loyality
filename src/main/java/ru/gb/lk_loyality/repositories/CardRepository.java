package ru.gb.lk_loyality.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.lk_loyality.entities.Card;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card>     findCardByCardNumber(Integer cardNumber);
    @Query(value="Select cardnumber From cards ORDER BY cardnumber DESC LIMIT 1"
            , nativeQuery = true)
    Integer lastCardNumber();
}

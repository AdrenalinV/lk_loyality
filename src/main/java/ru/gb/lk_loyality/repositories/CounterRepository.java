package ru.gb.lk_loyality.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.lk_loyality.entities.Counter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {
    List<Counter> findByCardId(Long card_id);
    List<Counter> findByCardIdAndDeltaDateTimeBetweenOrderByDeltaDateTime(Long card_id, LocalDateTime beginDate, LocalDateTime endDate);
    Optional<Counter> findCounterByCardIdAndDeltaAndDocumentId(Long cardId, Double delta, Long documentId);
}

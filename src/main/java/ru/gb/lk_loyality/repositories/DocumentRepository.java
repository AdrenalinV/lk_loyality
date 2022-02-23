package ru.gb.lk_loyality.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.lk_loyality.entities.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByCardIdAndDateTimeBetween(Long cardId, LocalDateTime beginDate, LocalDateTime endDate);
}

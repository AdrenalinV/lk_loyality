package ru.gb.lk_loyality.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.lk_loyality.entities.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByCardIdAndDateTimeBetween(Long cardId, LocalDateTime beginDate, LocalDateTime endDate);
    @Query(value="select number from documents"
            , nativeQuery = true)
    List<Integer> findAllDocumentsNumbers();
    Optional<Document> findDocumentByNumber(Integer number);
}

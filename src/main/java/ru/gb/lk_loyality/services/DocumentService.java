package ru.gb.lk_loyality.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.dto.CardDto;
import ru.gb.lk_loyality.dto.DocumentDto;
import ru.gb.lk_loyality.entities.Document;
import ru.gb.lk_loyality.repositories.DocumentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    /**
     * Сумма покупок за выбранный период.
     *
     * @param cardId номер карты
     * @param begin  начало периода
     * @param end    конец периода
     * @return сумма покупок пользователя в текущем месяце
     */
    public double getSumAmount(Long cardId, LocalDate begin, LocalDate end) {
        LocalDateTime beginDate, endDate;
        beginDate = LocalDateTime.of(begin, LocalTime.parse("00:00:00"));
        endDate = LocalDateTime.of(end, LocalTime.parse("23:59:59"));
        List<Document> documents = documentRepository.findAllByCardIdAndDateTimeBetween(
                cardId, beginDate, endDate);
        return documents.stream()
                .mapToDouble(Document::getSum)
                .sum();
    }

    public double getSumByCardId(Long cardId) {
        List<Document> documents = documentRepository.findAllByCardId(cardId);
        return documents.stream()
                .mapToDouble(Document::getSum)
                .sum();
    }

    public List<DocumentDto> getDocumentsDtoAmount(Long cardId, LocalDate begin, LocalDate end) {
        LocalDateTime beginDate, endDate;
        beginDate = LocalDateTime.of(begin, LocalTime.parse("00:00:00"));
        endDate = LocalDateTime.of(end, LocalTime.parse("23:59:59"));
        return documentRepository.findAllByCardIdAndDateTimeBetween(
                        cardId, beginDate, endDate).stream()
                .map(DocumentDto::new)
                .collect(Collectors.toList());
    }
}

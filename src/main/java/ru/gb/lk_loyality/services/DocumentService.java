package ru.gb.lk_loyality.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.dto.DocumentResponseDto;
import ru.gb.lk_loyality.entities.Document;
import ru.gb.lk_loyality.repositories.DocumentRepository;
import ru.gb.lk_loyality.utils.DocumentMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    /**
     * Сумма покупок за выбранный период.
     * @param cardId номер карты
     * @param begin начало периода
     * @param end конец периода
     * @return сумма покупок пользователя в текущем месяце
     */
    public double getSumAmount(Long cardId, LocalDate begin, LocalDate end) {
        LocalDateTime beginDate, endDate;
        beginDate = LocalDateTime.of(begin, LocalTime.MIN);
        endDate = LocalDateTime.of(end, LocalTime.MAX);
        List<Document> documents = documentRepository.findAllByCardIdAndDateTimeBetween(
                cardId, beginDate, endDate);
        return documents.stream()
                .mapToDouble(Document::getSum)
                .sum();
    }

    /**
     * Метод добавления документов в базу
     * @param documentResponseDtos список DocumentResponseDto
     * @return список DocumentResponseDto которые не удалось добавить
     */
    public List<DocumentResponseDto> addDocuments(List<DocumentResponseDto> documentResponseDtos) {
        List<DocumentResponseDto> errors = new ArrayList<>();
        List<Integer> documentsNumbers = documentRepository.findAllDocumentsNumbers();
        for (DocumentResponseDto docDto : documentResponseDtos) {
            if (!documentsNumbers.contains(docDto.getNumber())) {
                documentRepository.save(documentMapper.mapToDocument(docDto));
            } else {
                errors.add(docDto);
            }
        }
        return errors;
    }

    /**
     * Метод поиска документа по номеру
     * @param number номер документа
     * @return Optional документа
     */
    public Optional<Document> getDocumentByNumber(Integer number) {
        return documentRepository.findDocumentByNumber(number);
    }
}

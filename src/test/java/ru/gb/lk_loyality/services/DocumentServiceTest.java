package ru.gb.lk_loyality.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.gb.lk_loyality.entities.Document;
import ru.gb.lk_loyality.repositories.DocumentRepository;
import ru.gb.lk_loyality.utils.DocumentMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class DocumentServiceTest {

    private final DocumentRepository repository = Mockito.mock(DocumentRepository.class);;
    private final DocumentMapper mapper = Mockito.mock(DocumentMapper.class);
    private final DocumentService service = new DocumentService(repository, mapper);

    @Test
    @DisplayName("Сумма покупок за выбранный период")
    void getSumAmountTest() {
        Mockito
                .doReturn(generateListDocument())
                .when(repository)
                .findAllByCardIdAndDateTimeBetween(any(Long.class),any(LocalDateTime.class),any(LocalDateTime.class));
        double expect = service.getSumAmount(1L,LocalDate.parse("2022-02-01"),LocalDate.parse("2022-02-20"));
        Assertions.assertEquals(expect, 4700);
    }

    private List<Document> generateListDocument() {
        Document document1 = new Document();
        document1.setId(1L);
        document1.setNumber(1);
        document1.setDateTime(LocalDateTime.of(2022,02,01,12,00));
        document1.setSum(1000.0);
        document1.setCardId(1L);
        Document document2 = new Document();
        document2.setId(2L);
        document2.setNumber(2);
        document2.setDateTime(LocalDateTime.of(2022,02,01,12,00));
        document2.setSum(1500.0);
        document2.setCardId(1L);
        Document document3 = new Document();
        document3.setId(3L);
        document3.setNumber(3);
        document3.setDateTime(LocalDateTime.of(2022,02,01,12,00));
        document3.setSum(2000.0);
        document3.setCardId(1L);
        Document document4 = new Document();
        document4.setId(4L);
        document4.setNumber(4);
        document4.setDateTime(LocalDateTime.of(2022,02,01,12,00));
        document4.setSum(200.0);
        document4.setCardId(1L);
        Document document5 = new Document();
        document5.setId(5L);
        document5.setNumber(5);
        document5.setDateTime(LocalDateTime.now());
        document5.setSum(100.0);
        document5.setCardId(1L);
        List<Document> documents = new ArrayList<>();
        documents.add(document1);
        documents.add(document2);
        documents.add(document3);
        documents.add(document4);
        return documents;
    }
}

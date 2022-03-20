package ru.gb.lk_loyality.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.lk_loyality.dto.DocumentResponseDto;
import ru.gb.lk_loyality.entities.Document;
import ru.gb.lk_loyality.services.CardService;

@Service
@RequiredArgsConstructor
public class DocumentMapper {
    private final CardService cardService;

    public Document mapToDocument(DocumentResponseDto documentResponseDto) {
        Document document = new Document();
        document.setNumber(documentResponseDto.getNumber());
        document.setDateTime(documentResponseDto.getDateTime());
        document.setSum(documentResponseDto.getSum());
        document.setCardId(cardService.getCardByNumber(documentResponseDto.getCardNumber()).get().getId());
        return document;
    }
}

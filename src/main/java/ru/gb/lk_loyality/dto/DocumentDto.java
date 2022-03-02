package ru.gb.lk_loyality.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.lk_loyality.entities.Document;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class DocumentDto {
    private Integer number;
    private LocalDateTime dateTime;
    private Double sum;
    private Long cardId;

    public DocumentDto(Document document) {
        this.number = document.getNumber();
        this.dateTime = document.getDateTime();
        this.sum = document.getSum();
        this.cardId = document.getCardId();
    }
}

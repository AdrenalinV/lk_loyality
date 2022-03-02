package ru.gb.lk_loyality.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.lk_loyality.entities.Card;

@Data
@NoArgsConstructor

public class CardDto {
    private Integer cardNumber;
    private String qrCode;
    private Boolean isUsed;
    private Double activeBonus;

    public CardDto(Card card) {
        this.cardNumber = card.getCardNumber();
        this.qrCode = card.getQrCode();
        this.isUsed = card.getIsUsed();
        this.activeBonus = card.getActiveBonus();
    }
}

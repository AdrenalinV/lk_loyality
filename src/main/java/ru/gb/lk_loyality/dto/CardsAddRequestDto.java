package ru.gb.lk_loyality.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardsAddRequestDto {
    private Integer cardNumber;
    private String qrCode;

}

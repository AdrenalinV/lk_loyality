package ru.gb.lk_loyality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DocumentResponseDto {
    private Integer number;
    private LocalDateTime dateTime;
    private Double sum;
    private Integer cardNumber;
}

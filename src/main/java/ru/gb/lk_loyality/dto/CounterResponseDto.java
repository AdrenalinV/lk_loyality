package ru.gb.lk_loyality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CounterResponseDto {
    private Integer cardNumber;
    private Double delta;
    private LocalDateTime deltaDateTime;
    private LocalDate activeDate;
    private Integer documentNumber;
}

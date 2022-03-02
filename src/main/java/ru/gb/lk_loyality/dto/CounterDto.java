package ru.gb.lk_loyality.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.lk_loyality.entities.Counter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CounterDto {
    private Long cardId;
    private Double delta;
    private LocalDateTime deltaDateTime;
    private LocalDate activeDate;

    public CounterDto(Counter counter) {
        this.cardId = counter.getCardId();
        this.delta = counter.getDelta();
        this.deltaDateTime = counter.getDeltaDateTime();
        this.activeDate = counter.getActiveDate();
    }
}

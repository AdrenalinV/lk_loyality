package ru.gb.lk_loyality.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "counters")
@Data
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "delta")
    private Double delta;

    @Column(name = "delta_date_time")
    private LocalDateTime deltaDateTime;

    @Column(name = "active_date")
    private LocalDate activeDate;

    @Column(name = "document_id")
    private Long documentId;
}

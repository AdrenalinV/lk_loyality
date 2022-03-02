package ru.gb.lk_loyality.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "documents")
@Data
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "card_id")
    private Long cardId;
}

package ru.gb.lk_loyality.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "counters")
@Data
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "delta")
    private Float delta;

    @Column(name = "delta_date_time")
    private Calendar deltaDateTime;

    @Column(name = "active_date")
    private Calendar activeDate;
}

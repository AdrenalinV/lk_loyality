package ru.gb.lk_loyality.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name ="bonuses")
@Data
public class Bonus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "activebonus")
    private Float activeBonus;
}

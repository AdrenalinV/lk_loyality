package ru.gb.lk_loyality.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cards")
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cardnumber")
    private Integer cardNumber;

    @Column(name = "qrcode")
    private String qrCode;

    @Column(name = "used")
    private Boolean isUsed;

    @Column(name = "activebonus")
    private Double activeBonus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="card_id", referencedColumnName = "id")
    private List<Counter> counters;


}

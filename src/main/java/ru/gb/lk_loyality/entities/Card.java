package ru.gb.lk_loyality.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name= "cards")
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

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Bonus bonus;

    @OneToMany(mappedBy = "card")
    private List<Counter> counters;

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;
}

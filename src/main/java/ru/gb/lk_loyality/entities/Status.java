package ru.gb.lk_loyality.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "statuses")
@Data
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;
}

package com.gadjetmart.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="ORDER_NUMBERS")
public class OrderNumbers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUMBER")
    private int number;
}

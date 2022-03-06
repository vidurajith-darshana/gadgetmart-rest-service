package com.gadjetmart.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="SUPPLIERS")
public class Suppliers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "API")
    private String api;

    @Column(name = "STATE")
    private int state;
}

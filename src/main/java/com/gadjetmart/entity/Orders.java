package com.gadjetmart.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="ORDERS")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FK_USER")
    private User fkUser;

    @Column(name = "ORDER_REF")
    private String orderRef;

    @Column(name = "TOTAL_PRICE")
    private double totalPrice;

    @Column(name = "SHOPS")
    private String shops;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE_TIME")
    private Date createDateTime = new Date();
}

package com.gadjetmart.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="ORDER_DETAIL")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FK_ORDERS")
    private Orders fkOrders;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "ITEM_IMAGE")
    private String itemImage;

    @Column(name = "SUBTOTAL")
    private double subtotal;

    @Column(name = "DELIVERY_LOCATION")
    private String deliveryLocation;

    @Column(name = "WARRANTY_REQUEST")
    private int warrantyRequest;

    @Column(name = "QTY")
    private int qty;

    @Column(name = "SHOP_NAME")
    private String shopName;
}

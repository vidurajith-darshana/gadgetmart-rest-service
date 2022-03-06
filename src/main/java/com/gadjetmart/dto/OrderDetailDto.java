package com.gadjetmart.dto;

import lombok.Data;

@Data
public class OrderDetailDto {

    private String itemName;
    private double subtotal;
    private String itemImage;
    private String shopName;
    private String deliveryLocation;
    private int warrantyRequest;
    private int qty;
}

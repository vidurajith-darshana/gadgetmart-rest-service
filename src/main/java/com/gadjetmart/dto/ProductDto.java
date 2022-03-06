package com.gadjetmart.dto;

import lombok.Data;

@Data
public class ProductDto {

    private String name;
    private String description;
    private String category;
    private String brand;
    private double price;
    private String[] images;
}

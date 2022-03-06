package com.gadjetmart.dto;

import lombok.Data;

@Data
public class SingerRequestDto {
    private String itemName;
    private String itemBrand;
    private String itemDetails;
    private double itemPrice;
    private String[] itemImage;
    private String itemCategory;
}

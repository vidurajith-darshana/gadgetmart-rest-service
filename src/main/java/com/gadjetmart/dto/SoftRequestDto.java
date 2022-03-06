package com.gadjetmart.dto;

import lombok.Data;

@Data
public class SoftRequestDto {
    private String prodName;
    private String prodBrand;
    private String prodDetails;
    private double prodPrice;
    private String[] prodImage;
    private String prodCategory;
}

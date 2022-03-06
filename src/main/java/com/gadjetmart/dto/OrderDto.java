package com.gadjetmart.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private String orderRef;
    private UserDto userDto;
    private double totalPrice;
    private String[] shops;
    private String createDateTime;
    private List<OrderDetailDto> details;
}

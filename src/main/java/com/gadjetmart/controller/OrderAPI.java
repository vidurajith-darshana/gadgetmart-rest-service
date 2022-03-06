package com.gadjetmart.controller;

import com.gadjetmart.dto.OrderDto;
import com.gadjetmart.dto.ResponseDto;
import com.gadjetmart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order/api/v1")
public class OrderAPI {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody OrderDto orderDto){
        return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.OK);
    }

    @GetMapping("/get/{email:.+}")
    public ResponseEntity<ResponseDto> getOrders(@PathVariable("email") String email){
        return new ResponseEntity<>(orderService.getOrdersByUser(email), HttpStatus.OK);
    }

    @GetMapping("/get/{startDate}/{endDate}")
    public ResponseEntity<ResponseDto> getOrders(@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) throws Exception{
        return new ResponseEntity<>(orderService.getOrders(startDate,endDate), HttpStatus.OK);
    }
}

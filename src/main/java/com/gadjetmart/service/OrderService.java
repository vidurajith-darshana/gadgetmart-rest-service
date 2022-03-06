package com.gadjetmart.service;

import com.gadjetmart.dto.OrderDetailDto;
import com.gadjetmart.dto.OrderDto;
import com.gadjetmart.dto.ResponseDto;
import com.gadjetmart.dto.UserDto;
import com.gadjetmart.entity.OrderDetail;
import com.gadjetmart.entity.OrderNumbers;
import com.gadjetmart.entity.Orders;
import com.gadjetmart.entity.User;
import com.gadjetmart.repository.AuthRepository;
import com.gadjetmart.repository.OrderDetailRepository;
import com.gadjetmart.repository.OrderNumberRepository;
import com.gadjetmart.repository.OrderRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderNumberRepository orderNumberRepository;

    private DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Transactional(rollbackFor = {Exception.class})
    public ResponseDto createOrder(OrderDto orderDto) {

        User user = authRepository.findFirstByEmail(orderDto.getUserDto().getEmail());

        OrderNumbers orderNumbers = orderNumberRepository.findLastNumber();
        if (orderNumbers == null) {
            orderNumbers = new OrderNumbers();
            orderNumbers.setNumber(2000);
        } else {
            orderNumbers.setNumber(orderNumbers.getNumber()+1);
        }
        orderNumberRepository.saveAndFlush(orderNumbers);

        Orders orders = new Orders();
        orders.setOrderRef("#"+orderNumbers.getNumber());
        orders.setFkUser(user);
        orders.setShops(new Gson().toJson(orderDto.getShops()));
        orders.setTotalPrice(orderDto.getTotalPrice());

        orders = orderRepository.save(orders);

        for (OrderDetailDto dto : orderDto.getDetails()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setFkOrders(orders);
            orderDetail.setDeliveryLocation(dto.getDeliveryLocation());
            orderDetail.setItemImage(dto.getItemImage());
            orderDetail.setItemName(dto.getItemName());
            orderDetail.setQty(dto.getQty());
            orderDetail.setShopName(dto.getShopName());
            orderDetail.setSubtotal(dto.getSubtotal());
            orderDetail.setWarrantyRequest(dto.getWarrantyRequest());

            orderDetailRepository.save(orderDetail);
        }

        return new ResponseDto("","200","success");
    }

    public ResponseDto getOrdersByUser(String email) {

        User user = authRepository.findFirstByEmail(email);
        return checkOrders(orderRepository.findAllByFkUser(user));
    }

    public ResponseDto getOrders(String startDate, String endDate) throws Exception{
        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);
        return checkOrders(orderRepository.findOrders(start,end));
    }

    private ResponseDto checkOrders(List<Orders> orderList) {

        List<OrderDto> list = new ArrayList<>();
        for (Orders order: orderList) {

            List<OrderDetailDto> details = new ArrayList<>();

            OrderDto orderDto = new OrderDto();
            orderDto.setOrderRef(order.getOrderRef());
            orderDto.setUserDto(convertUserToUserDto(order.getFkUser()));
            orderDto.setCreateDateTime(dateTimeFormat.format(order.getCreateDateTime()));
            orderDto.setShops(new Gson().fromJson(order.getShops(),String[].class));
            orderDto.setTotalPrice(order.getTotalPrice());

            List<OrderDetail> detailList = orderDetailRepository.findAllByFkOrders(order);
            for (OrderDetail detail: detailList) {
                OrderDetailDto dto = new OrderDetailDto();
                dto.setQty(detail.getQty());
                dto.setItemImage(detail.getItemImage());
                dto.setItemName(detail.getItemName());
                dto.setSubtotal(detail.getSubtotal());
                dto.setDeliveryLocation(detail.getDeliveryLocation());
                dto.setShopName(detail.getShopName());
                dto.setWarrantyRequest(detail.getWarrantyRequest());

                details.add(dto);
            }
            orderDto.setDetails(details);
            list.add(orderDto);
        }

        return new ResponseDto("","200",list);
    }

    private UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setAddress(user.getAddress());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        return userDto;
    }
}

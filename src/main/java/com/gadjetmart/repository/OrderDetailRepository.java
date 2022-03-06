package com.gadjetmart.repository;

import com.gadjetmart.entity.OrderDetail;
import com.gadjetmart.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

    List<OrderDetail> findAllByFkOrders(Orders orders);
}

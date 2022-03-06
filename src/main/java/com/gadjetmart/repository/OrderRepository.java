package com.gadjetmart.repository;

import com.gadjetmart.entity.Orders;
import com.gadjetmart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM ORDERS WHERE CREATE_DATE_TIME BETWEEN :startDate AND :endDate")
    List<Orders> findOrders(Date startDate, Date endDate);
    List<Orders> findAllByFkUser(User user);
}

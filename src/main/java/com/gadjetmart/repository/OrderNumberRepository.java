package com.gadjetmart.repository;

import com.gadjetmart.entity.OrderNumbers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderNumberRepository extends JpaRepository<OrderNumbers,Long> {

    @Query(nativeQuery = true,value = "SELECT * FROM ORDER_NUMBERS ORDER BY ID DESC LIMIT 1")
    public OrderNumbers findLastNumber();
}

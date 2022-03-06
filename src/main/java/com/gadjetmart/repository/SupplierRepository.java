package com.gadjetmart.repository;

import com.gadjetmart.entity.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Suppliers,Long> {

    Suppliers findAllById(long id);
}

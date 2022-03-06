package com.gadjetmart.controller;

import com.gadjetmart.dto.ResponseDto;
import com.gadjetmart.dto.SupplierDto;
import com.gadjetmart.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company/api/v1")
public class CompanyAPI {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/products")
    public ResponseEntity<ResponseDto> getAllProducts(){
        return new ResponseEntity<>(companyService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/suppliers")
    public ResponseEntity<ResponseDto> getSuppliers(){
        return new ResponseEntity<>(companyService.getSuppliers(), HttpStatus.OK);
    }

    @PostMapping("/editSuppliers")
    public ResponseEntity<ResponseDto> editSuppliers(@RequestBody SupplierDto supplierDto){
        return new ResponseEntity<>(companyService.editSuppliers(supplierDto), HttpStatus.OK);
    }
}

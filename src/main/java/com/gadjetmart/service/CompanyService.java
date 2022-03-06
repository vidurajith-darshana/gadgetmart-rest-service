package com.gadjetmart.service;

import com.gadjetmart.dto.*;
import com.gadjetmart.entity.Suppliers;
import com.gadjetmart.repository.SupplierRepository;
import com.gadjetmart.service.soap.ProductClient;
import com.gadjetmart.wsdl.GetAllProductsResponse;
import com.gadjetmart.wsdl.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.io.IOException;
import java.util.*;

@Service
public class CompanyService extends WebServiceGatewaySupport {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private SupplierRepository supplierRepository;

    public ResponseDto getAllProducts() {

        // product map as will be the root
        Map<String,Object> productMap = new HashMap<>();

        List<ProductDto> abansList = new ArrayList<>();
        List<ProductDto> singerList = new ArrayList<>();
        List<ProductDto> softlogicList = new ArrayList<>();

        List<Suppliers> suppliers = supplierRepository.findAll();

        // get abans list
        GetAllProductsResponse abansProducts = productClient.getAllAbansProducts(suppliers.get(0).getApi());
        if (abansProducts != null && suppliers.get(0).getState() == 1) {
            for (Product product: abansProducts.getProduct()) {
                ProductDto productDto = new ProductDto();
                productDto.setName(product.getName());
                productDto.setBrand(product.getBrand());
                productDto.setCategory(product.getCategory());
                productDto.setDescription(product.getDescription());
                productDto.setPrice(Double.parseDouble(product.getPrice()));
                productDto.setImages(new String[]{product.getImage()});

                abansList.add(productDto);
            }
        }
        productMap.put(suppliers.get(0).getName(),abansList);

        // initiate client
        OkHttpClient client = new OkHttpClient();

        // get singer list
        Request singerRequest = new Request.Builder()
                .url(suppliers.get(2).getApi())
                .build();

        Call singerCall = client.newCall(singerRequest);
        try {
            Response response = singerCall.execute();
            if (response.isSuccessful() && suppliers.get(2).getState() == 1) {
                String json = Objects.requireNonNull(response.body()).string();
                Gson gson = new Gson();
                List<SingerRequestDto> list = gson.fromJson(json,new TypeToken<List<SingerRequestDto>>(){}.getType());
                for (SingerRequestDto dto : list) {
                    ProductDto productDto = new ProductDto();
                    productDto.setName(dto.getItemName());
                    productDto.setPrice(dto.getItemPrice());
                    productDto.setDescription(dto.getItemDetails());
                    productDto.setCategory(dto.getItemCategory());
                    productDto.setBrand(dto.getItemBrand());
                    productDto.setImages(dto.getItemImage());

                    singerList.add(productDto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        productMap.put(suppliers.get(2).getName(),singerList);

        // get softlogic list
        Request softRequest = new Request.Builder()
                .url(suppliers.get(1).getApi())
                .build();

        Call softCall = client.newCall(softRequest);
        try {
            Response response = softCall.execute();
            if (response.isSuccessful() && suppliers.get(1).getState() == 1) {
                String json = Objects.requireNonNull(response.body()).string();
                Gson gson = new Gson();
                List<SoftRequestDto> list = gson.fromJson(json,new TypeToken<List<SoftRequestDto>>(){}.getType());
                for (SoftRequestDto dto : list) {
                    ProductDto productDto = new ProductDto();
                    productDto.setName(dto.getProdName());
                    productDto.setPrice(dto.getProdPrice());
                    productDto.setDescription(dto.getProdDetails());
                    productDto.setCategory(dto.getProdCategory());
                    productDto.setBrand(dto.getProdBrand());
                    productDto.setImages(dto.getProdImage());

                    softlogicList.add(productDto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        productMap.put(suppliers.get(1).getName(),softlogicList);

        return new ResponseDto("success","200",productMap);
    }

    public ResponseDto getSuppliers() {
        List<Suppliers> suppliers = supplierRepository.findAll();
        List<SupplierDto> list = new ArrayList<>();
        for (Suppliers sup: suppliers) {
            SupplierDto supplierDto = new SupplierDto();
            supplierDto.setId(sup.getId());
            supplierDto.setName(sup.getName());
            supplierDto.setUrl(sup.getApi());
            supplierDto.setState(sup.getState()); // add

            list.add(supplierDto);
        }
        return new ResponseDto("","200",list);
    }

    public ResponseDto editSuppliers(SupplierDto supplierDto) {
        Suppliers suppliers = supplierRepository.findAllById(supplierDto.getId());
        if (supplierDto.getName() != null) suppliers.setName(supplierDto.getName());
        if (supplierDto.getUrl() != null) suppliers.setApi(supplierDto.getUrl());
        if (supplierDto.getState() != 0) suppliers.setState(supplierDto.getState());
        supplierRepository.save(suppliers);

        return new ResponseDto("","200","success");
    }
}

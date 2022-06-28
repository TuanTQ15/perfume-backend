package com.example.ecommercespring.service;

import com.example.ecommercespring.dto.ShippingCompanyDTO;
import com.example.ecommercespring.respone.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShippingCompanyService {
    public List<ShippingCompanyDTO> getAll();
    public ResponseEntity<?> getById(Long id);
    public Response addNew(ShippingCompanyDTO shippingCompanyDTO);
    public Response modify(ShippingCompanyDTO shippingCompanyDTO);
    public Response delete(Long id);
}

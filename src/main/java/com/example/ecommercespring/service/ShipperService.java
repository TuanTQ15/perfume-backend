package com.example.ecommercespring.service;

import com.example.ecommercespring.dto.ShipperDTO;
import com.example.ecommercespring.respone.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShipperService {
    public List<ShipperDTO> getAll();
    public ResponseEntity<?> getById(Long id);
    public Response addNew(ShipperDTO shippingCompanyDTO);
    public Response modify(ShipperDTO shipperDTO);
    public Response delete(Long id);
}

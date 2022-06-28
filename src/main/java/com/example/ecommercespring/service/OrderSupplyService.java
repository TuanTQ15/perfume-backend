package com.example.ecommercespring.service;

import com.example.ecommercespring.dto.OrderSupplyDTO;
import com.example.ecommercespring.respone.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderSupplyService {
    public List<OrderSupplyDTO> getAll();
    public ResponseEntity<?> getById(Long id);
    public Response addNew(OrderSupplyDTO orderSupplyDTO);
    public Response modify(OrderSupplyDTO orderSupplyDTO,boolean updateStatus);
}

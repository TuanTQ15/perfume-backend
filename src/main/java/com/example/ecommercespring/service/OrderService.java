package com.example.ecommercespring.service;

import com.example.ecommercespring.dto.OrderUserDTO;
import com.example.ecommercespring.respone.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    public List<OrderUserDTO> getAll(Integer Status);
    public List<OrderUserDTO> getAllByUser(Long id,Integer status);
    public Response addNew(OrderUserDTO orderUserDTO);
    public Response checkQuantityForOrder(OrderUserDTO orderUserDTO);
    public Response modify(OrderUserDTO orderUserDTO,boolean isUpdateStatus);
    public ResponseEntity<?> getOrderById(Long id);
}

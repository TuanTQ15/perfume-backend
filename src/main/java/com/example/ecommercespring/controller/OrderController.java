package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.OrderUserDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.security.services.UserDetailsImpl;
import com.example.ecommercespring.service.OrderService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/order")
    public List<OrderUserDTO> Get(@RequestParam("status") Integer status) {
        return orderService.getAll(status);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> GetOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/orderUser")
    public List<OrderUserDTO> getListByUser(@RequestParam("status") Integer status) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderService.getAllByUser(userDetails.getUserId(),status);
    }

    @PostMapping("/order")
    public Response Post(@RequestBody OrderUserDTO orderUserDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderUserDTO.setCustomerId(userDetails.getUserId());
        return orderService.addNew(orderUserDTO);
    }

    @PutMapping("/order")
    public Response Put(@RequestBody OrderUserDTO orderUserDTO, @RequestParam(value = "isUpdateStatus") boolean isUpdateStatus) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderUserDTO.setEmployeeId(userDetails.getUserId());
        return orderService.modify(orderUserDTO, isUpdateStatus);
    }

    @PostMapping("/checkQuantity")
    public Response checkQuantity(OrderUserDTO orderUserDTO) {
        return orderService.checkQuantityForOrder(orderUserDTO);
    }
}

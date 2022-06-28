package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.OrderUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderUser, Long> {
    List<OrderUser> findAllByCustomer_Id(Long id);
}
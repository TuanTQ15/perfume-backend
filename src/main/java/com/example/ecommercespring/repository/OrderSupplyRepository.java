package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.OrderSupply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSupplyRepository extends JpaRepository<OrderSupply, Long> {
}
package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
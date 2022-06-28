package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.DetailPromotion;
import com.example.ecommercespring.key.DetailPromotionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailPromotionRepository extends JpaRepository<DetailPromotion, DetailPromotionKey> {
}

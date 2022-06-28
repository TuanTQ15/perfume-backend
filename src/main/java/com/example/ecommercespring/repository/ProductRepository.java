package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findAllByBrand(Long id);
}
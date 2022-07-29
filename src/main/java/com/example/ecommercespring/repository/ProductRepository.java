package com.example.ecommercespring.repository;

import com.example.ecommercespring.dto.DataSet;
import com.example.ecommercespring.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findAllByBrand(Long id);


}
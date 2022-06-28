package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.DetailReceipt;
import com.example.ecommercespring.key.DetailReceiptKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailReceiptRepository extends JpaRepository<DetailReceipt, DetailReceiptKey> {
    List<DetailReceipt> findById_ProductId(Long productId);
}
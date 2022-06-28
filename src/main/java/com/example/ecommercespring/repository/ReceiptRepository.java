package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
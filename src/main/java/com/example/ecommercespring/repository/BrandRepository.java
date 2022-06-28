package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByPhoneNumberAndEmail(String phoneNumber, String email);

    Brand findByPhoneNumber(String phoneNumber);

    Brand findByEmail(String email);
}
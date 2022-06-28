package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.ShippingCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingCompanyRepository extends JpaRepository<ShippingCompany, Long> {
    ShippingCompany findByPhoneNumberAndEmail(String phoneNumber, String email);

    ShippingCompany findByPhoneNumber(String phoneNumber);

    ShippingCompany findByEmail(String email);
}
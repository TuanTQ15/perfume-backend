package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.DetailOS;
import com.example.ecommercespring.key.DetailOSKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailOSRepository extends JpaRepository<DetailOS, DetailOSKey> {
}

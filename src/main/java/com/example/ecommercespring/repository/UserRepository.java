package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumberAndEmail(String phoneNumber, String email);
    Optional<Object> findByEmail(String email);
    List<User> findAllByRole_Id(Long id);

//    User findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);

}
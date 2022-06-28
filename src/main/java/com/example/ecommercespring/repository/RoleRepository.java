package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.Role;
import com.example.ecommercespring.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(RoleName role);
}

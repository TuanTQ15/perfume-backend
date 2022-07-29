package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.DetailOrder;
import com.example.ecommercespring.key.DetailOrderKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface DetailOrderRepository extends JpaRepository<DetailOrder, DetailOrderKey> {
    List<DetailOrder> findByOrderUser_BookingDateBetweenAndOrderUser_StatusIsLessThanEqual(Date bookingDateStart, Date bookingDateEnd, Integer status);
    @Query(value = "SELECT * FROM detail_order d WHERE  d.product_id = ?1",
            nativeQuery = true)
    List<DetailOrder> findDetailOrder(Long id);
}

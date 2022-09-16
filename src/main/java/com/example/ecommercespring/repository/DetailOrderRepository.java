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
    @Query(value = "SELECT COUNT(*) FROM detail_order d, order_user o\n" +
            "WHERE  d.product_id =?1 and d.order_id= o.order_id\n" +
            "and o.booking_date >= ?2 \n" +
            "and o.booking_date <= ?3 ",
            nativeQuery = true)
    Integer countNumberDetailOrderByProductId(Long id,Date promotionStart, Date promotionEnd);
}

package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByDateCreatedBetween(Date dateCreatedStart, Date dateCreatedEnd);

    List<Invoice> findByDateCreatedBetweenAndOrderUser_StatusIsLessThanEqual(Date dateCreatedStart, Date dateCreatedEnd, Integer status);

}
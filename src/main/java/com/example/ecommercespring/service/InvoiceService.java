package com.example.ecommercespring.service;

import com.example.ecommercespring.dto.InvoiceDTO;
import com.example.ecommercespring.respone.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InvoiceService {
    public List<InvoiceDTO> getAll();
    public Response addNew(InvoiceDTO invoiceDTO);
//    public ResponseEntity<?> getStatistic(Integer year);
}

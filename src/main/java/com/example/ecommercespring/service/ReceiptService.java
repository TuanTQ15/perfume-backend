package com.example.ecommercespring.service;

import com.example.ecommercespring.dto.ReceiptDTO;
import com.example.ecommercespring.respone.Response;

import java.util.List;

public interface ReceiptService {
    public Response addNew(ReceiptDTO receiptDTO);
    public ReceiptDTO getById(Long id);
    public List<ReceiptDTO> getAll();
}

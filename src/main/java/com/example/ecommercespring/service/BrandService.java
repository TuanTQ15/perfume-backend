package com.example.ecommercespring.service;

import com.example.ecommercespring.dto.BrandDTO;
import com.example.ecommercespring.respone.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BrandService {
    public List<BrandDTO> getAll();

    public ResponseEntity<?> getById(Long id);
    /* public ResponseEntity<BrandDTO> getById(Long id); */

    public Response addNew(BrandDTO brandDTO);

    public Response modify(BrandDTO brandDTO);

    public Response delete(Long id);
}

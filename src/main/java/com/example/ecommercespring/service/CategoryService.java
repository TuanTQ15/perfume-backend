package com.example.ecommercespring.service;

import com.example.ecommercespring.dto.CategoryDTO;
import com.example.ecommercespring.respone.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    public List<CategoryDTO> getAll();
    public Response addNew(CategoryDTO categoryDTO);
    public Response modify(CategoryDTO categoryDTO);
    public  Response delete (Long id);
    public ResponseEntity<?> getById(Long id);
}

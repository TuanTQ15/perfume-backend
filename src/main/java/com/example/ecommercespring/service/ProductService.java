package com.example.ecommercespring.service;

import com.example.ecommercespring.dto.ProductDTO;
import com.example.ecommercespring.respone.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    public List<ProductDTO> getAll();
    public List<ProductDTO> getAllDiscount();
    public List<ProductDTO> getAllNewProduct();
    public List<ProductDTO> getAllHotSell();
    public ResponseEntity<?> getByid(Long id);
    public List<ProductDTO> getByBrand(Long brandId);
    public Response addNew(ProductDTO productDTO);
    public Response modify(ProductDTO productDTO);
    public Response delete(Long id);
}

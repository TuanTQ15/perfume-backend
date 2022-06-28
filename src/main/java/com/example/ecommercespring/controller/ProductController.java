package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.ProductDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/product")
    public List<ProductDTO> Get() {
        return productService.getAll();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> Get(@PathVariable("id") Long id) {
        return productService.getByid(id);
    }

    @GetMapping("/product/brand")
    public List<ProductDTO> getProductByBrand(@RequestParam("brandId") Long brandId) {
        return productService.getByBrand(brandId);
    }

    @PostMapping("/product")
    public Response Post(@RequestBody ProductDTO productDTO) {
        return productService.addNew(productDTO);
    }

    @PutMapping("/product")
    public Response Put(@RequestBody ProductDTO productDTO) {
        return productService.modify(productDTO);
    }

    @DeleteMapping("/product/{id}")
    public Response Delete(@PathVariable("id") Long id) {
        return productService.delete(id);
    }

    @GetMapping("/discount")
    public List<ProductDTO> GetDiscount() {
        return productService.getAllDiscount();
    }

    @GetMapping("/newProduct")
    public List<ProductDTO> GetNewProduct() {
        return productService.getAllNewProduct();
    }

    @GetMapping("/hotsell")
    public List<ProductDTO> getHotSellProduct() {
        return productService.getAllHotSell();
    }
}

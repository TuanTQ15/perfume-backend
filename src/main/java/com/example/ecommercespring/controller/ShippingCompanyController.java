package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.BrandDTO;
import com.example.ecommercespring.dto.ShippingCompanyDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.BrandService;
import com.example.ecommercespring.service.ShippingCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sc")
public class ShippingCompanyController {

    @Autowired
    ShippingCompanyService shippingCompanyService;

    @GetMapping
    public List<ShippingCompanyDTO> Get(){
        return shippingCompanyService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> Get(@PathVariable("id") Long id){
        return shippingCompanyService.getById(id);
    }

    @PostMapping
    public Response Post(@RequestBody ShippingCompanyDTO shippingCompanyDTO){
        return shippingCompanyService.addNew(shippingCompanyDTO);
    }

    @PutMapping
    public Response Put(@RequestBody ShippingCompanyDTO shippingCompanyDTO){
        return shippingCompanyService.modify(shippingCompanyDTO);
    }
    @DeleteMapping("/{id}")
    public Response Delete(@PathVariable("id") Long id){
          return shippingCompanyService.delete(id);
    }
}

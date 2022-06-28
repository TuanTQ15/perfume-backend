package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.BrandDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @GetMapping
    public List<BrandDTO> Get(){
        return brandService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> Get(@PathVariable("id") Long id){
        return brandService.getById(id);
    }

    @PostMapping
    public Response Post(@RequestBody BrandDTO brandDTO){
        return brandService.addNew(brandDTO);
    }

    @PutMapping
    public Response Put(@RequestBody BrandDTO brandDTO){
        return brandService.modify(brandDTO);
    }
    @DeleteMapping("/{id}")
    public Response Delete(@PathVariable("id") Long id){
        return brandService.delete(id);
    }
}

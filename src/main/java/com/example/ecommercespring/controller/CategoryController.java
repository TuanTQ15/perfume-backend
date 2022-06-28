package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.CategoryDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> Get(){
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return categoryService.getById(id);
    }

    @PostMapping
    public Response Post(@RequestBody CategoryDTO categoryDTO){
        return categoryService.addNew(categoryDTO);
    }
    @PutMapping
    public Response Put(@RequestBody CategoryDTO categoryDTO){
        return categoryService.modify(categoryDTO);
    }
    @DeleteMapping("/{id}")
    public Response Delete(@PathVariable("id") Long id){
        return categoryService.delete(id);
    }
}

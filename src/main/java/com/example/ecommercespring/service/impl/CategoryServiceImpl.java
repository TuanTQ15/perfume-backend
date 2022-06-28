package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.CategoryDTO;
import com.example.ecommercespring.entity.Category;
import com.example.ecommercespring.repository.CategoryRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAll(){
        return categoryRepository.findAll().stream().map(CategoryDTO:: new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getById(Long id){
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            return ResponseEntity.badRequest().body(new Response(false,"Danh mục không tồn tại"));
        }
        return ResponseEntity.ok(new CategoryDTO(category));
    }

    @Override
    public Response addNew(CategoryDTO categoryDTO){
        categoryRepository.save(categoryDTO.toEntity());
        return new Response(true,"Thêm danh mục thành công");
    }

    @Override
    public Response modify(CategoryDTO categoryDTO){
        Category category = categoryRepository.findById(categoryDTO.getCategoryId()).orElse(null);
        if(category == null){
            return new Response(true,"Danh mục không tồn tại");
        }
        category.setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(category);
        return new Response(true,"Sửa danh mục thành công");
    }

    @Override
    public  Response delete (Long id){
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            return new Response(true,"Danh mục không tồn tại");
        }
        if(category.getProductList().size() > 0){
            return new Response(true,"Danh mục đã tồn tại sản phẩm không thể xóa");
        }
        categoryRepository.deleteById(id);
        return new Response(true,"Xóa danh mục thành công");
    }
}

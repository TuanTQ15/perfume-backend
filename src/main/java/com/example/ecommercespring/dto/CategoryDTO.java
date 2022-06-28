package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    private String categoryName;

    public CategoryDTO (Category category){
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
    }

    public Category toEntity(){
        Category category = new Category();
        category.setCategoryName(this.categoryName);
        return  category;
    }

}

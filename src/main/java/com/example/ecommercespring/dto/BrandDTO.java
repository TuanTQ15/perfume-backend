package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private Long brandId ;
    private String brandName ;
    private String email ;
    private String phoneNumber ;
    private String address ;

    public BrandDTO(Brand brand){
        this.brandId = brand.getBrandId();
        this.brandName = brand.getBrandName();
        this.email = brand.getEmail();
        this.phoneNumber = brand.getPhoneNumber();
        this.address = brand.getAddress();
    }

    public Brand toEntity(){
        return new Brand(this.getBrandId(),this.getBrandName(),this.getEmail(),this.getPhoneNumber(),this.getAddress());
    }
}

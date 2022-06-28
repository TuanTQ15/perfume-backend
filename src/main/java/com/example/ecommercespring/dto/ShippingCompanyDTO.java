package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.ShippingCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingCompanyDTO {

    private Long shippingCompanyId;
    private String name ;
    private String address ;
    private String phoneNumber ;
    private String email ;

    public ShippingCompanyDTO(ShippingCompany shippingCompany){
        this.shippingCompanyId = shippingCompany.getShippingCompanyId();
        this.name = shippingCompany.getName();
        this.address = shippingCompany.getAddress();
        this.phoneNumber = shippingCompany.getPhoneNumber();
        this.email = shippingCompany.getEmail();
    }

    public ShippingCompany toEntity(){
        ShippingCompany shippingCompany = new ShippingCompany();
        shippingCompany.setName(this.getName());
        shippingCompany.setAddress(this.getAddress());
        shippingCompany.setPhoneNumber(this.getPhoneNumber());
        shippingCompany.setEmail(this.getEmail());
        return shippingCompany;
    }
}

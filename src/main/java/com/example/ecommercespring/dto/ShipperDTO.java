package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.Shipper;
import com.example.ecommercespring.entity.ShippingCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipperDTO {
    private Long shipperId ;
    private String fullName ;
    private String email ;
    private String phoneNumber ;
    private Date dateOfBirth ;
    private Long shippingCompanyId;
//    private ShippingCompanyDTO shippingCompany;

    public ShipperDTO(Shipper shipper){
        this.shipperId = shipper.getShipperId();
        this.fullName = shipper.getFullName();
        this.dateOfBirth = shipper.getDateOfBirth();
        this.phoneNumber = shipper.getPhoneNumber();
        this.email = shipper.getEmail();
        this.shippingCompanyId = shipper.getShippingCompany().getShippingCompanyId();
    }

    public Shipper toEntity(){
        Shipper shipper = new Shipper();
        shipper.setFullName(this.getFullName());
        shipper.setDateOfBirth(this.getDateOfBirth());
        shipper.setPhoneNumber(this.getPhoneNumber());
        shipper.setEmail(this.getEmail());
        return shipper;
    }
}

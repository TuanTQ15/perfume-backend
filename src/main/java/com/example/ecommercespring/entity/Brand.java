package com.example.ecommercespring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId ;
    private String brandName ;
    private String email ;
    private String phoneNumber ;
    private String address ;

    @OneToMany(mappedBy = "brand",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy = "brand",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderSupply> orderSupplyList = new ArrayList<>();


    public Brand(Long brandId, String brandName, String email, String phoneNumber, String address) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}

package com.example.ecommercespring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shippingCompany")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shippingCompanyId;
    private String name ;
    private String address ;
    private String phoneNumber ;
    private String email ;

    @OneToMany(mappedBy = "shippingCompany",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shipper> shipperList = new ArrayList<>();

}

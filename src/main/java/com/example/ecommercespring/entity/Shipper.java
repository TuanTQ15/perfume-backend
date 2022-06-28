package com.example.ecommercespring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipperId ;
    private String fullName ;
    private String email ;
    private String phoneNumber ;
    private Date dateOfBirth ;

    @ManyToOne
    @JoinColumn(name= "shippingCompanyId")
    private ShippingCompany shippingCompany;

    @OneToMany(mappedBy = "shipper")
    private List<Invoice> invoiceList = new ArrayList<>();
}

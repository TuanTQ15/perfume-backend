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
public class OrderUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId ;
    private String fullName ;
    private String phoneNumber ;
    private String address ;
    private Date bookingDate ;
    private Date deliveryDate ;
    private Integer status ;
    private String note ;
    private String transactionId ;

    @ManyToMany
    @JoinTable(
            name = "DetailOrder",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "productId"))
    List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy = "orderUser",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailOrder> detailOrders = new ArrayList<>();

    @OneToOne(mappedBy = "orderUser",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @PrimaryKeyJoinColumn
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private User employee;
}

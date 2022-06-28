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
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptId;
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private User user;

    @ManyToMany
    @JoinTable(name = "DetailReceipt",
            joinColumns = @JoinColumn(name = "receiptId"),
            inverseJoinColumns = @JoinColumn(name = "productId"))
    private List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy = "receipt",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailReceipt> detailReceiptList = new ArrayList<>();

    @OneToOne
//    @MapsId
    @JoinColumn(name = "orderSupplyId")
    private OrderSupply orderSupply;
}

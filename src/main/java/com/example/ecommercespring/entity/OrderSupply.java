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
public class OrderSupply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderSupplyId;
    private Date bookingDate;
    private Integer status;
    private Date receivedDate;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @ManyToMany
    @JoinTable(name = "DetailOS",
            joinColumns = @JoinColumn(name = "orderSupplyId"),
            inverseJoinColumns = @JoinColumn(name = "productId"))
    private List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy = "orderSupply",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailOS> detailOSList = new ArrayList<>();

    @OneToOne(mappedBy = "orderSupply",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Receipt receipt;

}

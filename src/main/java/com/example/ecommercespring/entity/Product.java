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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String description;
    private String image;
    private Integer price;
    private Integer quantityInStock;
    private Integer sex;
    private String origin;



    @ManyToMany
    @JoinTable(name = "DetailOrder",
            joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "orderId"))
    private List<OrderUser> orderUserList = new ArrayList<>();

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailOrder> detailOrders = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "DetailPromotion",
            joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "promotionId"))
    private List<Promotion> promotionList = new ArrayList<>();

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailPromotion> detailPromotionList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @ManyToMany
    @JoinTable(name = "DetailOS",
            joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "orderSupplyId"))
    private List<OrderSupply> orderSupplyList = new ArrayList<>();

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailOS> detailOSList = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "DetailReceipt",
            joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "receiptId"))
    private List<Receipt> receiptList = new ArrayList<>();

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailReceipt> detailReceiptList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;


    public Product(Long productId, String productName, String description, String image, Integer price,
                   Integer quantityInStock, Integer sex, String origin) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.sex = sex;
        this.origin = origin;
    }
}

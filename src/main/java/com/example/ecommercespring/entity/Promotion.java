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
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId ;
    private String promotionName ;
    private Date startDate ;
    private Date endDate ;
    private String description;

    @ManyToMany
    @JoinTable(name = "DetailPromotion",
            joinColumns = @JoinColumn(name="promotionId"),
            inverseJoinColumns = @JoinColumn(name = "productId"))
    private List<Product> productList = new ArrayList<>();

    @OneToMany(mappedBy = "promotion",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailPromotion> detailPromotionList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private User user;
}

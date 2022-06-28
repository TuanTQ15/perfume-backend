package com.example.ecommercespring.entity;

import com.example.ecommercespring.key.DetailPromotionKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailPromotion {
    @EmbeddedId
    DetailPromotionKey id = new DetailPromotionKey();

    @ManyToOne
    @MapsId("promotionId")
    @JoinColumn(name="promotionId")
    private Promotion promotion;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="productId")
    private Product product;

    private Float percentDiscount;
}

package com.example.ecommercespring.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailPromotionKey implements Serializable {
    @JoinColumn(name = "productId")
    private Long productId;

    @JoinColumn(name = "promotionId")
    private Long promotionId;
}

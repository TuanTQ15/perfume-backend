package com.example.ecommercespring.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailOrderKey implements Serializable {
    @Column(name = "orderId")
    private Long orderId;

    @Column(name = "productId")
    private Long productId;
}

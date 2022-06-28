package com.example.ecommercespring.entity;

import com.example.ecommercespring.dto.DetailOrderDTO;
import com.example.ecommercespring.key.DetailOrderKey;
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
public class DetailOrder {
    @EmbeddedId
    DetailOrderKey id = new DetailOrderKey();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name="orderId")
    OrderUser orderUser;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId")
    Product product;


    private Integer quantity;
    private Integer price;


}

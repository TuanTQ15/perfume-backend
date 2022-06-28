package com.example.ecommercespring.entity;

import com.example.ecommercespring.key.DetailOSKey;
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
public class DetailOS {
    @EmbeddedId
    private DetailOSKey id = new DetailOSKey();

    @ManyToOne
    @MapsId("orderSupplyId")
    @JoinColumn(name="orderSupplyId")
    private OrderSupply orderSupply;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="productId")
    private Product product;

    private Integer quantity;
    private Integer price;
}

package com.example.ecommercespring.entity;

import com.example.ecommercespring.key.DetailReceiptKey;
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
public class DetailReceipt {

    @EmbeddedId
    private DetailReceiptKey id = new DetailReceiptKey();
    private Integer quantity;
    private Integer price;

    @ManyToOne
    @MapsId("receiptId")
    @JoinColumn(name = "receiptId")
    private Receipt receipt;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId")
    private Product product;

}

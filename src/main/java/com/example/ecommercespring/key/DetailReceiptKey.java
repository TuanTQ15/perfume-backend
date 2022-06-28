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
public class DetailReceiptKey implements Serializable {
    @JoinColumn(name = "receiptId")
    private Long receiptId;

    @JoinColumn(name = "productId")
    private Long productId;
}

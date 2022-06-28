package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.DetailOrder;
import com.example.ecommercespring.entity.DetailReceipt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailReceiptDTO {

    private Long receiptId;
    private Long productId;
    private Integer quantity;
    private Integer price;



    public DetailReceiptDTO(DetailReceipt detailReceipt){
        this.receiptId = detailReceipt.getReceipt().getReceiptId();
        this.productId = detailReceipt.getProduct().getProductId();
        this.quantity = detailReceipt.getQuantity();
        this.price = detailReceipt.getPrice();
    }

    public DetailReceipt toEntity(){
        DetailReceipt detailReceipt = new DetailReceipt();
        detailReceipt.setQuantity(this.getQuantity());
        detailReceipt.setPrice(this.getPrice());
        return  detailReceipt;
    }
}

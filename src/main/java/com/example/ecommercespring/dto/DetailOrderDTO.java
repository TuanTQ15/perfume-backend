package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.DetailOrder;
import com.example.ecommercespring.key.DetailOrderKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailOrderDTO {

    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Integer price;
    private String productName;
    private String origin;

    public DetailOrderDTO(DetailOrder detailOrder){
        this.orderId = detailOrder.getOrderUser().getOrderId();
        this.productId = detailOrder.getProduct().getProductId();
        this.quantity = detailOrder.getQuantity();
        this.price = detailOrder.getPrice();
        this.productName = detailOrder.getProduct().getProductName();
        this.origin = detailOrder.getProduct().getOrigin();
    }

    public DetailOrder toEntity(){
        DetailOrder detailOrder = new DetailOrder();
        detailOrder.setQuantity(this.getQuantity());
        detailOrder.setPrice(this.getPrice());
        return  detailOrder;
    }
}

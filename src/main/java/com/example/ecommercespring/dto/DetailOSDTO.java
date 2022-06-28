package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.DetailOS;
import com.example.ecommercespring.entity.DetailOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailOSDTO {

    private Long orderSupplyId;
    private Long productId;
    private Integer quantity;
    private Integer price;

    public DetailOSDTO(DetailOS detailOS){
        this.orderSupplyId = detailOS.getOrderSupply().getOrderSupplyId();
        this.productId = detailOS.getProduct().getProductId();
        this.quantity = detailOS.getQuantity();
        this.price = detailOS.getPrice();
    }

    public DetailOS toEntity(){
        DetailOS detailOS = new DetailOS();
        detailOS.setQuantity(this.getQuantity());
        detailOS.setPrice(this.getPrice());
        return  detailOS;
    }
}

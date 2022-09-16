package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.DetailPromotion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailPromotionDTO {

    private Long promotionId;
    private Long productId;
    private Float percentDiscount;
    private Date dateCreated;
    private Integer disable;
    public DetailPromotionDTO(DetailPromotion detailPromotion) {
        this.promotionId = detailPromotion.getPromotion().getPromotionId();
        this.productId = detailPromotion.getProduct().getProductId();
        this.percentDiscount = detailPromotion.getPercentDiscount();
        this.dateCreated = detailPromotion.getDateCreated();
        this.disable = detailPromotion.getDisable();
    }
    public DetailPromotion toEntity(){
        DetailPromotion detailPromotion = new DetailPromotion();
        detailPromotion.setPercentDiscount(this.percentDiscount);
        detailPromotion.setDateCreated(this.dateCreated);
        detailPromotion.setDisable(this.disable);
        return detailPromotion;
    }
}

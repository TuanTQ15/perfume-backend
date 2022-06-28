package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.Promotion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO {
    private Long promotionId;
    private String promotionName;
    private Date startDate;
    private Date endDate;
    private String description;
    private Long employeeId;
    private List<DetailPromotionDTO> detailPromotionList = new ArrayList<>();

    public PromotionDTO(Promotion promotion) {
        this.promotionId = promotion.getPromotionId();
        this.promotionName = promotion.getPromotionName();
        this.startDate = promotion.getStartDate();
        this.endDate = promotion.getEndDate();
        this.description = promotion.getDescription();
        this.employeeId = promotion.getUser().getId();
        this.detailPromotionList = promotion.getDetailPromotionList().stream().map(DetailPromotionDTO::new).collect(Collectors.toList());
    }

    public Promotion toEntity() {
        Promotion promotion = new Promotion();
        promotion.setPromotionName(this.promotionName);
        promotion.setStartDate(this.startDate);
        promotion.setEndDate(this.endDate);
        promotion.setDescription(this.description);
        return promotion;
    }
}

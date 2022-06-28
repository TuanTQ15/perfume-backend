package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.DetailOrder;
import com.example.ecommercespring.entity.DetailPromotion;
import com.example.ecommercespring.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productId;
    private String productName;
    private String description;
    private String image;
    private Integer price;
    private Integer quantityInStock;
    private Long brandId;
    private Long categoryId;
    private Integer sex;
    private String origin;

    private BrandDTO brand;
    private CategoryDTO category;
    //    private List<DetailPromotionDTO> detailPromotionList = new ArrayList<>();
    private DetailPromotionDTO detailPromotion;

    private Integer quantitySold;

    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.image = product.getImage();
        this.price = product.getPrice();
        this.quantityInStock = product.getQuantityInStock();
        this.sex = product.getSex();
        this.origin = product.getOrigin();
        this.brandId = product.getBrand().getBrandId();
        this.categoryId = product.getCategory().getCategoryId();
        this.brand = new BrandDTO(product.getBrand().getBrandId(), product.getBrand().getBrandName(), product.getBrand().getEmail(),
                product.getBrand().getPhoneNumber(), product.getBrand().getAddress());
        this.category = new CategoryDTO(product.getCategory().getCategoryId(), product.getCategory().getCategoryName());
//        this.detailPromotionList = product.getDetailPromotionList().stream().map(DetailPromotionDTO::new).collect(Collectors.toList());
        this.detailPromotion = product.getDetailPromotionList().stream()
                .filter(detailPromotion -> {
                    return detailPromotion.getPromotion().getStartDate().compareTo(new Date()) < 0
                            && detailPromotion.getPromotion().getEndDate().compareTo(new Date()) > 0;
                })
                .max(Comparator.comparing(DetailPromotion::getPercentDiscount))
                .map(DetailPromotionDTO::new).orElse(null);
//        boolean allNull = product.getDetailOrders().stream().map(DetailOrder::getQuantity).allMatch(Objects::isNull);
        try {
            Integer sum = product.getDetailOrders().stream()
                    .mapToInt(value -> value.getQuantity().intValue())
                    .sum();
            this.quantitySold = sum;
        } catch (Exception e) {
            this.quantitySold = 0;
        }

    }

    public Product toEntity() {
        return new Product(this.productId, this.productName, this.description,
                this.image, this.price, this.quantityInStock,this.sex,this.origin);
    }

}

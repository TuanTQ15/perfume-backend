package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.DetailPromotionDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.DetailPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/detailPromotion")
public class DetailPromotionController {

    @Autowired
    DetailPromotionService detailPromotionService;

    @DeleteMapping
    public Response Delete(@RequestBody DetailPromotionDTO detailPromotionDTO){
        return detailPromotionService.delete(detailPromotionDTO.getProductId(),detailPromotionDTO.getPromotionId());
    }
}

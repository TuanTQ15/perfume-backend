package com.example.ecommercespring.service;

import com.example.ecommercespring.respone.Response;

import java.util.Date;

public interface DetailPromotionService {
    public Response delete(Long id1,Long id2);
    public Response checkForDelete(Long productId, Date promotionStart, Date promotionEnd);
}

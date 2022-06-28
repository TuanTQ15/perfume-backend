package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.entity.DetailPromotion;
import com.example.ecommercespring.key.DetailPromotionKey;
import com.example.ecommercespring.repository.DetailPromotionRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.DetailPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailPromotionServiceImpl implements DetailPromotionService {

    @Autowired
    DetailPromotionRepository detailPromotionRepository;

    @Override
    public Response delete(Long productId,Long promotionId){
        DetailPromotion detailPromotion = detailPromotionRepository.
                findById(new DetailPromotionKey(productId,promotionId)).orElse(null);
        if(detailPromotion == null){
            return new Response(false,"Chi tiết khuyến mãi không tồn tại");
        }
        detailPromotionRepository.deleteById(new DetailPromotionKey(productId,promotionId));
        return new Response(true,"Xoá chi tiết khuyến mãi thành công");
    }
}

package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.entity.DetailOrder;
import com.example.ecommercespring.entity.DetailPromotion;
import com.example.ecommercespring.key.DetailPromotionKey;
import com.example.ecommercespring.repository.DetailOrderRepository;
import com.example.ecommercespring.repository.DetailPromotionRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.DetailPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DetailPromotionServiceImpl implements DetailPromotionService {

    @Autowired
    DetailPromotionRepository detailPromotionRepository;
    @Autowired
    DetailOrderRepository detailOrderRepository;
    @Override
    public Response delete(Long productId,Long promotionId){
        DetailPromotion detailPromotion = detailPromotionRepository.
                findById(new DetailPromotionKey(productId,promotionId)).orElse(null);
        Integer detailOrderCount = detailOrderRepository.countNumberDetailOrderByProductId(productId,
                detailPromotion.getPromotion().getStartDate(),  detailPromotion.getPromotion().getEndDate());
        if(detailPromotion == null){
            return new Response(false,"Chi tiết khuyến mãi không tồn tại");
        }
        if(detailOrderCount!=0){
            return  new Response(false,"Sản phẩm này đã có đơn hàng không thể xóa khuyến mãi \n Số lượng đơn hàng: "+detailOrderCount);
        }

        detailPromotionRepository.deleteById(new DetailPromotionKey(productId,promotionId));
        return new Response(true,"Xoá chi tiết khuyến mãi thành công");
    }

    @Override
    public Response checkForDelete(Long productId,Date promotionStart, Date promotionEnd) {

        Integer detailOrderCount = detailOrderRepository.countNumberDetailOrderByProductId(productId,  promotionStart,  promotionEnd);
        if(detailOrderCount != 0){
            return new Response(true,"Sản phẩm  có đơn đặt hàng bạn không thể xóa");
        }

        return new Response(false, "Sản phẩm chưa có đơn đặt hàng bạn có thể xóa");

    }
}

package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.DetailPromotionDTO;
import com.example.ecommercespring.dto.PromotionDTO;
import com.example.ecommercespring.entity.*;
import com.example.ecommercespring.key.DetailPromotionKey;
import com.example.ecommercespring.repository.DetailPromotionRepository;
import com.example.ecommercespring.repository.ProductRepository;
import com.example.ecommercespring.repository.PromotionRepository;
import com.example.ecommercespring.repository.UserRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    DetailPromotionRepository detailPromotionRepository;

    @Override
    public List<PromotionDTO> getAll() {
        return promotionRepository.findAll().stream().map(PromotionDTO::new).collect(Collectors.toList());
    }
    @Override
    public PromotionDTO getById(Long id){
        Promotion promotion = promotionRepository.findById(id).orElse(null);
        return new PromotionDTO(promotion);
    }

    @Override
    public Response addNew( PromotionDTO promotionDTO) {
        Promotion promotion = promotionDTO.toEntity();

        User user = userRepository.findById(promotionDTO.getEmployeeId()).orElse(null);
        if (user == null) {
            return new Response(false, "Mã nhân viên không tồn tại");
        }
        List<DetailPromotion> detailPromotionList = new ArrayList<>();
        for (DetailPromotionDTO detailPromotionDTO : promotionDTO.getDetailPromotionList()) {
            DetailPromotion detailPromotion = detailPromotionDTO.toEntity();
            Product product = productRepository.findById(detailPromotionDTO.getProductId()).orElse(null);
            if (product == null) {
                return new Response(false, "Sản phẩm không tồn tại");
            }
            detailPromotion.setProduct(product);
            detailPromotion.setPromotion(promotion);
            detailPromotionList.add(detailPromotion);
        }
        promotion.setDetailPromotionList(detailPromotionList);
        promotion.setUser(user);
        promotionRepository.save(promotion);
        return new Response(true, "Thêm đợt khuyến mãi thành công");
    }

    @Override
    public Response modify(PromotionDTO promotionDTO) {
        Promotion promotion = promotionRepository.findById(promotionDTO.getPromotionId()).orElse(null);
        if (promotion == null)
            return new Response(false, "Đợt khuyến mãi không tồn tại");
        if(promotion.getUser().getId() != promotionDTO.getEmployeeId()){
            User user = userRepository.findById(promotionDTO.getEmployeeId()).orElse(null);
            if (user == null) {
                return new Response(false, "Mã nhân viên không tồn tại");
            }
            promotion.setUser(user);
        }
        promotion.setStartDate(promotionDTO.getStartDate());
        promotion.setEndDate(promotionDTO.getEndDate());
        promotion.setDescription(promotionDTO.getDescription());
        promotion.setPromotionName(promotion.getPromotionName());

        for (DetailPromotionDTO detailPromotionDTO : promotionDTO.getDetailPromotionList()) {
//            if(detailPromotionDTO.getPromotionId() !=)
            DetailPromotion detailPromotion =
                    detailPromotionRepository
                            .findById(new DetailPromotionKey(detailPromotionDTO.getProductId(),promotion.getPromotionId()))
                            .orElse(null);
            if(detailPromotion == null){
                Product product = productRepository.findById(detailPromotionDTO.getProductId()).orElse(null);
                if (product == null) {
                    return new Response(false, "Sản phẩm không tồn tại");
                }
                detailPromotion = detailPromotionDTO.toEntity();
                detailPromotion.setProduct(product);
                detailPromotion.setPromotion(promotion);
                promotion.getDetailPromotionList().add(detailPromotion);
            }else {
                detailPromotion.setPercentDiscount(detailPromotionDTO.getPercentDiscount());
            }
        }
        promotionRepository.save(promotion);
        return new Response(true, "Sửa đợt khuyến mãi thành công");
    }

    @Override
    public Response delete(Long id){
        Promotion promotion = promotionRepository.findById(id).orElse(null);
        if (promotion == null)
            return new Response(false, "Đợt khuyến mãi không tồn tại");
        if(promotion.getDetailPromotionList().size() > 0)
            return new Response(false, "Đợt khuyến mãi đã có sản phẩm không thế xóa");

        promotionRepository.deleteById(id);
        return new Response(true, "Xóa đợt khuyến mãi thành công");
    }
}

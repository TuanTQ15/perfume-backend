package com.example.ecommercespring.service;

import com.example.ecommercespring.dto.PromotionDTO;
import com.example.ecommercespring.respone.Response;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PromotionService {
    public List<PromotionDTO> getAll();
    public PromotionDTO getById(Long id);
    public Response addNew(@RequestBody PromotionDTO promotionDTO);
    public Response modify(@RequestBody PromotionDTO promotionDTO);
    public Response delete(Long id);
}

package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.PromotionDTO;
import com.example.ecommercespring.dto.ShipperDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.security.services.UserDetailsImpl;
import com.example.ecommercespring.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/promotion")
public class PromotionController {

    @Autowired
    PromotionService promotionService;

    @GetMapping
    public List<PromotionDTO> Get(){
        return promotionService.getAll();
    }

    @GetMapping("/{id}")
    public PromotionDTO Get(@PathVariable("id") Long id){
        return promotionService.getById(id);
    }

    @PostMapping
    public Response Post(@RequestBody PromotionDTO promotionDTO){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        promotionDTO.setEmployeeId(userDetails.getUserId());
        return promotionService.addNew(promotionDTO);
    }

    @PutMapping
    public Response Put(@RequestBody PromotionDTO promotionDTO){
        return promotionService.modify(promotionDTO);
    }
    @DeleteMapping("/{id}")
    public Response Delete(@PathVariable("id") Long id){
        return promotionService.delete(id);
    }
}

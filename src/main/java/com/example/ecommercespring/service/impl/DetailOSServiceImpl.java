package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.DetailOSDTO;
import com.example.ecommercespring.entity.DetailOS;
import com.example.ecommercespring.key.DetailOSKey;
import com.example.ecommercespring.repository.DetailOSRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.DetailOSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailOSServiceImpl implements DetailOSService {

    @Autowired
    DetailOSRepository detailOSRepository;

    @Override
    public Response delete(DetailOSDTO detailOSDTO){
        DetailOS detailOS = detailOSRepository.
                findById(new DetailOSKey(detailOSDTO.getOrderSupplyId(),detailOSDTO.getProductId()))
                .orElse(null);
        if(detailOS == null){
            return new Response(false,"Chi tiết đơn đặt hàng từ hãng không tồn tại");
        }
        detailOSRepository.deleteById(new DetailOSKey(detailOSDTO.getOrderSupplyId(),detailOSDTO.getProductId()));
        return new Response(true,"Xóa thành công chi tiết đơn đặt hàng từ hãng");
    }
}

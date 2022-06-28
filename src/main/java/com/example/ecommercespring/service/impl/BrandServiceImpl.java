package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.BrandDTO;
import com.example.ecommercespring.entity.Brand;
import com.example.ecommercespring.repository.BrandRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandRepository brandRepository;

    @Override
    public List<BrandDTO> getAll(){
        return brandRepository.findAll().stream().map(BrandDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getById(Long id){
        Brand brand = brandRepository.findById(id).orElse(null);
        if(brand == null){
            return ResponseEntity.badRequest().body(new Response(false, "Hãng không tồn tại"));
        }
        return ResponseEntity.ok(new BrandDTO(brand));
    }

    @Override
    public Response addNew(BrandDTO brandDTO){
        if(brandDTO.getPhoneNumber() ==null || brandDTO.getEmail() ==null){
            return new Response(false,"Số điện thoại hoặc email rỗng");
        }
        Brand checkBrandPN = brandRepository.findByEmail(brandDTO.getEmail());
        if(checkBrandPN != null){
            return new Response(false,"Email đã tồn tại");
        }

        Brand checkBrand = brandRepository.findByPhoneNumber(brandDTO.getPhoneNumber());
        if(checkBrand != null){
            return new Response(false,"Số điện thoại đã tồn tại");
        }
        brandRepository.save(brandDTO.toEntity());
        return new Response(true,"Thêm hãng thành công");
    }

    @Override
    public Response modify(BrandDTO brandDTO){
        Brand brand = brandRepository.findById(brandDTO.getBrandId()).orElse(null);
        if(brand == null) return new Response(false,"Hãng không tồn tại");

        if(brandDTO.getPhoneNumber() ==null || brandDTO.getEmail() ==null){
            return new Response(false,"Số điện thoại hoặc email rỗng");
        }
        if(brandDTO.getEmail() != brand.getEmail()){
            Brand checkBrandPN = brandRepository.findByEmail(brandDTO.getEmail());
            if(checkBrandPN != null){
                return new Response(false,"Email đã tồn tại");
            }
        }
        if(brandDTO.getPhoneNumber() != brand.getPhoneNumber()){
            Brand checkBrand = brandRepository.findByPhoneNumber(brandDTO.getPhoneNumber());
            if(checkBrand != null){
                return new Response(false,"Số điện thoại đã tồn tại");
            }
        }

        brand.setBrandName(brandDTO.getBrandName());
        brand.setEmail(brandDTO.getEmail());
        brand.setPhoneNumber(brandDTO.getPhoneNumber());
        brand.setAddress(brandDTO.getAddress());
        brandRepository.save(brand);
        return new Response(true,"Sửa hãng thàng công");
    }

    @Override
    public Response delete(Long id){
        Brand brand = brandRepository.findById(id).orElse(null);
        if(brand == null) return new Response(false,"Hãng không tồn tại");
        if(brand.getProductList().size() > 0) return new Response(false,"Hãng đã có sản phẩm không thể xóa");
        if(brand.getOrderSupplyList().size() > 0) return new Response(true,"Hãng đã có đơn đặt hàng không thể xóa");
        brandRepository.deleteById(id);
        return new Response(true,"Xóa hãng thành công");
    }
}

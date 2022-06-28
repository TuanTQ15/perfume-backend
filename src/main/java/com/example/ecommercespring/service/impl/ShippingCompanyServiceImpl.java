package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.ShippingCompanyDTO;
import com.example.ecommercespring.entity.Brand;
import com.example.ecommercespring.entity.Shipper;
import com.example.ecommercespring.entity.ShippingCompany;
import com.example.ecommercespring.repository.ShippingCompanyRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.ShippingCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingCompanyServiceImpl implements ShippingCompanyService {

    @Autowired
    ShippingCompanyRepository shippingCompanyRepository;

    @Override
    public List<ShippingCompanyDTO> getAll() {
        return shippingCompanyRepository.findAll().stream().map(ShippingCompanyDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        ShippingCompany shippingCompany = shippingCompanyRepository.findById(id).orElse(null);
        if (shippingCompany == null) {
            return ResponseEntity.badRequest().body(new Response(false, "Đơn vị vận chuyển không tồn tại"));
        }
        return ResponseEntity.ok(new ShippingCompanyDTO(shippingCompany));
    }

    @Override
    public Response addNew(ShippingCompanyDTO shippingCompanyDTO) {
        if (shippingCompanyDTO.getPhoneNumber() == null || shippingCompanyDTO.getEmail() == null) {
            return new Response(false, "Số điện thoại hoặc email rỗng");
        }
        ShippingCompany checkscPN = shippingCompanyRepository.findByPhoneNumber(shippingCompanyDTO.getPhoneNumber());
        if (checkscPN != null) {
            return new Response(false, "Số điện thoại đã tồn tại");
        }

        ShippingCompany checksc = shippingCompanyRepository.findByPhoneNumber(shippingCompanyDTO.getPhoneNumber());
        if (checksc != null) {
            return new Response(false, "Email đã tồn tại");
        }

        shippingCompanyRepository.save(shippingCompanyDTO.toEntity());
        return new Response(true, "Thêm đơn vị vận chuyển thành công");
    }

    @Override
    public Response modify(ShippingCompanyDTO shippingCompanyDTO) {
        ShippingCompany shippingCompany = shippingCompanyRepository.findById(shippingCompanyDTO.getShippingCompanyId())
                .orElse(null);
        if (shippingCompany == null) {
            return new Response(false, "Đơn vị vận chuyển không tồn tại");
        }

        if (shippingCompanyDTO.getPhoneNumber() == null || shippingCompanyDTO.getEmail() == null) {
            return new Response(false, "Số điện thoại hoặc email rỗng");
        }
        if(shippingCompany.getEmail() != shippingCompany.getEmail()){
            ShippingCompany checkscPN = shippingCompanyRepository.findByPhoneNumber(shippingCompanyDTO.getPhoneNumber());
            if (checkscPN != null) {
                return new Response(false, "Số điện thoại đã tồn tại");
            }
        }
        if(shippingCompany.getPhoneNumber() != shippingCompany.getPhoneNumber()){
            ShippingCompany checksc = shippingCompanyRepository.findByPhoneNumber(shippingCompanyDTO.getPhoneNumber());
            if (checksc != null) {
                return new Response(false, "Email đã tồn tại");
            }
        }

        shippingCompany.setName(shippingCompanyDTO.getName());
        shippingCompany.setAddress(shippingCompanyDTO.getAddress());
        shippingCompany.setPhoneNumber(shippingCompanyDTO.getPhoneNumber());
        shippingCompany.setEmail(shippingCompanyDTO.getEmail());
        shippingCompanyRepository.save(shippingCompany);
        return new Response(true, "Sửa đơn vị vận chuyển thành công");
    }

    @Override
    public Response delete(Long id) {
        ShippingCompany shippingCompany = shippingCompanyRepository.findById(id).orElse(null);
        if (shippingCompany == null) return new Response(false, "Đơn vị vận chuyển không tồn tại");
        if (shippingCompany.getShipperList().size() > 0)
            return new Response(false, "Đơn vị vận chuyển đã có nhân viên giao hàng không thể xóa");
        shippingCompanyRepository.deleteById(id);
        return new Response(true, "Xóa đơn vị vận chuyển thành công");
    }
}

package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.ShipperDTO;
import com.example.ecommercespring.dto.ShippingCompanyDTO;
import com.example.ecommercespring.entity.Shipper;
import com.example.ecommercespring.entity.ShippingCompany;
import com.example.ecommercespring.entity.User;
import com.example.ecommercespring.repository.ShipperRepository;
import com.example.ecommercespring.repository.ShippingCompanyRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipperServiceImpl implements ShipperService {

    @Autowired
    ShipperRepository shipperRepository;
    @Autowired
    ShippingCompanyRepository shippingCompanyRepository;

    @Override
    public List<ShipperDTO> getAll(){
        return shipperRepository.findAll().stream().map(ShipperDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getById(Long id){
        Shipper shipper = shipperRepository.findById(id).orElse(null);
        if(shipper == null)
            return ResponseEntity.badRequest().body(new Response(false, "Nhân viên giao hàng không tồn tại"));
        return ResponseEntity.ok(new ShipperDTO(shipper));
    }

    @Override
    public Response addNew(ShipperDTO shipperDTO){
        ShippingCompany shippingCompany = shippingCompanyRepository.findById(shipperDTO.getShippingCompanyId())
                .orElse(null);
        if(shippingCompany == null)
            return new Response(false,"Đơn vị vận chuyển không tồn tại");

        if(shipperDTO.getPhoneNumber() ==null || shipperDTO.getEmail() ==null){
            return new Response(false,"Số điện thoại hoặc email rỗng");
        }

        Shipper checkShipperPN = shipperRepository.findByPhoneNumber(shipperDTO.getPhoneNumber());
        if(checkShipperPN != null){
            return new Response(false,"Số điện thoại đã tồn tại");
        }

        Shipper checkShipper = shipperRepository.findByEmail(shipperDTO.getEmail());
        if(checkShipper != null){
            return new Response(false,"Email đã tồn tại");
        }

        Shipper shipper = shipperDTO.toEntity();
        shipper.setShippingCompany(shippingCompany);
        shipperRepository.save(shipper);
        return new Response(true,"Thêm nhân viên vận chuyển thành công");
    }

    @Override
    public Response modify(ShipperDTO shipperDTO){
        Shipper shipper = shipperRepository.findById(shipperDTO.getShipperId())
                .orElse(null);
        if (shipper == null) {
            return new Response(false,"Nhân viên vận chuyển không tồn tại");
        }
        if(shipperDTO.getPhoneNumber() ==null || shipperDTO.getEmail() ==null){
            return new Response(false,"Số điện thoại hoặc email rỗng");
        }

        if(shipper.getPhoneNumber() != shipperDTO.getPhoneNumber()){
            Shipper checkShipper = shipperRepository.findByPhoneNumber(shipperDTO.getPhoneNumber());
            if(checkShipper != null){
                return new Response(false,"Số điện thoại đã tồn tại");
            }
        }

        if(shipper.getEmail() != shipperDTO.getEmail()){
            Shipper checkShipper = shipperRepository.findByEmail(shipperDTO.getEmail());
            if(checkShipper != null){
                return new Response(false,"Email đã tồn tại");
            }
        }

        if(shipperDTO.getShippingCompanyId() != shipper.getShippingCompany().getShippingCompanyId()){
            ShippingCompany shippingCompany = shippingCompanyRepository.findById(shipperDTO.getShippingCompanyId())
                    .orElse(null);
            if(shippingCompany == null)
                return new Response(false,"Đơn vị vận chuyển không tồn tại");
            shipper.setShippingCompany(shippingCompany);
        }
        shipper.setFullName(shipperDTO.getFullName());
        shipper.setDateOfBirth(shipperDTO.getDateOfBirth());
        shipper.setPhoneNumber(shipperDTO.getPhoneNumber());
        shipper.setEmail(shipperDTO.getEmail());
        shipperRepository.save(shipper);
        return new Response(true,"Sửa nhân viên vận chuyển thành công");
    }

    @Override
    public Response delete(Long id){
        Shipper shipper = shipperRepository.findById(id).orElse(null);
        if(shipper == null)
            return new Response(false,"Đơn vị vận chuyển không tồn tại");
        if(shipper.getInvoiceList().size() > 0)
            return new Response(false,"Nhân viên vận chuyển đã có giao cho 1 đơn đặt hàng không thể xóa");
        shipperRepository.deleteById(id);
        return new Response(true,"Xóa nhân viên vận chuyển thành công");
    }
}

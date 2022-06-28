package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.DetailOSDTO;
import com.example.ecommercespring.dto.OrderSupplyDTO;
import com.example.ecommercespring.entity.*;
import com.example.ecommercespring.key.DetailOSKey;
import com.example.ecommercespring.repository.*;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.OrderSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderSupplyServiceImpl implements OrderSupplyService {

    @Autowired
    OrderSupplyRepository orderSupplyRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    DetailOSRepository detailOSRepository;

    @Override
    public List<OrderSupplyDTO> getAll() {
        return orderSupplyRepository.findAll().stream().map(OrderSupplyDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        OrderSupply orderSupply = orderSupplyRepository.findById(id).orElse(null);
        if (orderSupply == null) {
            return ResponseEntity.badRequest().body(new Response(false, "Đơn đặt hàng không tồn tại"));
        }
        return ResponseEntity.ok(new OrderSupplyDTO(orderSupply));
    }

    @Override
    public Response addNew(OrderSupplyDTO orderSupplyDTO) {
        OrderSupply orderSupply = orderSupplyDTO.toEntity();

        User user = userRepository.findById(orderSupplyDTO.getEmployeeId()).orElse(null);
        if (user == null) {
            return new Response(false, "Mã nhân viên không tồn tại");
        }
        Brand brand = brandRepository.findById(orderSupplyDTO.getBrandId()).orElse(null);
        if (brand == null) {
            return new Response(false, "Mã hãng không tồn tại");
        }
        List<DetailOS> detailOSList = new ArrayList<>();
        for (DetailOSDTO detailOSDTO : orderSupplyDTO.getDetailOSList()) {
            DetailOS detailOS = detailOSDTO.toEntity();

            Product product = productRepository.findById(detailOSDTO.getProductId()).orElse(null);
            if (product == null) {
                return new Response(false, "Sản phẩm không tồn tại");
            }
            if (product.getBrand().getBrandId() != orderSupplyDTO.getBrandId()) {
                return new Response(false, "Sản phẩm " + product.getProductName() +
                        " thuộc hãng " + product.getBrand().getBrandName());
            }
            detailOS.setProduct(product);
            detailOS.setOrderSupply(orderSupply);
            detailOSList.add(detailOS);
        }
        orderSupply.setDetailOSList(detailOSList);
        orderSupply.setUser(user);
        orderSupply.setBrand(brand);
        orderSupplyRepository.save(orderSupply);
        return new Response(true, "Thêm đơn đặt hàng từ hãng thành công");
    }

    @Override
    public Response modify(OrderSupplyDTO orderSupplyDTO, boolean updateStatus) {
        OrderSupply orderSupply = orderSupplyRepository.findById(orderSupplyDTO.getOrderSupplyId()).orElse(null);
        if (orderSupply == null)
            return new Response(false, "Đơn đặt hàng từ hãng không tồn tại");
        //kiểm tra nếu khác mã thì mới đi kiểm tra
        if (orderSupply.getBrand().getBrandId() != orderSupplyDTO.getBrandId()) {
            Brand brand = brandRepository.findById(orderSupplyDTO.getBrandId()).orElse(null);
            if (brand == null) {
                return new Response(false, "Mã hãng không tồn tại");
            }
            orderSupply.setBrand(brand);
        }
        User user = userRepository.findById(orderSupplyDTO.getEmployeeId()).orElse(null);
        if (user == null) {
            return new Response(false, "Mã nhân viên không tồn tại");
        }
        orderSupply.setUser(user);

        if (updateStatus) {
            orderSupply.setStatus(orderSupplyDTO.getStatus());
        } else {
            for (DetailOSDTO detailOSDTO : orderSupplyDTO.getDetailOSList()) {
                DetailOS detailOS = detailOSRepository.findById(
                        new DetailOSKey(orderSupply.getOrderSupplyId(), detailOSDTO.getProductId())).orElse(null);
                if (detailOS == null) {
                    Product product = productRepository.findById(detailOSDTO.getProductId()).orElse(null);
                    if (product == null) {
                        return new Response(false, "Sản phẩm không tồn tại");
                    }
                    detailOS = detailOSDTO.toEntity();
                    detailOS.setProduct(product);
                    detailOS.setOrderSupply(orderSupply);
                    orderSupply.getDetailOSList().add(detailOS);
                } else {
                    detailOS.setPrice(detailOSDTO.getPrice());
                    detailOS.setQuantity(detailOSDTO.getQuantity());
                }
            }
            orderSupply.setStatus(orderSupplyDTO.getStatus());
            orderSupply.setBookingDate(orderSupplyDTO.getBookingDate());
            orderSupply.setReceivedDate(orderSupplyDTO.getReceivedDate());
        }

        orderSupplyRepository.save(orderSupply);
        return new Response(true, "Sửa đợt khuyến mãi thành công");
    }
}

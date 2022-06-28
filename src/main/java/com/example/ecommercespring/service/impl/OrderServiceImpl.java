package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.DetailOrderDTO;
import com.example.ecommercespring.dto.OrderUserDTO;
import com.example.ecommercespring.entity.*;
import com.example.ecommercespring.repository.UserRepository;
import com.example.ecommercespring.repository.OrderRepository;
import com.example.ecommercespring.repository.ProductRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
//    @Autowired
//    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<OrderUserDTO> getAll(Integer status) {
        return orderRepository.findAll().stream()
                .filter(orderUser -> orderUser.getStatus() == status)
                .map(OrderUserDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getOrderById(Long id){
        OrderUser orderUser = orderRepository.findById(id).orElse(null);
        if(orderUser ==null){
            return ResponseEntity
                    .badRequest()
                    .body(new Response(false,"Đơn đặt hàng không tồn tại"));
        }
        OrderUserDTO orderUserDTO = new OrderUserDTO(orderUser);
        return ResponseEntity.ok(orderUserDTO);
    }

    @Override
    public List<OrderUserDTO> getAllByUser(Long id,Integer status){
        return orderRepository.findAllByCustomer_Id(id).stream()
                .filter(orderUser -> orderUser.getStatus() == status)
                .map(OrderUserDTO::new).collect(Collectors.toList());
    }

    @Override
    public Response addNew(OrderUserDTO orderUserDTO) {
        OrderUser orderUser = orderUserDTO.toEntity();
        User user = userRepository.findById(orderUserDTO.getCustomerId()).orElse(null);
        if (user == null) {
            return new Response(false, "Mã khách hàng không tồn tại");
        }

        List<DetailOrder> detailOrders = orderUser.getDetailOrders();
        for (DetailOrderDTO d : orderUserDTO.getDetailOrderList()) {
            DetailOrder detailOrder = d.toEntity();
            Product product = productRepository.findById(d.getProductId()).orElse(null);
            if (product == null) {
                return new Response(false, "Sản phẩm không tồn tại");
            }
            product.setQuantityInStock(product.getQuantityInStock() - d.getQuantity());
            detailOrder.setProduct(product);
            detailOrder.setOrderUser(orderUser);
            detailOrders.add(detailOrder);
        }
        orderUser.setCustomer(user);
        orderRepository.save(orderUser);
        return new Response(true, "Tạo đơn đặt hàng thành công");
    }

    @Override
    public Response checkQuantityForOrder(OrderUserDTO orderUserDTO){
        if(orderUserDTO.getDetailOrderList() == null)
            return new Response(false,"Giỏ hàng trống vui lòng kiểm tra lại");
        for (DetailOrderDTO detailOrderDTO: orderUserDTO.getDetailOrderList()) {
            Product product = productRepository.findById(detailOrderDTO.getProductId()).orElse(null);
            if(product.getQuantityInStock() < detailOrderDTO.getQuantity()){
                return new Response(false,"Số lượng tồn của " + product.getProductName() +" chỉ còn " + product.getQuantityInStock());
            }
        }
        return new Response(true,"Đủ số lượng cho phiếu đặt");
    }

    @Override
    public Response modify(OrderUserDTO orderUserDTO, boolean isUpdateStatus) {
        OrderUser orderUser = orderRepository.findById(orderUserDTO.getOrderId()).orElse(null);
        if (orderUser == null) return new Response(false, "Mã đơn đặt hàng không tồn tại");
        // kiem tra neu khac moi find va set
        if(orderUser.getEmployee() == null || orderUser.getEmployee().getId() != orderUserDTO.getEmployeeId()){
            User user = userRepository.findById(orderUserDTO.getEmployeeId()).orElse(null);
            if (user == null) {
                return new Response(false, "Mã nhân viên không tồn tại");
            }
            orderUser.setEmployee(user);
        }
        if (isUpdateStatus) {
            orderUser.setStatus(orderUserDTO.getStatus());
            if (orderUserDTO.getStatus() == 3) {
                for (DetailOrder detailOrder : orderUser.getDetailOrders()) {
                    Product product = productRepository.findById(detailOrder.getProduct().getProductId()).orElse(null);
                    product.setQuantityInStock(product.getQuantityInStock() + detailOrder.getQuantity());
                    productRepository.save(product);
                }
            }
            orderRepository.save(orderUser);
            return new Response(true, "Sửa trạng thái của phiếu đặt thành công");
        } else {
            orderUser.setDeliveryDate(orderUserDTO.getDeliveryDate());
            orderRepository.save(orderUser);
            return new Response(true, "Sửa ngày giao thành công");
        }
    }
}

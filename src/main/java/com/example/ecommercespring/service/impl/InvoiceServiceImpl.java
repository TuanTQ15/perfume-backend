package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.InvoiceDTO;
import com.example.ecommercespring.entity.*;
import com.example.ecommercespring.repository.InvoiceRepository;
import com.example.ecommercespring.repository.OrderRepository;
import com.example.ecommercespring.repository.ShipperRepository;
import com.example.ecommercespring.repository.UserRepository;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShipperRepository shipperRepository;
    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<InvoiceDTO> getAll(){
        return invoiceRepository.findAll().stream().map(InvoiceDTO::new).collect(Collectors.toList());
    }

//    @Override
//    public InvoiceDTO getById(Long id){
//        Invoice invoice = invoiceRepository.findById(id).orElse(null);
//        return new InvoiceDTO(invoice);
//    }

    @Override
    public Response addNew(InvoiceDTO invoiceDTO){
        User user = userRepository.findById(invoiceDTO.getEmployeeId()).orElse(null);
        if(user == null)
            return new Response(false,"Mã nhân viên không tồn tại");
        OrderUser orderUser = orderRepository.findById(invoiceDTO.getOrderId()).orElse(null);
        if(orderUser == null)
            return new Response(false,"Mã phiếu đặt không tồn tại");
        Shipper shipper = shipperRepository.findById(invoiceDTO.getShipperId()).orElse(null);
        if(shipper == null)
            return new Response(false,"Mã nhân viên giao hàng không tồn tại");
        Invoice invoice = invoiceDTO.toEntity();
        invoice.setUser(user);
        invoice.setOrderUser(orderUser);
        invoice.setShipper(shipper);
        invoiceRepository.save(invoice);
        return new Response(true,"Tạo hóa đơn thành công");
    }

}

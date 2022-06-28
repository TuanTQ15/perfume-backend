package com.example.ecommercespring.service.impl;

import com.example.ecommercespring.dto.DetailReceiptDTO;
import com.example.ecommercespring.dto.ReceiptDTO;
import com.example.ecommercespring.entity.*;
import com.example.ecommercespring.repository.*;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    ReceiptRepository receiptRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    DetailReceiptRepository detailReceiptRepository;
    @Autowired
    OrderSupplyRepository orderSupplyRepository;

    @Override
    public List<ReceiptDTO> getAll() {
        return receiptRepository.findAll().stream().map(ReceiptDTO::new).collect(Collectors.toList());
    }
    @Override
    public ReceiptDTO getById(Long id){
        Receipt receipt = receiptRepository.findById(id).orElse(null);
        return new ReceiptDTO(receipt);
    }

    @Override
    public Response addNew(ReceiptDTO receiptDTO) {
        Receipt receipt = receiptDTO.toEntity();
        User user = userRepository.findById(receiptDTO.getEmployeeId()).orElse(null);
        if (user == null) {
            return new Response(false, "Mã nhân viên không tồn tại");
        }
        OrderSupply orderSupply = orderSupplyRepository.findById(receiptDTO.getOrderSupplyId()).orElse(null);
        if(orderSupply == null){
            return new Response(false, "Mã đơn đặt hàng từ hãng không tồn tại");
        }
        List<DetailReceipt> detailReceiptList = new ArrayList<>();
        for (DetailReceiptDTO detailReceiptDTO : receiptDTO.getDetailReceiptList()) {
            DetailReceipt detailReceipt = detailReceiptDTO.toEntity();
            Product product = productRepository.findById(detailReceiptDTO.getProductId()).orElse(null);
            if (product == null) {
                return new Response(false, "Sản phẩm không tồn tại");
            }
            detailReceipt.setProduct(product);
            detailReceipt.setReceipt(receipt);
            detailReceiptList.add(detailReceipt);
            product.setQuantityInStock(product.getQuantityInStock() + detailReceiptDTO.getQuantity());
        }
        receipt.setDetailReceiptList(detailReceiptList);
        receipt.setUser(user);
        receipt.setOrderSupply(orderSupply);
        receiptRepository.save(receipt);
        return new Response(true, "Thêm đợt khuyến mãi thành công");
    }
}

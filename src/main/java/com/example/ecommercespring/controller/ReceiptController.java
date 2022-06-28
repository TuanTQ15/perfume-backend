package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.BrandDTO;
import com.example.ecommercespring.dto.ReceiptDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.security.services.UserDetailsImpl;
import com.example.ecommercespring.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/receipt")
public class ReceiptController {

    @Autowired
    ReceiptService receiptService;

    @GetMapping
    public List<ReceiptDTO> Get(){
        return receiptService.getAll();
    }

    @GetMapping("/{id}")
    public ReceiptDTO Get(@PathVariable("id") Long id){
        return receiptService.getById(id);
    }

    @PostMapping
    public Response Post(@RequestBody ReceiptDTO receiptDTO){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        receiptDTO.setEmployeeId(userDetails.getUserId());
        return receiptService.addNew(receiptDTO);
    }
}

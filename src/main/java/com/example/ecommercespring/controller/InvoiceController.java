package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.InvoiceDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.security.services.UserDetailsImpl;
import com.example.ecommercespring.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/invoice")
    public List<InvoiceDTO> Get(){
        return invoiceService.getAll();
    }

    @PostMapping("/invoice")
    public Response Post(@RequestBody InvoiceDTO invoiceDTO){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        invoiceDTO.setEmployeeId(userDetails.getUserId());
        return invoiceService.addNew(invoiceDTO);
    }



//    @PostMapping
//    public ResponseEntity<?> getProfit()
}

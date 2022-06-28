package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.OrderSupplyDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.security.services.UserDetailsImpl;
import com.example.ecommercespring.service.OrderSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/os")
public class OrderSupplyController {

    @Autowired
    OrderSupplyService orderSupplyService;

    @GetMapping
    public List<OrderSupplyDTO> Get(){
        return orderSupplyService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return orderSupplyService.getById(id);
    }

    @PostMapping
    public Response Post(@RequestBody OrderSupplyDTO orderSupplyDTO){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderSupplyDTO.setEmployeeId(userDetails.getUserId());
        return orderSupplyService.addNew(orderSupplyDTO) ;
    }

    @PutMapping
    public Response Put(@RequestBody OrderSupplyDTO orderSupplyDTO,@RequestParam("updateStatus") boolean updateStatus){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderSupplyDTO.setEmployeeId(userDetails.getUserId());
        return orderSupplyService.modify(orderSupplyDTO,updateStatus) ;
    }
}

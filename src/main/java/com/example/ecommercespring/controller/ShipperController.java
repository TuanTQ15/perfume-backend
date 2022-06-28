package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.ShipperDTO;
import com.example.ecommercespring.dto.ShippingCompanyDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.ShipperService;
import com.example.ecommercespring.service.ShippingCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/shipper")
public class ShipperController {
    @Autowired
    ShipperService shipperService;

    @GetMapping
    public List<ShipperDTO> Get(){
        return shipperService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> Get(@PathVariable("id") Long id){
        return shipperService.getById(id);
    }

    @PostMapping
    public Response Post(@RequestBody ShipperDTO shipperDTO){
        return shipperService.addNew(shipperDTO);
    }

    @PutMapping
    public Response Put(@RequestBody ShipperDTO shipperDTO){
        return shipperService.modify(shipperDTO);
    }
    @DeleteMapping("/{id}")
    public Response Delete(@PathVariable("id") Long id){
        return shipperService.delete(id);
    }
}

package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.DetailOSDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.service.DetailOSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/detailOS")
public class DetailOSController {

    @Autowired
    DetailOSService detailOSService;

    @DeleteMapping
    public Response Delete(@RequestBody DetailOSDTO detailOSDTO){
        return detailOSService.delete(detailOSDTO);
    }
}

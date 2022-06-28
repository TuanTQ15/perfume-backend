package com.example.ecommercespring.controller;

import com.example.ecommercespring.dto.UserDTO;
import com.example.ecommercespring.respone.Response;
import com.example.ecommercespring.security.services.UserDetailsImpl;
import com.example.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/employee")
    public List<UserDTO> GetEmployee(){
        return userService.getUserListByRole(1L);
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id){
        return userService.getEmployeeById(id);
    }

    @PostMapping("/employee")
    public Response PostEmployee(@RequestBody UserDTO userDTO){
        return userService.addNewEmployee(userDTO);
    }

    @PutMapping("/employee/{id}")
    public Response PutEmployee(@RequestBody UserDTO userDTO,@PathVariable Long id){
        userDTO.setId(id);
        return userService.modify(userDTO);
    }

    @DeleteMapping("/employee/{id}")
    public Response DeleteEmployee(@PathVariable Long id){
        return userService.deleteEmployee(id);
    }

    //=============================

    @GetMapping("/customer")
    public List<UserDTO> GetCustomer(){
        return userService.getUserListByRole(2L);
    }

    @PostMapping("/customer")
    public Response Post(@RequestBody UserDTO userDTO){
        return userService.addNewCustomer(userDTO);
    }

    @PutMapping("/customer")
    public Response PutCustomer(@RequestBody UserDTO userDTO){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userDTO.setId(userDetails.getUserId());
        return userService.modify(userDTO);
    }
}
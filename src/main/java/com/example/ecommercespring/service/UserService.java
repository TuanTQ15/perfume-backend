package com.example.ecommercespring.service;

import com.example.ecommercespring.dto.UserDTO;
import com.example.ecommercespring.respone.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public List<UserDTO> getUserListByRole(Long id);
    public Response addNewCustomer(UserDTO userDTO);
    public Response addNewEmployee(UserDTO userDTO);
    public Response modify(UserDTO userDTO);
    public ResponseEntity<?> getEmployeeById(Long id);
    public Response deleteEmployee(Long id);
}

package com.example.ecommercespring.service;

import com.example.ecommercespring.dto.UserDTO;
import com.example.ecommercespring.respone.Response;

import java.util.List;

public interface EmployeeService {

    public List<UserDTO> getAll();
    public Response addNewEmployee(UserDTO employeeDTO);
    public Response modifyEmployee(UserDTO employeeDTO);
    public Response delete(Long employeeId);
}

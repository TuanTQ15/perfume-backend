package com.example.ecommercespring.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String role;


    public JwtResponse(String token, String email, String fullName,String address, String phoneNumber,  String role) {
        this.token = token;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }
}
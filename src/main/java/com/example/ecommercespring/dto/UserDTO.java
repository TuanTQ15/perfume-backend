package com.example.ecommercespring.dto;

import com.example.ecommercespring.entity.RoleName;
import com.example.ecommercespring.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id ;
    private String fullName ;
    private Date dateOfBirth ;
    private String address ;
    private String phoneNumber ;
    private String email ;
    private String password ;
    private RoleName role;

    public UserDTO(User user){
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.dateOfBirth = user.getDateOfBirth();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole().getName();
    }

    public User toEntity(){
        User user = new User();
        user.setDateOfBirth(this.getDateOfBirth());
        user.setAddress(this.getAddress());
        user.setPhoneNumber(this.getPhoneNumber());
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        user.setFullName(this.getFullName());
        return user;
    }
}

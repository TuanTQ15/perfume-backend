package com.example.ecommercespring.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ecommercespring.payload.request.LoginRequest;
import com.example.ecommercespring.payload.response.JwtResponse;
import com.example.ecommercespring.security.jwt.JwtUtils;
import com.example.ecommercespring.security.services.UserDetailsImpl;
import com.example.ecommercespring.service.AuthService;

import com.example.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final UserService userService;

    @Autowired
    AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                    PasswordEncoder encoder, UserService customService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.userService = customService;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        // if go there, the user/password is correct
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // generate jwt to return to client
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getFullName(),userDetails.getAddress(),userDetails.getPhoneNumber(),
                roles.get(0)));
    }
}

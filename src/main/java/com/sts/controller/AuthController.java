package com.sts.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sts.config.JwtTokenProvider;
import com.sts.dto.AuthRequest;
import com.sts.dto.AuthResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
	    @Autowired
	    private AuthenticationManager authManager;

	    @Autowired
	    private JwtTokenProvider tokenProvider;

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
	        try {
	            Authentication authentication = authManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
	            );

	            String token = tokenProvider.generateToken(request.getUsername());
	            return ResponseEntity.ok(new AuthResponse(token));
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	        }
	    }
}

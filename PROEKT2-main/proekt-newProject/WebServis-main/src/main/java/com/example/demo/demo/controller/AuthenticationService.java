package com.example.demo.demo.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.demo.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.demo.com.example.config.JwtService;
import com.example.demo.demo.model.Role;
import com.example.demo.demo.repository.BookingRepository;
import com.example.demo.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository repository;
    private final BookingRepository bookingRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();
    repository.save(user);

    // Generate JWT token including the userId
    var jwtToken = jwtService.generateToken(new HashMap<>(), user, user.getId_user());  // Pass the userId

    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
}

    // public AuthenticationResponse unregister(String token) {
    //     // Extract username from JWT token
    //     String username = jwtService.extractUsername(token.substring(7)); // Remove "Bearer "
    
    //     // Find the user in the database
    //     var user = repository.findByUsername(username)
    //             .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    
    //     // Delete the user from the database
    //     repository.delete(user);
    
    //     return AuthenticationResponse.builder()
    //             .token("User successfully unregistered.") // You can return a message instead of a token
    //             .build();
    // }
    public AuthenticationResponse unregister(String token) {
        // Extract the userId from the token (since the token contains userId as a claim)
        Integer userId = jwtService.extractUserIdFromToken(token.substring(7)); // Remove "Bearer " if it's in the header
        
        // Find the user by userId
        var user = repository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            bookingRepository.deleteByUserIdUser(userId);  
            repository.deleteById(userId); 
        return AuthenticationResponse.builder()
            .token("User and their bookings successfully unregistered.")  // Return a success message instead of a token
            .build();
    }
    
    
    

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = repository.findByUsername(request.getUsername())
            .orElseThrow();
    
        // Generate JWT token including the userId
        var jwtToken = jwtService.generateToken(new HashMap<>(), user, user.getId_user());  // Pass the userId
    
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }
    
    public AuthenticationResponse verifyToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return AuthenticationResponse.builder()
                .token("Invalid token format") 
                .build();
        }
    
        String token = authHeader.substring(7);
    
        try {
            String username = jwtService.extractUsername(token);
            boolean isValid = jwtService.validateToken(token, username);
    
            if (isValid) {
                return AuthenticationResponse.builder()
                    .token("{ \"valid\": true, \"username\": \"" + username + "\" }")
                    .build();
            } else {
                return AuthenticationResponse.builder()
                    .token("Invalid token")
                    .build();
            }
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                .token("Token verification failed: " + e.getMessage()) 
                .build();
        }
    }
    
    
}

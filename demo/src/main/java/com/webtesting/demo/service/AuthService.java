package com.webtesting.demo.service;

import com.webtesting.demo.dto.LoginRequest;
import com.webtesting.demo.dto.SignUpRequest;
import com.webtesting.demo.dto.UserResponse;
import com.webtesting.demo.model.User;
import com.webtesting.demo.repository.UserRepository;
import com.webtesting.demo.exception.ResourceAlreadyExistsException;
import com.webtesting.demo.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public UserResponse signup(SignUpRequest request) {
        log.info("Signing up new user: {}", request.getUsername());
        
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Username already exists: {}", request.getUsername());
            throw new ResourceAlreadyExistsException("Username already exists: " + request.getUsername());
        }
        
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Email already exists: {}", request.getEmail());
            throw new ResourceAlreadyExistsException("Email already exists: " + request.getEmail());
        }
        
        // Check password confirmation
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            log.warn("Password and confirmation do not match for user: {}", request.getUsername());
            throw new IllegalArgumentException("Password and confirmation do not match");
        }
        
        // Create new user
        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .fullName(request.getFullName())
            .isActive(true)
            .build();
        
        User savedUser = userRepository.save(user);
        log.info("User created successfully: {}", request.getUsername());
        return convertToResponse(savedUser);
    }
    
    public UserResponse login(LoginRequest request) {
        log.info("Logging in user with email: {}", request.getEmail());
        
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> {
                log.warn("User not found with email: {}", request.getEmail());
                return new ResourceNotFoundException("User not found with email: " + request.getEmail());
            });
        
        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Invalid password for user: {}", request.getEmail());
            throw new IllegalArgumentException("Invalid password");
        }
        
        log.info("User logged in successfully: {}", request.getEmail());
        return convertToResponse(user);
    }
    
    private UserResponse convertToResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .fullName(user.getFullName())
            .isActive(user.getIsActive())
            .build();
    }
}

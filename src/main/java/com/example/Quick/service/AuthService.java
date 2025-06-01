package com.example.Quick.service;

import com.example.Quick.Entity.User;
import com.example.Quick.Repository.UserRepository;
import com.example.Quick.dto.AuthResponse;
import com.example.Quick.dto.LoginRequest;
import com.example.Quick.dto.RegisterRequest;
import com.example.Quick.dto.UserDto;
import com.example.Quick.exception.ResourceNotFoundException;
import com.example.Quick.mapper.UserMapper;
import com.example.Quick.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = userMapper.registerRequestToUser(request);

        // Generate unique userCredentialId
        user.setUserCredentialId(UUID.randomUUID().toString());
        user.setRole("USER");
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);
        UserDto userDto = userMapper.userToUserDto(savedUser);

        return AuthResponse.builder()
                .token(token)
                .user(userDto)
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        // Manually verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(user);
        UserDto userDto = userMapper.userToUserDto(user);

        return AuthResponse.builder()
                .token(token)
                .user(userDto)
                .build();
    }

    public UserDto getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.userToUserDto(user);
    }
}

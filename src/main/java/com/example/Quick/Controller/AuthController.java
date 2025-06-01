package com.example.Quick.Controller;

import com.example.Quick.dto.AuthResponse;
import com.example.Quick.dto.LoginRequest;
import com.example.Quick.dto.RegisterRequest;
import com.example.Quick.dto.UserDto;
import com.example.Quick.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API endpoints")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Creates a new user account")
    @ApiResponse(responseCode = "201", description = "User registered successfully", 
                 content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @Operation(summary = "Login", description = "Authenticates a user and returns a JWT token")
    @ApiResponse(responseCode = "200", description = "Authentication successful", 
                 content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Get user profile", description = "Returns the current user's profile information")
    @ApiResponse(responseCode = "200", description = "Profile retrieved successfully", 
                 content = @Content(schema = @Schema(implementation = UserDto.class)))
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(Authentication authentication) {
        return ResponseEntity.ok(authService.getUserProfile(authentication.getName()));
    }
}

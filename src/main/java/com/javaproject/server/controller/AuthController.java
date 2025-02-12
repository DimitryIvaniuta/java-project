package com.javaproject.server.controller;

import com.javaproject.server.data.entity.User;
import com.javaproject.server.dto.ApiResponse;
import com.javaproject.server.dto.LoginRequest;
import com.javaproject.server.dto.LoginResponse;
import com.javaproject.server.service.AuthService;
import com.javaproject.server.validation.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Auth Service.
     */
    @Autowired
    private AuthService authService;

    /**
     * User Login access point.
     *
     * @param request a Login Request
     * @return a response
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody final LoginRequest request) {
        try {
            User user = authService.authenticateUser(request.getEmail(), request.getPassword());

            String token = generateJwtToken(user);

            return ResponseEntity.ok(new LoginResponse("Login successful", token, user.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.ordinal(), e.getMessage()));
        }
    }

    private String generateJwtToken(final User user) {
        return "dummy-jwt-token-for-" + user.getEmail();
    }

}

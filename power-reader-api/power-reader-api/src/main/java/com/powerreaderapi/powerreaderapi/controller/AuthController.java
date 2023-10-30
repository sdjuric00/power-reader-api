package com.powerreaderapi.powerreaderapi.controller;

import com.powerreaderapi.powerreaderapi.request.LoginRequest;
import com.powerreaderapi.powerreaderapi.response.UserLoginResponse;
import com.powerreaderapi.powerreaderapi.service.security.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Validated
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {

        return authService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
    }

}

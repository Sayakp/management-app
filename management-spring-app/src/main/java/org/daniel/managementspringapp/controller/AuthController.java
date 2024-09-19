package org.daniel.managementspringapp.controller;

import jakarta.validation.Valid;
import org.daniel.managementspringapp.dto.LoginRequestDto;
import org.daniel.managementspringapp.dto.NewUserDto;
import org.daniel.managementspringapp.dto.UserDto;
import org.daniel.managementspringapp.service.impl.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDto> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody NewUserDto newUser) {
        return ResponseEntity.ok(authService.registerUser(newUser));
    }

    @PostMapping("/signout")
    public ResponseEntity<String> logoutUser() {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authService.logoutUser().toString())
                .body("Signed out successfully");
    }
}

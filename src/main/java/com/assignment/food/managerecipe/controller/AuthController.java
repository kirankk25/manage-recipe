package com.assignment.food.managerecipe.controller;

import com.assignment.food.managerecipe.dto.AuthenticationResponse;
import com.assignment.food.managerecipe.model.LoginRequest;
import com.assignment.food.managerecipe.repository.UserRepository;
import com.assignment.food.managerecipe.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}

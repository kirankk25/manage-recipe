package com.assignment.food.managerecipe.service;

import com.assignment.food.managerecipe.dto.AuthenticationResponse;
import com.assignment.food.managerecipe.model.LoginRequest;
import com.assignment.food.managerecipe.model.User;
import com.assignment.food.managerecipe.repository.UserRepository;
import com.assignment.food.managerecipe.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationResponse login(LoginRequest loginRequest) {
    	
    	User initUser=new User();
		initUser.setEnabled(true);
		initUser.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
		initUser.setUserName(loginRequest.getUsername());
		userRepository.save(initUser);
		
        Authentication authentication=authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }
}

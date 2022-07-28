package com.assignment.food.managerecipe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.assignment.food.managerecipe.repository.UserRepository;
import com.assignment.food.managerecipe.service.AuthService;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class ManageRecipeApplication implements CommandLineRunner {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final AuthService authService;

	public static void main(String[] args) {
		SpringApplication.run(ManageRecipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * LoginRequest loginRequest = new LoginRequest("user","password"); User
		 * initUser=new User(); initUser.setEnabled(true);
		 * initUser.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
		 * initUser.setUserName(loginRequest.getUsername());
		 * userRepository.save(initUser);
		 * 
		 * System.out.println("====> "+authService.login(loginRequest).
		 * getAuthenticationToken());
		 */
	}

}

package com.assignment.food.managerecipe.controller;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.assignment.food.managerecipe.model.LoginRequest;
import com.assignment.food.managerecipe.service.AuthService;

@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    private String token = "";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        LoginRequest loginRequest = new LoginRequest("user", "password");
        token = authService.login(loginRequest).getAuthenticationToken();
        System.out.println("===>"+token);
    }

   //@Test
    public void test() throws Exception {
        @SuppressWarnings("unused")
		final MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("")
                .accept(MediaType.APPLICATION_JSON)
                        .header("authorization","Bearer "+token))
                .andReturn().getResponse();
    }
}

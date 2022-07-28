package com.assignment.food.managerecipe.controller;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.assignment.food.managerecipe.commonexceptions.ControllerExceptionHandler;
import com.assignment.food.managerecipe.commonexceptions.NotFoundException;
import com.assignment.food.managerecipe.model.Recipe;
import com.assignment.food.managerecipe.repository.RecipeRepository;
import com.assignment.food.managerecipe.service.RecipeService;

//Under development
public class RecipeControllerTest1 {

    @Mock
    RecipeService recipeService;
    
    @Autowired
    private RecipeRepository repository;

    RecipeController controller;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
    	MockitoAnnotations.openMocks(this);

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    //@Test
    public void testGetRecipe() throws Exception {
    	Recipe recipe = new Recipe(1L,"RCP1",1L,"recipe1","veg","potato","oven");
    	repository.save(recipe);
        mockMvc.perform(get("/api/recipes/recipe-by-code/test"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    //@Test
    public void testGetRecipeNotFound() throws Exception {

        when(recipeService.getRecipeByCode(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe-by-code/test1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

   //@Test
    public void testGetRecipeNumberFormatException() throws Exception {

        mockMvc.perform(get("/api/recipes/recipe-by-code/"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}
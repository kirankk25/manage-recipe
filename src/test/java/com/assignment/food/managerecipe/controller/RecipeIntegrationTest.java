package com.assignment.food.managerecipe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.assignment.food.managerecipe.commonexceptions.NotValidValueException;
import com.assignment.food.managerecipe.dto.IngredientDto;
import com.assignment.food.managerecipe.dto.RecipeDto;
import com.assignment.food.managerecipe.model.Recipe;
import com.assignment.food.managerecipe.repository.RecipeRepository;
import com.assignment.food.managerecipe.service.RecipeService;

@SpringBootTest
public class RecipeIntegrationTest {

    @Autowired
    private RecipeRepository repository;

    @Autowired
    @InjectMocks
    private RecipeController controller;

    @Autowired
    private RecipeService service;


    @Test
    public void testGetAllRecipes() throws Exception {
    	List<Recipe> expected = new ArrayList<>();
    	Recipe recipe = new Recipe(1L,"RCP1",1L,"recipe1","veg","potato","oven");
    	expected.add(recipe);
    	repository.save(recipe);
    	List<Recipe> actualRecipes = repository.findAll();
    	Map<String, Object> testMap = new HashMap<>();        
    	ResponseEntity<Page<RecipeDto>> response = controller.getAllRecipes(testMap);
    	assertThat(actualRecipes).size().isEqualTo(1);
    	assertThat(actualRecipes).isEqualTo(expected);
    	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    	assertThat(response.getBody()).isEqualTo(service.getAllRecipes(testMap));
    }
    
    @Test
    public void testAddRecipe() throws Exception {
        List<RecipeDto> expected = new ArrayList<>();
        IngredientDto ingredientDto = new IngredientDto();
        RecipeDto recipeDto = new RecipeDto(2L,2L,"RCP2","recipe2","veg2",ingredientDto,"potato2",1L);
        expected.add(recipeDto);     
        ResponseEntity<Object> response = controller.addNewRecipe(recipeDto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(service.addNewRecipe(recipeDto).getBody());
    }
    
    @Test
    public void testUpdateRecipe() throws Exception {
        List<RecipeDto> expected = new ArrayList<>();
        IngredientDto ingredientDto = new IngredientDto();
        RecipeDto recipeDto = new RecipeDto(3L,3L,"RCP3","recipe3","veg3",ingredientDto,"potato",1L);
        expected.add(recipeDto);
        ResponseEntity<Object> newRecipeResponse = controller.addNewRecipe(recipeDto);
        assertThat(newRecipeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(newRecipeResponse.getBody()).isEqualTo(service.addNewRecipe(recipeDto).getBody());
        RecipeDto updatedRecipe = new RecipeDto(3L,3L,"RCP3","updaterecope","updatedveg2",ingredientDto,"potato",1L);
        ResponseEntity<Object> response = controller.updateRecipe(updatedRecipe);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(service.updateRecipe(recipeDto).getBody());
    }
    
    @Test
    public void testdeleteRecipe() throws Exception {
        List<RecipeDto> expected = new ArrayList<>();
        IngredientDto ingredientDto = new IngredientDto();
        RecipeDto recipeDto = new RecipeDto(4L,4L,"RCP4","recipe4","veg4",ingredientDto,"potato",1L);
        expected.add(recipeDto);
        controller.addNewRecipe(recipeDto);
        ResponseEntity<Object> deletedResponse = controller.deleteRecipe(4L);
        assertThat(deletedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGetRecipe() throws Exception {
        assertThatThrownBy(() -> controller.getRecipeByCode("RCP5"))
                .isInstanceOf(NotValidValueException.class)
                .hasMessageContaining("Recipe not Found");
    }
}

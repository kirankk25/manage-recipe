package com.assignment.food.managerecipe.service;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.assignment.food.managerecipe.dto.RecipeDto;


public interface RecipeService {
	
    public Page<RecipeDto> getAllRecipes(Map<String, Object> filterCriteria);

    public ResponseEntity<Object> getRecipeByCode(String code);

    public ResponseEntity<Object> addNewRecipe(RecipeDto recipeDto);
    
    public ResponseEntity<Object> updateRecipe(RecipeDto recipeDto);
    
    public ResponseEntity<Object> deleteRecipe(Long id);
}    

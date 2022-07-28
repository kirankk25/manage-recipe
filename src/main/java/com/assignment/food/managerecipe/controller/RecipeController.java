package com.assignment.food.managerecipe.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.food.managerecipe.dto.RecipeDto;
import com.assignment.food.managerecipe.service.RecipeService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/recipes")
@AllArgsConstructor
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

	@GetMapping("/allrecipes")
	public ResponseEntity<Page<RecipeDto>> getAllRecipes(
			@RequestBody @Nullable final Map<String, Object> filterCriteria) {
		log.info("RecipeController.getAllRecipes() method called");
		return status(HttpStatus.OK).body(recipeService.getAllRecipes(filterCriteria));
	}

	@PostMapping("/add-recipe")
	public ResponseEntity<Object> addNewRecipe(@Valid @RequestBody RecipeDto recipeDto) {
		log.info("RecipeController.addNewRecipe() method called..");
		return recipeService.addNewRecipe(recipeDto);
	}
	
    @PostMapping("/update-recipe")
	public ResponseEntity<Object> updateRecipe(	@Valid @RequestBody final RecipeDto recipeDto) {
    	log.info("RecipeController.updateRecipe() method called");
		return recipeService.updateRecipe(recipeDto);
	}
    @DeleteMapping(value ="/delete-recipe/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable(value = "id") final Long id) {
    	log.info("RecipeController.deleteRecipe() method called");
		return recipeService.deleteRecipe(id);		
    }
    
    @GetMapping("/recipe-by-code/{code}")
    public ResponseEntity<Object> getRecipeByCode(@PathVariable String code){
    	log.info("RecipeController.getRecipeByCode() method called and received code::"+code);
    	return recipeService.getRecipeByCode(code);
    }

}

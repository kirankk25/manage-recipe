package com.assignment.food.managerecipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDto {
    private Long id;
    private Long servings;
    private String recipeCode;
    private String recipeName;
    private String recipeType;
    private IngredientDto ingredients;
    private String instructions;
    private Long calories;
}

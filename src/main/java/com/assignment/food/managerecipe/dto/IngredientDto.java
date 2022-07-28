/**
 * 
 */
package com.assignment.food.managerecipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kiran Kumar Karedla
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDto {
	private String ingredient;
    private String ingredientOperator;
}

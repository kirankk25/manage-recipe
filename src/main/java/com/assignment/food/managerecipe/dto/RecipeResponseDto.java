/**
 * 
 */
package com.assignment.food.managerecipe.dto;

import java.io.Serializable;

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
public class RecipeResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 406584961695634735L;
	
	private int status;
	private String code;
	private String message;
	
	
	

}

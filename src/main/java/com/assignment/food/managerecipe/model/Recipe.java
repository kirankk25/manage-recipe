package com.assignment.food.managerecipe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_RECIPE")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @NotBlank
    @Column(name = "RECIPE_CODE")
    private String recipeCode;
    
    @NotBlank
    @Column(name = "SERVINGS")
    private Long servings;
    
    @NotBlank
    @Column(name = "RECIPE_NAME")
    private String recipeName;

    @NotBlank
    @Column(name="RECIPE_TYPE")
    private String recipeType;

    @NotBlank
    @Column(name = "INGREDIENTS")
    private String ingredients;
    
    @NotBlank
    @Column(name = "INSTRUCTIONS")
    private String instructions;


}

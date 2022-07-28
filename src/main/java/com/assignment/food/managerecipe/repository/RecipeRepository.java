package com.assignment.food.managerecipe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.assignment.food.managerecipe.model.Recipe;

import io.micrometer.core.lang.Nullable;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {
		
	List<Recipe> findAll(@Nullable Specification<Recipe> search);
	Optional<Recipe> findByRecipeCode(String recipeCode);
}

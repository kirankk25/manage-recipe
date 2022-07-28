/**
 * 
 */
package com.assignment.food.managerecipe.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.food.managerecipe.commonexceptions.NotFoundException;
import com.assignment.food.managerecipe.commonexceptions.NotValidValueException;
import com.assignment.food.managerecipe.constants.RecipeConstants;
import com.assignment.food.managerecipe.dto.IngredientDto;
import com.assignment.food.managerecipe.dto.RecipeDto;
import com.assignment.food.managerecipe.dto.RecipeResponseDto;
import com.assignment.food.managerecipe.model.Recipe;
import com.assignment.food.managerecipe.repository.RecipeRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kiran Kumar Karedla
 * 
 *This is a Implementation class that Implements Recipes Service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {
	
    private final RecipeRepository recipeRepository;
    
    /**
     * This method used to fetch the recipes with/with out filter criteria
     */
    @Override
    public Page<RecipeDto> getAllRecipes(Map<String, Object> filterCriteria) {
    	log.info("RecipeServiceImpl.getAllRecipes() method called");
    	List<RecipeDto> recipeDtoList = null;
        if(null!=filterCriteria) {
			recipeDtoList = recipeRepository.findAll(buildSpec(filterCriteria)).stream().map(this::mapToDto)
					.collect(toList());   	
        }
        else {
			recipeDtoList = recipeRepository.findAll().stream().map(this::mapToDto).collect(toList());
        }
        log.info("RecipeServiceImpl.getAllRecipes() method ended");
        return new PageImpl<>(recipeDtoList);
    }

    
    /**
     * This method used to add new recipe.
     */
    @Override
    @Transactional
    public ResponseEntity<Object> addNewRecipe(RecipeDto recipeDto) {
    	log.info("RecipeServiceImpl.addNewRecipe() method called");
    	try {
    		Recipe newRecipe=new Recipe();
    		newRecipe.setRecipeCode(recipeDto.getRecipeCode());
    		newRecipe.setRecipeName(recipeDto.getRecipeName());
    		newRecipe.setServings(recipeDto.getServings());
    		newRecipe.setRecipeType(recipeDto.getRecipeType());
    		newRecipe.setIngredients(null!=recipeDto.getIngredients()?recipeDto.getIngredients().getIngredient():"");
    		newRecipe.setInstructions(recipeDto.getInstructions());
    		recipeRepository.save(newRecipe);
    	}
    	catch (Exception ex) {
    		log.info("RecipeServiceImpl.addNewRecipe() - exception"+ex.getMessage());
			RecipeResponseDto recipeResponseDto = new RecipeResponseDto(HttpStatus.BAD_GATEWAY.value(),
					RecipeConstants.SERVER_ERROR, RecipeConstants.EXPETION_WHILE_SAVING);
			return new ResponseEntity<>(recipeResponseDto, new HttpHeaders(), HttpStatus.BAD_GATEWAY);
		}
		RecipeResponseDto recipeResponseDto = new RecipeResponseDto(HttpStatus.OK.value(),
				RecipeConstants.SUCCESSFULLY_SAVED,recipeDto.getRecipeCode() );
		log.info("RecipeServiceImpl.addNewRecipe() Ended::"+RecipeConstants.SUCCESSFULLY_SAVED);
		return new ResponseEntity<>(recipeResponseDto, new HttpHeaders(),HttpStatus.OK);
    }
    
    /**
     * This method used to update recipe.
     */
    @Override
    @Transactional
    public ResponseEntity<Object> updateRecipe(RecipeDto recipeDto){
    	log.info("RecipeServiceImpl.updateRecipe() method called");
    	Optional<Recipe> recipe = recipeRepository.findById(recipeDto.getId());
    	if (!recipe.isPresent()){
    		log.info("RecipeServiceImpl.updateRecipe() - ended::"+RecipeConstants.RECIPE_NOT_FOUND);
    		throw new NotFoundException(RecipeConstants.RECIPE_NOT_FOUND);
    	   }
    	else{
    		try {
    		Recipe rcp  = recipe.get();
    		rcp.setRecipeName(recipeDto.getRecipeName());
    		rcp.setIngredients(null!=recipeDto.getIngredients()?recipeDto.getIngredients().getIngredient():"");
    		rcp.setRecipeType(recipeDto.getRecipeType());
    		rcp.setInstructions(recipeDto.getInstructions());
    		rcp.setServings(recipeDto.getServings());
			recipeRepository.save(rcp);
	    	}catch (Exception ex) {
				RecipeResponseDto recipeResponseDto = new RecipeResponseDto(HttpStatus.BAD_GATEWAY.value(),
						RecipeConstants.SERVER_ERROR, RecipeConstants.EXPETION_WHILE_UPDATING);
				return new ResponseEntity<>(recipeResponseDto, new HttpHeaders(), HttpStatus.BAD_GATEWAY);
			}
		RecipeResponseDto recipeResponseDto = new RecipeResponseDto(HttpStatus.OK.value(),
				RecipeConstants.SUCCESSFULLY_UPDATED, recipeDto.getRecipeCode());
		log.info("RecipeServiceImpl.addNewRecipe() Ended::"+RecipeConstants.SUCCESSFULLY_UPDATED);
		return new ResponseEntity<>(recipeResponseDto, new HttpHeaders(),HttpStatus.OK);
       }
    }
        
	    /**
	     * This method used to delete recipe.
	     */
        @Override
    	@Transactional
        public ResponseEntity<Object> deleteRecipe(Long id){
        	
        	Optional<Recipe> recipe = recipeRepository.findById(id);
        	if (!recipe.isPresent()){
        		log.info("RecipeServiceImpl.addNewRecipe() - ended::"+RecipeConstants.RECIPE_NOT_FOUND_DELETE);
        		throw new NotFoundException(RecipeConstants.RECIPE_NOT_FOUND_DELETE);
        	   }
        	else{
        		try {       			
        		    recipeRepository.delete(recipe.get()); 		
    	    	}catch (Exception ex) {
    				RecipeResponseDto recipeResponseDto = new RecipeResponseDto(HttpStatus.BAD_GATEWAY.value(),
    						RecipeConstants.SERVER_ERROR, RecipeConstants.EXPETION_WHILE_DELETEING);
    				return new ResponseEntity<>(recipeResponseDto, new HttpHeaders(), HttpStatus.BAD_GATEWAY);
    			}
    		RecipeResponseDto recipeResponseDto = new RecipeResponseDto(HttpStatus.OK.value(),
    				RecipeConstants.SUCCESSFULLY_DELETED, id+"");
    		log.info("RecipeServiceImpl.addNewRecipe() Ended::"+RecipeConstants.SUCCESSFULLY_DELETED);
    		return new ResponseEntity<>(recipeResponseDto, new HttpHeaders(),HttpStatus.OK);
            }
    
         }
        
        
        /**
         * This method used to fetch recipe based on Code
         */
        @Override
        public ResponseEntity<Object> getRecipeByCode(String code) {
        	log.info("RecipeServiceImpl.getRecipeByCode() method called");
        	Optional<Recipe> recipe = recipeRepository.findByRecipeCode(code);
        	if (!recipe.isPresent()){
        		log.info(RecipeConstants.RECIPE_NOT_FOUND+":::RecipeServiceImpl.getRecipeById() method ended");
        		throw new NotValidValueException(RecipeConstants.RECIPE_NOT_FOUND);
        	}
        	else{
        		log.info("RecipeServiceImpl.getRecipeByCode() method ended with Statsu OK");
        		return new ResponseEntity<>(mapToDto(recipe.get()), new HttpHeaders(),HttpStatus.OK);
        	}
        }
        	
    	private Specification<Recipe> buildSpec(final Map<String, Object> filterCriteria) {
    		log.info("RecipeServiceImpl.buildSpec() method called");
    		return new Specification<Recipe>() {
    			
    			private static final long serialVersionUID = 1L;
    			@SuppressWarnings("unchecked")
    			@Override
    			public Predicate toPredicate(final Root<Recipe> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
    				final List<Predicate> filterPredicates = new ArrayList<>();
    				final Set<String> paramKeySet = filterCriteria.keySet();
    				paramKeySet.forEach(parameter -> {
    					switch(parameter) {
    					  case RecipeConstants.RECIPE_CODE:
    						filterPredicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(RecipeConstants.RECIPE_CODE)),
    								filterCriteria.get(parameter).toString().toLowerCase()));
    					  break; 
    					  case RecipeConstants.RECIPE_TYPE:
    						filterPredicates.add(criteriaBuilder.like(
    								criteriaBuilder.lower(root.get(RecipeConstants.RECIPE_TYPE).as(String.class)),
    								filterCriteria.get(parameter).toString().toLowerCase() + RecipeConstants.PERCENTAGE));
    					  break;
    					  case RecipeConstants.INGREDINETS:
    						HashMap<String,String> map =  (HashMap<String, String>)filterCriteria.get(RecipeConstants.INGREDINETS);
    						if (null!=map.get(RecipeConstants.INGREDINETS_OPERATOR) && map.get(RecipeConstants.INGREDINETS_OPERATOR).equals(RecipeConstants.EXCLUDE)) {
    							filterPredicates.add(criteriaBuilder.notLike(
    									criteriaBuilder.lower(root.get(RecipeConstants.INGREDINETS).as(String.class)),
    									RecipeConstants.PERCENTAGE + map.get(RecipeConstants.INGREDINET).toLowerCase()
    											+ RecipeConstants.PERCENTAGE));
    						} else {
    							filterPredicates.add(criteriaBuilder
    									.like(criteriaBuilder.lower(root.get(RecipeConstants.INGREDINETS).as(String.class)),
    											RecipeConstants.PERCENTAGE
    													+ map.get(RecipeConstants.INGREDINET).toLowerCase()
    													+ RecipeConstants.PERCENTAGE));
    						}
    					  break;
    					  case RecipeConstants.SERVINGS:
    						filterPredicates.add(criteriaBuilder.equal(root.get(RecipeConstants.SERVINGS),
    								filterCriteria.get(parameter).toString()));
    					  break;
    					  case RecipeConstants.INSTRUCTIONS:
    						filterPredicates.add(criteriaBuilder.like(
    								criteriaBuilder.lower(root.get(RecipeConstants.INSTRUCTIONS).as(String.class)),
    								RecipeConstants.PERCENTAGE+ filterCriteria.get(parameter).toString().toLowerCase() + RecipeConstants.PERCENTAGE));
    					  break;
    					  case RecipeConstants.RECIPE_NAME:
    						filterPredicates.add(criteriaBuilder.like(
    								criteriaBuilder.lower(root.get(RecipeConstants.RECIPE_NAME).as(String.class)),
    								filterCriteria.get(parameter).toString() + RecipeConstants.PERCENTAGE));
    					  break;
    					  default:
    						  throw new NotValidValueException(RecipeConstants.FILER_NOT_MATCH);
    					}
    				});
    				return criteriaBuilder.and(filterPredicates.toArray(new Predicate[filterPredicates.size()]));
    			}
    		};
    	}

    	private RecipeDto mapToDto(Recipe recipe) {
            return RecipeDto.builder().recipeName(recipe.getRecipeCode())
                    .id(recipe.getId())
                    .servings(recipe.getServings())
                    .recipeCode(recipe.getRecipeCode())
                    .recipeName(recipe.getRecipeName())
                    .recipeType(recipe.getRecipeType())
                    .ingredients(mapToIngredientDto(recipe))
                    .instructions(recipe.getInstructions())
                    .build();
        }
    	
		private IngredientDto mapToIngredientDto(Recipe recipe) {
			return IngredientDto.builder().ingredient(recipe.getIngredients()).build();
		}


}

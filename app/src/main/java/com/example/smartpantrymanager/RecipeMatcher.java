package com.example.smartpantrymanager;

import java.util.ArrayList;

public class RecipeMatcher {

    public static ArrayList<Recipe> getMatchingRecipes(
            ArrayList<Ingredient> pantryIngredients,
            ArrayList<Recipe> allRecipes) {

        ArrayList<Recipe> matchingRecipes = new ArrayList<>();

        // Check every recipe
        for (Recipe recipe : allRecipes) {

            boolean canMakeRecipe = true;

            // Check every ingredient required by the recipe
            for (String requiredIngredient : recipe.getIngredients()) {

                boolean ingredientFound = false;

                // Look for the required ingredient in the pantry
                for (Ingredient pantryIngredient : pantryIngredients) {

                    if (pantryIngredient.getName()
                            .trim()
                            .equalsIgnoreCase(requiredIngredient.trim())) {

                        ingredientFound = true;
                        break;
                    }
                }

                // If one required ingredient is missing,
                // the recipe cannot be suggested
                if (!ingredientFound) {
                    canMakeRecipe = false;
                    break;
                }
            }

            // Only add recipes when ALL ingredients are available
            if (canMakeRecipe) {
                matchingRecipes.add(recipe);
            }
        }

        return matchingRecipes;
    }
}
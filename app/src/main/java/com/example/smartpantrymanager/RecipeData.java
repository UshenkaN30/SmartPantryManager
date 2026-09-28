package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeData {

    public static ArrayList<Recipe> getAllRecipes() {

        ArrayList<Recipe> recipes = new ArrayList<>();

        recipes.add(new Recipe(
                1,
                "Scrambled Eggs",
                new ArrayList<>(Arrays.asList("eggs", "milk", "butter")),
                "Beat the eggs with milk. Melt the butter in a pan, add the egg mixture and cook while stirring until done."
        ));

        recipes.add(new Recipe(
                2,
                "Cheese Omelette",
                new ArrayList<>(Arrays.asList("eggs", "cheese", "butter")),
                "Beat the eggs, melt the butter in a pan and add the eggs. Add cheese and fold the omelette before serving."
        ));

        recipes.add(new Recipe(
                3,
                "Tomato Sandwich",
                new ArrayList<>(Arrays.asList("bread", "tomato", "butter")),
                "Spread butter on the bread, add sliced tomato and close the sandwich."
        ));

        recipes.add(new Recipe(
                4,
                "Cheese Sandwich",
                new ArrayList<>(Arrays.asList("bread", "cheese", "butter")),
                "Spread butter on the bread, add cheese and close the sandwich."
        ));

        recipes.add(new Recipe(
                5,
                "Egg Sandwich",
                new ArrayList<>(Arrays.asList("bread", "eggs", "butter")),
                "Cook the eggs, spread butter on the bread and add the cooked eggs."
        ));

        recipes.add(new Recipe(
                6,
                "Tomato Pasta",
                new ArrayList<>(Arrays.asList("pasta", "tomato", "onion")),
                "Cook the pasta. Fry the onion and tomato until soft, then combine with the cooked pasta."
        ));

        recipes.add(new Recipe(
                7,
                "Cheese Pasta",
                new ArrayList<>(Arrays.asList("pasta", "cheese", "milk")),
                "Cook the pasta. Add milk and grated cheese, then stir over low heat until creamy."
        ));

        recipes.add(new Recipe(
                8,
                "Chicken and Rice",
                new ArrayList<>(Arrays.asList("chicken", "rice", "onion")),
                "Cook the rice. Fry the onion and chicken until cooked through, then serve with the rice."
        ));

        recipes.add(new Recipe(
                9,
                "Vegetable Rice",
                new ArrayList<>(Arrays.asList("rice", "carrot", "peas")),
                "Cook the rice. Cook the carrot and peas until tender, then mix them into the rice."
        ));

        recipes.add(new Recipe(
                10,
                "Fried Rice",
                new ArrayList<>(Arrays.asList("rice", "eggs", "onion")),
                "Fry the onion, add cooked rice and then add beaten eggs. Stir until the eggs are cooked."
        ));

        recipes.add(new Recipe(
                11,
                "Mashed Potatoes",
                new ArrayList<>(Arrays.asList("potato", "milk", "butter")),
                "Boil the potatoes until soft. Drain and mash them with milk and butter."
        ));

        recipes.add(new Recipe(
                12,
                "Potato and Egg Hash",
                new ArrayList<>(Arrays.asList("potato", "eggs", "onion")),
                "Cook diced potato and onion in a pan until tender. Add the eggs and cook until set."
        ));

        recipes.add(new Recipe(
                13,
                "Chicken Pasta",
                new ArrayList<>(Arrays.asList("chicken", "pasta", "tomato")),
                "Cook the pasta. Cook the chicken and tomato in a pan, then combine with the pasta."
        ));

        recipes.add(new Recipe(
                14,
                "Banana Toast",
                new ArrayList<>(Arrays.asList("bread", "banana", "butter")),
                "Toast the bread, spread with butter and top with sliced banana."
        ));

        recipes.add(new Recipe(
                15,
                "Banana Milkshake",
                new ArrayList<>(Arrays.asList("banana", "milk", "sugar")),
                "Add the banana, milk and sugar to a blender and blend until smooth."
        ));

        return recipes;
    }
}
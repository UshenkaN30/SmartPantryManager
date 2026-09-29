package com.example.smartpantrymanager;

import java.util.ArrayList;

public class Recipe {

    private int id;
    private String name;

    // Ingredient names
    private ArrayList<String> ingredients;

    // Required quantity for each ingredient
    private ArrayList<Double> requiredQuantities;

    // Required unit for each ingredient
    private ArrayList<String> requiredUnits;

    private String instructions;

    // New constructor used for recipes loaded from SQLite
    public Recipe(int id,
                  String name,
                  ArrayList<String> ingredients,
                  ArrayList<Double> requiredQuantities,
                  ArrayList<String> requiredUnits,
                  String instructions) {

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.requiredQuantities = requiredQuantities;
        this.requiredUnits = requiredUnits;
        this.instructions = instructions;
    }

    // Keep old constructor so existing code still works
    public Recipe(int id,
                  String name,
                  ArrayList<String> ingredients,
                  String instructions) {

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;

        this.requiredQuantities = new ArrayList<>();
        this.requiredUnits = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<Double> getRequiredQuantities() {
        return requiredQuantities;
    }

    public ArrayList<String> getRequiredUnits() {
        return requiredUnits;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setRequiredQuantities(
            ArrayList<Double> requiredQuantities) {

        this.requiredQuantities = requiredQuantities;
    }

    public void setRequiredUnits(
            ArrayList<String> requiredUnits) {

        this.requiredUnits = requiredUnits;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
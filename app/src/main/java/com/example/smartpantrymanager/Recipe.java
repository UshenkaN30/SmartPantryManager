package com.example.smartpantrymanager;

import java.util.ArrayList;

public class Recipe {

    private int id;
    private String name;
    private ArrayList<String> ingredients;
    private String instructions;

    public Recipe(int id, String name,
                  ArrayList<String> ingredients,
                  String instructions) {

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
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

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
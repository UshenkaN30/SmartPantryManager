package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private Button btnBackToPantry;
    private TextView tvNoRecipes;
    private RecyclerView recyclerViewRecipes;

    private DatabaseHelper databaseHelper;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        // Connect views
        btnBackToPantry = findViewById(R.id.btnBackToPantry);
        tvNoRecipes = findViewById(R.id.tvNoRecipes);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);

        // Set up RecyclerView
        recyclerViewRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // Connect to SQLite database
        databaseHelper = new DatabaseHelper(this);

        // Load matching recipes
        loadSuggestedRecipes();

        // Back button
        btnBackToPantry.setOnClickListener(v -> finish());
    }

    private void loadSuggestedRecipes() {

        // Get ingredients currently stored in the pantry
        ArrayList<Ingredient> pantryIngredients =
                databaseHelper.getAllIngredients();

        // Get all 15 recipes
        ArrayList<Recipe> allRecipes =
                RecipeData.getAllRecipes();

        // Find recipes where ALL required ingredients are available
        ArrayList<Recipe> matchingRecipes =
                RecipeMatcher.getMatchingRecipes(
                        pantryIngredients,
                        allRecipes
                );

        // Display matching recipes
        recipeAdapter = new RecipeAdapter(matchingRecipes);
        recyclerViewRecipes.setAdapter(recipeAdapter);

        // Show a message when there are no matches
        if (matchingRecipes.isEmpty()) {

            tvNoRecipes.setText(
                    "No recipes can be made with your current pantry ingredients."
            );

            tvNoRecipes.setVisibility(View.VISIBLE);
            recyclerViewRecipes.setVisibility(View.GONE);

        } else {

            tvNoRecipes.setVisibility(View.GONE);
            recyclerViewRecipes.setVisibility(View.VISIBLE);
        }
    }
}
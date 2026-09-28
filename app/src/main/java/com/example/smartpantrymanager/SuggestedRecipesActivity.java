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
    private TextView tvRecipesTitle;
    private TextView tvRecipesSubtitle;
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
        tvRecipesTitle = findViewById(R.id.tvRecipesTitle);
        tvRecipesSubtitle = findViewById(R.id.tvRecipesSubtitle);
        tvNoRecipes = findViewById(R.id.tvNoRecipes);
        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);

        // Set up RecyclerView
        recyclerViewRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        databaseHelper = new DatabaseHelper(this);

        // Check whether user selected View All Recipes
        boolean showAllRecipes =
                getIntent().getBooleanExtra(
                        "show_all_recipes",
                        false
                );

        if (showAllRecipes) {
            loadAllRecipes();
        } else {
            loadSuggestedRecipes();
        }

        // Back to Pantry
        btnBackToPantry.setOnClickListener(v -> finish());
    }

    private void loadSuggestedRecipes() {

        tvRecipesTitle.setText("Suggested Recipes");
        tvRecipesSubtitle.setText(
                "Recipes you can make using your current pantry ingredients."
        );

        ArrayList<Ingredient> pantryIngredients =
                databaseHelper.getAllIngredients();

        ArrayList<Recipe> allRecipes =
                RecipeData.getAllRecipes();

        ArrayList<Recipe> matchingRecipes =
                RecipeMatcher.getMatchingRecipes(
                        pantryIngredients,
                        allRecipes
                );

        recipeAdapter = new RecipeAdapter(matchingRecipes);
        recyclerViewRecipes.setAdapter(recipeAdapter);

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

    private void loadAllRecipes() {

        tvRecipesTitle.setText("All Recipes");
        tvRecipesSubtitle.setText(
                "Browse all 15 recipes available in Smart Pantry Manager."
        );

        ArrayList<Recipe> allRecipes =
                RecipeData.getAllRecipes();

        recipeAdapter = new RecipeAdapter(allRecipes);
        recyclerViewRecipes.setAdapter(recipeAdapter);

        tvNoRecipes.setVisibility(View.GONE);
        recyclerViewRecipes.setVisibility(View.VISIBLE);
    }
}
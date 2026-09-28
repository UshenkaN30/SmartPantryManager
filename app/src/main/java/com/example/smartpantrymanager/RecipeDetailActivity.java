package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView tvRecipeDetailTitle;
    private TextView tvRecipeDetailIngredients;
    private TextView tvRecipeDetailInstructions;
    private Button btnBackToRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Connect views
        tvRecipeDetailTitle =
                findViewById(R.id.tvRecipeDetailTitle);

        tvRecipeDetailIngredients =
                findViewById(R.id.tvRecipeDetailIngredients);

        tvRecipeDetailInstructions =
                findViewById(R.id.tvRecipeDetailInstructions);

        btnBackToRecipes =
                findViewById(R.id.btnBackToRecipes);

        // Receive recipe information
        String recipeName =
                getIntent().getStringExtra("recipe_name");

        String recipeIngredients =
                getIntent().getStringExtra("recipe_ingredients");

        String recipeInstructions =
                getIntent().getStringExtra("recipe_instructions");

        // Display recipe information
        tvRecipeDetailTitle.setText(recipeName);
        tvRecipeDetailIngredients.setText(recipeIngredients);
        tvRecipeDetailInstructions.setText(recipeInstructions);

        // Return to Suggested Recipes
        btnBackToRecipes.setOnClickListener(v -> finish());
    }
}
package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnAddIngredient;
    private Button btnSuggestedRecipes;
    private Button btnAllRecipes;
    private Button btnSettings;

    private TextView tvEmptyPantry;
    private RecyclerView recyclerViewPantry;

    private DatabaseHelper databaseHelper;
    private ArrayList<Ingredient> ingredientList;
    private IngredientAdapter ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect views
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);
        btnAllRecipes = findViewById(R.id.btnAllRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        tvEmptyPantry = findViewById(R.id.tvEmptyPantry);
        recyclerViewPantry = findViewById(R.id.recyclerViewPantry);

        // Create database helper
        databaseHelper = new DatabaseHelper(this);

        // Set up RecyclerView
        recyclerViewPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // Add Ingredient
        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );
            startActivity(intent);
        });

        // Suggested Recipes
        btnSuggestedRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );

            intent.putExtra("show_all_recipes", false);

            startActivity(intent);
        });

        // View All Recipes
        btnAllRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
            );

            intent.putExtra("show_all_recipes", true);

            startActivity(intent);
        });

        // Settings
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIngredients();
    }

    private void loadIngredients() {

        ingredientList = databaseHelper.getAllIngredients();

        ingredientAdapter = new IngredientAdapter(ingredientList);

        recyclerViewPantry.setAdapter(ingredientAdapter);

        if (ingredientList.isEmpty()) {
            tvEmptyPantry.setVisibility(View.VISIBLE);
            recyclerViewPantry.setVisibility(View.GONE);
        } else {
            tvEmptyPantry.setVisibility(View.GONE);
            recyclerViewPantry.setVisibility(View.VISIBLE);
        }
    }
}
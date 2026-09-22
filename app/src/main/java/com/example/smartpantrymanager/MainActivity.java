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
        tvEmptyPantry = findViewById(R.id.tvEmptyPantry);
        recyclerViewPantry = findViewById(R.id.recyclerViewPantry);

        // Create database helper
        databaseHelper = new DatabaseHelper(this);

        // Set up RecyclerView
        recyclerViewPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // Add Ingredient button
        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );
            startActivity(intent);
        });

        // Suggested Recipes button
        btnSuggestedRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SuggestedRecipesActivity.class
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

        // Show empty message only when pantry has no ingredients
        if (ingredientList.isEmpty()) {
            tvEmptyPantry.setVisibility(View.VISIBLE);
            recyclerViewPantry.setVisibility(View.GONE);
        } else {
            tvEmptyPantry.setVisibility(View.GONE);
            recyclerViewPantry.setVisibility(View.VISIBLE);
        }
    }
}
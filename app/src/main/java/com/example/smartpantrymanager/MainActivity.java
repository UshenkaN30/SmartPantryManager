package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    private Toolbar mainToolbar;

    private DatabaseHelper databaseHelper;
    private ArrayList<Ingredient> ingredientList;
    private IngredientAdapter ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect views
        btnAddIngredient =
                findViewById(R.id.btnAddIngredient);

        btnSuggestedRecipes =
                findViewById(R.id.btnSuggestedRecipes);

        btnAllRecipes =
                findViewById(R.id.btnAllRecipes);

        btnSettings =
                findViewById(R.id.btnSettings);

        tvEmptyPantry =
                findViewById(R.id.tvEmptyPantry);

        recyclerViewPantry =
                findViewById(R.id.recyclerViewPantry);

        mainToolbar =
                findViewById(R.id.mainToolbar);

        // Create database helper
        databaseHelper =
                new DatabaseHelper(this);

        // Set up RecyclerView
        recyclerViewPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // =====================================================
        // TOOLBAR NAVIGATION MENU
        // =====================================================

        mainToolbar.inflateMenu(
                R.menu.main_menu
        );

        mainToolbar.setOnMenuItemClickListener(item -> {

            int itemId = item.getItemId();

            // Suggested Recipes
            if (itemId == R.id.menuSuggestedRecipes) {

                openSuggestedRecipes();

                return true;
            }

            // All Recipes
            if (itemId == R.id.menuAllRecipes) {

                openAllRecipes();

                return true;
            }

            // Settings
            if (itemId == R.id.menuSettings) {

                openSettings();

                return true;
            }

            return false;
        });

        // =====================================================
        // EXISTING BUTTON NAVIGATION
        // =====================================================

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
            openSuggestedRecipes();
        });

        // View All Recipes
        btnAllRecipes.setOnClickListener(v -> {
            openAllRecipes();
        });

        // Settings
        btnSettings.setOnClickListener(v -> {
            openSettings();
        });
    }

    // =========================================================
    // NAVIGATION METHODS
    // =========================================================

    private void openSuggestedRecipes() {

        Intent intent = new Intent(
                MainActivity.this,
                SuggestedRecipesActivity.class
        );

        intent.putExtra(
                "show_all_recipes",
                false
        );

        startActivity(intent);
    }

    private void openAllRecipes() {

        Intent intent = new Intent(
                MainActivity.this,
                SuggestedRecipesActivity.class
        );

        intent.putExtra(
                "show_all_recipes",
                true
        );

        startActivity(intent);
    }

    private void openSettings() {

        Intent intent = new Intent(
                MainActivity.this,
                SettingsActivity.class
        );

        startActivity(intent);
    }

    // =========================================================
    // PANTRY
    // =========================================================

    @Override
    protected void onResume() {
        super.onResume();
        loadIngredients();
    }

    private void loadIngredients() {

        ingredientList =
                databaseHelper.getAllIngredients();

        ingredientAdapter =
                new IngredientAdapter(
                        ingredientList
                );

        recyclerViewPantry.setAdapter(
                ingredientAdapter
        );

        if (ingredientList.isEmpty()) {

            tvEmptyPantry.setVisibility(
                    View.VISIBLE
            );

            recyclerViewPantry.setVisibility(
                    View.GONE
            );

        } else {

            tvEmptyPantry.setVisibility(
                    View.GONE
            );

            recyclerViewPantry.setVisibility(
                    View.VISIBLE
            );
        }
    }
}
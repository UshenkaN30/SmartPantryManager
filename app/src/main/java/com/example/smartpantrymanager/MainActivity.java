package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnAddIngredient;
    private Button btnSuggestedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        // Add Ingredient button
        btnAddIngredient = findViewById(R.id.btnAddIngredient);

        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    AddEditIngredientActivity.class);
            startActivity(intent);
        });

        // Suggested Recipes button
        btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);

        btnSuggestedRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    SuggestedRecipesActivity.class);
            startActivity(intent);
        });
    }
}
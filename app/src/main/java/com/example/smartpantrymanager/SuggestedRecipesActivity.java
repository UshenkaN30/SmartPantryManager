package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private Button btnBackToPantry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        btnBackToPantry = findViewById(R.id.btnBackToPantry);

        btnBackToPantry.setOnClickListener(v -> finish());
    }
}
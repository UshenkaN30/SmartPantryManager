package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText etIngredientName;
    private EditText etQuantity;
    private EditText etUnit;

    private Button btnSaveIngredient;
    private Button btnCancel;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        // Connect Java variables to the XML views
        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);

        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);
        btnCancel = findViewById(R.id.btnCancel);

        // Create database helper
        databaseHelper = new DatabaseHelper(this);

        // Save Ingredient button
        btnSaveIngredient.setOnClickListener(v -> saveIngredient());

        // Cancel button
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveIngredient() {

        String name = etIngredientName.getText().toString().trim();
        String quantityText = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();

        // Validate ingredient name
        if (name.isEmpty()) {
            etIngredientName.setError("Please enter an ingredient name");
            etIngredientName.requestFocus();
            return;
        }

        // Validate quantity
        if (quantityText.isEmpty()) {
            etQuantity.setError("Please enter a quantity");
            etQuantity.requestFocus();
            return;
        }

        // Validate unit
        if (unit.isEmpty()) {
            etUnit.setError("Please enter a unit");
            etUnit.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            etQuantity.setError("Please enter a valid quantity");
            etQuantity.requestFocus();
            return;
        }

        if (quantity <= 0) {
            etQuantity.setError("Quantity must be greater than 0");
            etQuantity.requestFocus();
            return;
        }

        // Create ingredient object
        Ingredient ingredient = new Ingredient(name, quantity, unit);

        // Save ingredient to SQLite
        long result = databaseHelper.addIngredient(ingredient);

        if (result != -1) {
            Toast.makeText(
                    AddEditIngredientActivity.this,
                    "Ingredient saved successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {
            Toast.makeText(
                    AddEditIngredientActivity.this,
                    "Failed to save ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
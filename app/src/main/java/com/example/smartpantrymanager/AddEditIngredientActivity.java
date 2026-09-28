package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText etIngredientName;
    private EditText etQuantity;
    private EditText etUnit;

    private Button btnSaveIngredient;
    private Button btnCancel;

    private DatabaseHelper databaseHelper;

    private int ingredientId = -1;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        // Connect Java variables to XML views
        etIngredientName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);

        btnSaveIngredient = findViewById(R.id.btnSaveIngredient);
        btnCancel = findViewById(R.id.btnCancel);

        databaseHelper = new DatabaseHelper(this);

        // Check if this screen was opened for editing
        if (getIntent().hasExtra("ingredient_id")) {

            isEditMode = true;

            ingredientId = getIntent().getIntExtra(
                    "ingredient_id", -1
            );

            String name = getIntent().getStringExtra(
                    "ingredient_name"
            );

            double quantity = getIntent().getDoubleExtra(
                    "ingredient_quantity", 0
            );

            String unit = getIntent().getStringExtra(
                    "ingredient_unit"
            );

            // Fill the form with existing ingredient information
            etIngredientName.setText(name);
            etQuantity.setText(formatQuantity(quantity));
            etUnit.setText(unit);

            btnSaveIngredient.setText("Update Ingredient");

            TextView title = findViewById(R.id.tvIngredientTitle);
            title.setText("Edit Ingredient");
        }

        // Save or Update button
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

        // EDIT existing ingredient
        if (isEditMode) {

            Ingredient ingredient =
                    new Ingredient(ingredientId, name, quantity, unit);

            int result =
                    databaseHelper.updateIngredient(ingredient);

            if (result > 0) {

                Toast.makeText(
                        this,
                        "Ingredient updated successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Failed to update ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } else {

            // ADD new ingredient
            Ingredient ingredient =
                    new Ingredient(name, quantity, unit);

            long result =
                    databaseHelper.addIngredient(ingredient);

            if (result != -1) {

                Toast.makeText(
                        this,
                        "Ingredient saved successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Failed to save ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    private String formatQuantity(double quantity) {

        if (quantity == Math.floor(quantity)) {
            return String.valueOf((int) quantity);
        }

        return String.valueOf(quantity);
    }
}
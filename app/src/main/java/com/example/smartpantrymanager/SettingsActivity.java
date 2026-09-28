package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchNotifications;
    private Switch switchRecipeSuggestions;
    private Button btnBackToPantry;

    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "SmartPantrySettings";
    private static final String KEY_NOTIFICATIONS = "notifications";
    private static final String KEY_RECIPE_SUGGESTIONS = "recipe_suggestions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Connect views
        switchNotifications = findViewById(R.id.switchNotifications);
        switchRecipeSuggestions = findViewById(R.id.switchRecipeSuggestions);
        btnBackToPantry = findViewById(R.id.btnBackToPantry);

        // Open saved settings
        sharedPreferences = getSharedPreferences(
                PREFS_NAME,
                MODE_PRIVATE
        );

        // Load previously saved settings
        boolean notificationsEnabled =
                sharedPreferences.getBoolean(KEY_NOTIFICATIONS, true);

        boolean recipeSuggestionsEnabled =
                sharedPreferences.getBoolean(KEY_RECIPE_SUGGESTIONS, true);

        switchNotifications.setChecked(notificationsEnabled);
        switchRecipeSuggestions.setChecked(recipeSuggestionsEnabled);

        // Save notification setting when changed
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {

            sharedPreferences.edit()
                    .putBoolean(KEY_NOTIFICATIONS, isChecked)
                    .apply();

            if (isChecked) {
                Toast.makeText(
                        this,
                        "Pantry notifications enabled",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                Toast.makeText(
                        this,
                        "Pantry notifications disabled",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // Save recipe suggestion setting when changed
        switchRecipeSuggestions.setOnCheckedChangeListener((buttonView, isChecked) -> {

            sharedPreferences.edit()
                    .putBoolean(KEY_RECIPE_SUGGESTIONS, isChecked)
                    .apply();

            if (isChecked) {
                Toast.makeText(
                        this,
                        "Recipe suggestions enabled",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                Toast.makeText(
                        this,
                        "Recipe suggestions disabled",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // Return to pantry
        btnBackToPantry.setOnClickListener(v -> finish());
    }
}
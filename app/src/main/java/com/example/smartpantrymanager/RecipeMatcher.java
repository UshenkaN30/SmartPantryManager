package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeMatcher {

    public static ArrayList<Recipe> getMatchingRecipes(
            ArrayList<Ingredient> pantryIngredients,
            ArrayList<Recipe> allRecipes) {

        ArrayList<Recipe> matchingRecipes = new ArrayList<>();

        // Check every recipe
        for (Recipe recipe : allRecipes) {

            boolean canMakeRecipe = true;

            ArrayList<String> requiredIngredients =
                    recipe.getIngredients();

            ArrayList<Double> requiredQuantities =
                    recipe.getRequiredQuantities();

            ArrayList<String> requiredUnits =
                    recipe.getRequiredUnits();

            // Safety check
            if (requiredIngredients == null
                    || requiredQuantities == null
                    || requiredUnits == null
                    || requiredIngredients.size() != requiredQuantities.size()
                    || requiredIngredients.size() != requiredUnits.size()) {

                continue;
            }

            // Check every ingredient required by the recipe
            for (int i = 0;
                 i < requiredIngredients.size();
                 i++) {

                String requiredName =
                        requiredIngredients.get(i);

                double requiredQuantity =
                        requiredQuantities.get(i);

                String requiredUnit =
                        requiredUnits.get(i);

                double totalAvailable = 0;

                // Search pantry for this ingredient
                for (Ingredient pantryIngredient
                        : pantryIngredients) {

                    if (sameIngredient(
                            pantryIngredient.getName(),
                            requiredName)) {

                        double convertedQuantity =
                                convertQuantity(
                                        pantryIngredient.getQuantity(),
                                        pantryIngredient.getUnit(),
                                        requiredUnit
                                );

                        // Only add quantities with compatible units
                        if (convertedQuantity >= 0) {
                            totalAvailable += convertedQuantity;
                        }
                    }
                }

                // Strict quantity rule:
                // available amount must be at least
                // the amount required by the recipe
                if (totalAvailable < requiredQuantity) {

                    canMakeRecipe = false;
                    break;
                }
            }

            // Recipe is suggested only when
            // ALL ingredients have enough quantity
            if (canMakeRecipe) {
                matchingRecipes.add(recipe);
            }
        }

        return matchingRecipes;
    }

    // =========================================================
    // INGREDIENT NAME MATCHING
    // =========================================================

    private static boolean sameIngredient(
            String pantryName,
            String requiredName) {

        String normalPantry =
                normalizeIngredientName(pantryName);

        String normalRequired =
                normalizeIngredientName(requiredName);

        return normalPantry.equals(normalRequired);
    }

    // =========================================================
    // INGREDIENT NAME NORMALISATION
    // =========================================================

    private static String normalizeIngredientName(
            String name) {

        if (name == null) {
            return "";
        }

        String normalized =
                name.trim()
                        .toLowerCase(Locale.ROOT);

        // Handle common plural ingredient names
        switch (normalized) {

            case "eggs":
                return "egg";

            case "tomatoes":
                return "tomato";

            case "potatoes":
                return "potato";

            case "bananas":
                return "banana";

            case "carrots":
                return "carrot";

            case "onions":
                return "onion";

            case "peas":
                return "pea";

            default:

                // Basic plural handling
                if (normalized.endsWith("s")
                        && normalized.length() > 1) {

                    normalized =
                            normalized.substring(
                                    0,
                                    normalized.length() - 1
                            );
                }

                return normalized;
        }
    }

    // =========================================================
    // QUANTITY AND UNIT CONVERSION
    // =========================================================

    private static double convertQuantity(
            double quantity,
            String pantryUnit,
            String requiredUnit) {

        String from =
                normalizeUnit(pantryUnit);

        String to =
                normalizeUnit(requiredUnit);

        // Same unit
        if (from.equals(to)) {
            return quantity;
        }

        // ---------------- WEIGHT ----------------

        // Kilograms -> grams
        if (from.equals("kg")
                && to.equals("g")) {

            return quantity * 1000;
        }

        // Grams -> kilograms
        if (from.equals("g")
                && to.equals("kg")) {

            return quantity / 1000;
        }

        // ---------------- VOLUME ----------------

        // Litres -> millilitres
        if (from.equals("l")
                && to.equals("ml")) {

            return quantity * 1000;
        }

        // Millilitres -> litres
        if (from.equals("ml")
                && to.equals("l")) {

            return quantity / 1000;
        }

        // Incompatible units
        return -1;
    }

    // =========================================================
    // UNIT NORMALISATION
    // =========================================================

    private static String normalizeUnit(
            String unit) {

        if (unit == null) {
            return "";
        }

        String normalized =
                unit.trim()
                        .toLowerCase(Locale.ROOT);

        // ---------------- WEIGHT ----------------

        if (normalized.equals("kg")
                || normalized.equals("kilogram")
                || normalized.equals("kilograms")) {

            return "kg";
        }

        if (normalized.equals("g")
                || normalized.equals("gram")
                || normalized.equals("grams")) {

            return "g";
        }

        // ---------------- VOLUME ----------------

        if (normalized.equals("l")
                || normalized.equals("liter")
                || normalized.equals("liters")
                || normalized.equals("litre")
                || normalized.equals("litres")) {

            return "l";
        }

        if (normalized.equals("ml")
                || normalized.equals("milliliter")
                || normalized.equals("milliliters")
                || normalized.equals("millilitre")
                || normalized.equals("millilitres")) {

            return "ml";
        }

        // ---------------- PIECES ----------------

        if (normalized.equals("piece")
                || normalized.equals("pieces")
                || normalized.equals("unit")
                || normalized.equals("units")) {

            return "piece";
        }

        // ---------------- SLICES ----------------

        if (normalized.equals("slice")
                || normalized.equals("slices")) {

            return "slice";
        }

        return normalized;
    }
}
package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";

    // Changed from version 1 to version 2
    private static final int DATABASE_VERSION = 2;

    // ---------------- PANTRY TABLE ----------------

    private static final String TABLE_INGREDIENTS = "ingredients";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_UNIT = "unit";

    // ---------------- RECIPE TABLE ----------------

    private static final String TABLE_RECIPES = "recipes";

    private static final String RECIPE_ID = "recipe_id";
    private static final String RECIPE_NAME = "recipe_name";
    private static final String RECIPE_INSTRUCTIONS = "instructions";

    // ---------------- RECIPE INGREDIENT TABLE ----------------

    private static final String TABLE_RECIPE_INGREDIENTS =
            "recipe_ingredients";

    private static final String RI_ID = "id";
    private static final String RI_RECIPE_ID = "recipe_id";
    private static final String RI_NAME = "ingredient_name";
    private static final String RI_QUANTITY = "required_quantity";
    private static final String RI_UNIT = "required_unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createIngredientTable(db);
        createRecipeTables(db);

        seedRecipes(db);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        // Preserve existing pantry data.
        // Version 2 adds recipe tables only.
        if (oldVersion < 2) {

            createRecipeTables(db);
            seedRecipes(db);
        }
    }

    // =========================================================
    // TABLE CREATION
    // =========================================================

    private void createIngredientTable(SQLiteDatabase db) {

        String createIngredientsTable =
                "CREATE TABLE " + TABLE_INGREDIENTS + " (" +
                        COLUMN_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME +
                        " TEXT NOT NULL, " +
                        COLUMN_QUANTITY +
                        " REAL NOT NULL, " +
                        COLUMN_UNIT +
                        " TEXT NOT NULL)";

        db.execSQL(createIngredientsTable);
    }

    private void createRecipeTables(SQLiteDatabase db) {

        String createRecipesTable =
                "CREATE TABLE IF NOT EXISTS " +
                        TABLE_RECIPES + " (" +
                        RECIPE_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        RECIPE_NAME +
                        " TEXT NOT NULL, " +
                        RECIPE_INSTRUCTIONS +
                        " TEXT NOT NULL)";

        db.execSQL(createRecipesTable);

        String createRecipeIngredientsTable =
                "CREATE TABLE IF NOT EXISTS " +
                        TABLE_RECIPE_INGREDIENTS + " (" +
                        RI_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        RI_RECIPE_ID +
                        " INTEGER NOT NULL, " +
                        RI_NAME +
                        " TEXT NOT NULL, " +
                        RI_QUANTITY +
                        " REAL NOT NULL, " +
                        RI_UNIT +
                        " TEXT NOT NULL, " +
                        "FOREIGN KEY(" + RI_RECIPE_ID + ") REFERENCES " +
                        TABLE_RECIPES + "(" + RECIPE_ID + "))";

        db.execSQL(createRecipeIngredientsTable);
    }

    // =========================================================
    // PANTRY CRUD
    // =========================================================

    public long addIngredient(Ingredient ingredient) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, ingredient.getName());
        values.put(COLUMN_QUANTITY, ingredient.getQuantity());
        values.put(COLUMN_UNIT, ingredient.getUnit());

        long result =
                db.insert(TABLE_INGREDIENTS, null, values);

        db.close();

        return result;
    }

    public ArrayList<Ingredient> getAllIngredients() {

        ArrayList<Ingredient> ingredientList =
                new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_INGREDIENTS,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(COLUMN_ID)
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(COLUMN_NAME)
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(COLUMN_UNIT)
                );

                ingredientList.add(
                        new Ingredient(
                                id,
                                name,
                                quantity,
                                unit
                        )
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ingredientList;
    }

    public int updateIngredient(Ingredient ingredient) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, ingredient.getName());
        values.put(COLUMN_QUANTITY, ingredient.getQuantity());
        values.put(COLUMN_UNIT, ingredient.getUnit());

        int result = db.update(
                TABLE_INGREDIENTS,
                values,
                COLUMN_ID + " = ?",
                new String[]{
                        String.valueOf(ingredient.getId())
                }
        );

        db.close();

        return result;
    }

    public int deleteIngredient(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_INGREDIENTS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return result;
    }

    // =========================================================
    // RECIPE SEEDING
    // =========================================================

    private void seedRecipes(SQLiteDatabase db) {

        // Avoid inserting the recipes more than once.
        Cursor cursor =
                db.rawQuery(
                        "SELECT COUNT(*) FROM " + TABLE_RECIPES,
                        null
                );

        boolean recipesAlreadyExist = false;

        if (cursor.moveToFirst()) {
            recipesAlreadyExist =
                    cursor.getInt(0) > 0;
        }

        cursor.close();

        if (recipesAlreadyExist) {
            return;
        }

        addRecipe(
                db,
                "Scrambled Eggs",
                "Beat the eggs with milk. Melt the butter in a pan, " +
                        "add the egg mixture and cook while stirring until done.",
                new String[]{"eggs", "milk", "butter"},
                new double[]{2, 50, 10},
                new String[]{"pieces", "ml", "g"}
        );

        addRecipe(
                db,
                "Cheese Omelette",
                "Beat the eggs, melt the butter in a pan, cook the eggs " +
                        "and add cheese before folding the omelette.",
                new String[]{"eggs", "cheese", "butter"},
                new double[]{2, 50, 10},
                new String[]{"pieces", "g", "g"}
        );

        addRecipe(
                db,
                "Tomato Sandwich",
                "Butter the bread, slice the tomato and place it between " +
                        "the bread slices.",
                new String[]{"bread", "tomato", "butter"},
                new double[]{2, 1, 10},
                new String[]{"slices", "pieces", "g"}
        );

        addRecipe(
                db,
                "Cheese Sandwich",
                "Butter the bread and add the cheese between the slices.",
                new String[]{"bread", "cheese", "butter"},
                new double[]{2, 50, 10},
                new String[]{"slices", "g", "g"}
        );

        addRecipe(
                db,
                "Egg Sandwich",
                "Cook the eggs, butter the bread and place the cooked " +
                        "eggs between the bread slices.",
                new String[]{"bread", "eggs", "butter"},
                new double[]{2, 2, 10},
                new String[]{"slices", "pieces", "g"}
        );

        addRecipe(
                db,
                "Tomato Pasta",
                "Cook the pasta. Cook the onion and tomato together, " +
                        "then combine with the pasta.",
                new String[]{"pasta", "tomato", "onion"},
                new double[]{200, 2, 1},
                new String[]{"g", "pieces", "pieces"}
        );

        addRecipe(
                db,
                "Cheese Pasta",
                "Cook the pasta, warm the milk, add the cheese and " +
                        "combine everything together.",
                new String[]{"pasta", "cheese", "milk"},
                new double[]{200, 100, 100},
                new String[]{"g", "g", "ml"}
        );

        addRecipe(
                db,
                "Chicken and Rice",
                "Cook the rice. Cook the chicken and onion, then serve " +
                        "the chicken mixture with the rice.",
                new String[]{"chicken", "rice", "onion"},
                new double[]{200, 200, 1},
                new String[]{"g", "g", "pieces"}
        );

        addRecipe(
                db,
                "Vegetable Rice",
                "Cook the rice and vegetables, then combine them together.",
                new String[]{"rice", "carrot", "peas"},
                new double[]{200, 1, 100},
                new String[]{"g", "pieces", "g"}
        );

        addRecipe(
                db,
                "Fried Rice",
                "Cook the rice. Fry the onion and eggs, add the rice " +
                        "and stir until heated through.",
                new String[]{"rice", "eggs", "onion"},
                new double[]{200, 2, 1},
                new String[]{"g", "pieces", "pieces"}
        );

        addRecipe(
                db,
                "Mashed Potatoes",
                "Boil the potatoes until soft, then mash with milk and butter.",
                new String[]{"potato", "milk", "butter"},
                new double[]{3, 100, 20},
                new String[]{"pieces", "ml", "g"}
        );

        addRecipe(
                db,
                "Potato and Egg Hash",
                "Cook the potatoes and onion, add the eggs and cook " +
                        "until the eggs are set.",
                new String[]{"potato", "eggs", "onion"},
                new double[]{2, 2, 1},
                new String[]{"pieces", "pieces", "pieces"}
        );

        addRecipe(
                db,
                "Chicken Pasta",
                "Cook the pasta and chicken, add tomato and combine.",
                new String[]{"chicken", "pasta", "tomato"},
                new double[]{200, 200, 2},
                new String[]{"g", "g", "pieces"}
        );

        addRecipe(
                db,
                "Banana Toast",
                "Toast the bread, spread with butter and top with " +
                        "sliced banana.",
                new String[]{"bread", "banana", "butter"},
                new double[]{2, 1, 10},
                new String[]{"slices", "pieces", "g"}
        );

        addRecipe(
                db,
                "Banana Milkshake",
                "Blend the banana, milk and sugar until smooth.",
                new String[]{"banana", "milk", "sugar"},
                new double[]{1, 250, 10},
                new String[]{"pieces", "ml", "g"}
        );
    }

    private void addRecipe(
            SQLiteDatabase db,
            String name,
            String instructions,
            String[] ingredientNames,
            double[] quantities,
            String[] units) {

        ContentValues recipeValues =
                new ContentValues();

        recipeValues.put(RECIPE_NAME, name);
        recipeValues.put(
                RECIPE_INSTRUCTIONS,
                instructions
        );

        long recipeId =
                db.insert(
                        TABLE_RECIPES,
                        null,
                        recipeValues
                );

        for (int i = 0;
             i < ingredientNames.length;
             i++) {

            ContentValues ingredientValues =
                    new ContentValues();

            ingredientValues.put(
                    RI_RECIPE_ID,
                    recipeId
            );

            ingredientValues.put(
                    RI_NAME,
                    ingredientNames[i]
            );

            ingredientValues.put(
                    RI_QUANTITY,
                    quantities[i]
            );

            ingredientValues.put(
                    RI_UNIT,
                    units[i]
            );

            db.insert(
                    TABLE_RECIPE_INGREDIENTS,
                    null,
                    ingredientValues
            );
        }
    }
    // =========================================================
// GET ALL RECIPES FROM SQLITE
// =========================================================

    public ArrayList<Recipe> getAllRecipes() {

        ArrayList<Recipe> recipeList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor recipeCursor = db.query(
                TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                RECIPE_NAME + " ASC"
        );

        if (recipeCursor.moveToFirst()) {

            do {

                int recipeId = recipeCursor.getInt(
                        recipeCursor.getColumnIndexOrThrow(RECIPE_ID)
                );

                String recipeName = recipeCursor.getString(
                        recipeCursor.getColumnIndexOrThrow(RECIPE_NAME)
                );

                String instructions = recipeCursor.getString(
                        recipeCursor.getColumnIndexOrThrow(
                                RECIPE_INSTRUCTIONS
                        )
                );

                ArrayList<String> ingredientNames =
                        new ArrayList<>();

                ArrayList<Double> requiredQuantities =
                        new ArrayList<>();

                ArrayList<String> requiredUnits =
                        new ArrayList<>();

                Cursor ingredientCursor = db.query(
                        TABLE_RECIPE_INGREDIENTS,
                        null,
                        RI_RECIPE_ID + " = ?",
                        new String[]{
                                String.valueOf(recipeId)
                        },
                        null,
                        null,
                        RI_ID + " ASC"
                );

                if (ingredientCursor.moveToFirst()) {

                    do {

                        String ingredientName =
                                ingredientCursor.getString(
                                        ingredientCursor
                                                .getColumnIndexOrThrow(
                                                        RI_NAME
                                                )
                                );

                        double requiredQuantity =
                                ingredientCursor.getDouble(
                                        ingredientCursor
                                                .getColumnIndexOrThrow(
                                                        RI_QUANTITY
                                                )
                                );

                        String requiredUnit =
                                ingredientCursor.getString(
                                        ingredientCursor
                                                .getColumnIndexOrThrow(
                                                        RI_UNIT
                                                )
                                );

                        ingredientNames.add(ingredientName);
                        requiredQuantities.add(requiredQuantity);
                        requiredUnits.add(requiredUnit);

                    } while (ingredientCursor.moveToNext());
                }

                ingredientCursor.close();

                Recipe recipe = new Recipe(
                        recipeId,
                        recipeName,
                        ingredientNames,
                        requiredQuantities,
                        requiredUnits,
                        instructions
                );

                recipeList.add(recipe);

            } while (recipeCursor.moveToNext());
        }

        recipeCursor.close();
        db.close();

        return recipeList;
    }
}
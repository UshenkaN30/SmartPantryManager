package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_INGREDIENTS = "ingredients";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_UNIT = "unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createIngredientsTable =
                "CREATE TABLE " + TABLE_INGREDIENTS + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_QUANTITY + " REAL NOT NULL, " +
                        COLUMN_UNIT + " TEXT NOT NULL)";

        db.execSQL(createIngredientsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        onCreate(db);
    }

    // Add a new ingredient
    public long addIngredient(Ingredient ingredient) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, ingredient.getName());
        values.put(COLUMN_QUANTITY, ingredient.getQuantity());
        values.put(COLUMN_UNIT, ingredient.getUnit());

        long result = db.insert(TABLE_INGREDIENTS, null, values);

        db.close();

        return result;
    }

    // Get all ingredients
    public ArrayList<Ingredient> getAllIngredients() {

        ArrayList<Ingredient> ingredientList = new ArrayList<>();

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

                Ingredient ingredient =
                        new Ingredient(id, name, quantity, unit);

                ingredientList.add(ingredient);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ingredientList;
    }
}
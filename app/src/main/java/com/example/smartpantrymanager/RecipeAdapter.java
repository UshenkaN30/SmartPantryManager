package com.example.smartpantrymanager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter
        extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final ArrayList<Recipe> recipeList;

    public RecipeAdapter(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_recipe,
                        parent,
                        false
                );

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position) {

        Recipe recipe = recipeList.get(position);

        holder.tvRecipeName.setText(
                recipe.getName()
        );

        // Build ingredient text including
        // required quantity and unit
        String ingredientText =
                buildIngredientText(recipe, false);

        holder.tvRecipeIngredients.setText(
                ingredientText
        );

        holder.btnViewRecipe.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    RecipeDetailActivity.class
            );

            intent.putExtra(
                    "recipe_name",
                    recipe.getName()
            );

            // Send detailed ingredient information
            // to RecipeDetailActivity
            intent.putExtra(
                    "recipe_ingredients",
                    buildIngredientText(recipe, true)
            );

            intent.putExtra(
                    "recipe_instructions",
                    recipe.getInstructions()
            );

            v.getContext().startActivity(intent);
        });
    }

    // =========================================================
    // BUILD INGREDIENT DISPLAY TEXT
    // =========================================================

    private String buildIngredientText(
            Recipe recipe,
            boolean useNewLines) {

        ArrayList<String> ingredients =
                recipe.getIngredients();

        ArrayList<Double> quantities =
                recipe.getRequiredQuantities();

        ArrayList<String> units =
                recipe.getRequiredUnits();

        StringBuilder builder =
                new StringBuilder();

        for (int i = 0;
             i < ingredients.size();
             i++) {

            if (i > 0) {

                if (useNewLines) {
                    builder.append("\n");
                } else {
                    builder.append(", ");
                }
            }

            double quantity =
                    quantities.get(i);

            // Remove unnecessary .0
            if (quantity == Math.floor(quantity)) {

                builder.append(
                        (int) quantity
                );

            } else {

                builder.append(quantity);
            }

            builder.append(" ");
            builder.append(units.get(i));
            builder.append(" ");
            builder.append(
                    capitalize(
                            ingredients.get(i)
                    )
            );
        }

        return builder.toString();
    }

    // =========================================================
    // CAPITALISE INGREDIENT NAME
    // =========================================================

    private String capitalize(String text) {

        if (text == null || text.isEmpty()) {
            return "";
        }

        return text.substring(0, 1)
                .toUpperCase()
                + text.substring(1);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    // =========================================================
    // VIEW HOLDER
    // =========================================================

    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvRecipeName;
        TextView tvRecipeIngredients;
        Button btnViewRecipe;

        public RecipeViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvRecipeName =
                    itemView.findViewById(
                            R.id.tvRecipeName
                    );

            tvRecipeIngredients =
                    itemView.findViewById(
                            R.id.tvRecipeIngredients
                    );

            btnViewRecipe =
                    itemView.findViewById(
                            R.id.btnViewRecipe
                    );
        }
    }
}
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
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder, int position) {

        Recipe recipe = recipeList.get(position);

        holder.tvRecipeName.setText(recipe.getName());

        String ingredients =
                String.join(", ", recipe.getIngredients());

        holder.tvRecipeIngredients.setText(ingredients);

        holder.btnViewRecipe.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    RecipeDetailActivity.class
            );

            intent.putExtra(
                    "recipe_name",
                    recipe.getName()
            );

            intent.putExtra(
                    "recipe_ingredients",
                    String.join("\n", recipe.getIngredients())
            );

            intent.putExtra(
                    "recipe_instructions",
                    recipe.getInstructions()
            );

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvRecipeName;
        TextView tvRecipeIngredients;
        Button btnViewRecipe;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecipeName =
                    itemView.findViewById(R.id.tvRecipeName);

            tvRecipeIngredients =
                    itemView.findViewById(R.id.tvRecipeIngredients);

            btnViewRecipe =
                    itemView.findViewById(R.id.btnViewRecipe);
        }
    }
}
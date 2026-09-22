package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IngredientAdapter
        extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final ArrayList<Ingredient> ingredientList;

    public IngredientAdapter(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull IngredientViewHolder holder, int position) {

        Ingredient ingredient = ingredientList.get(position);

        holder.tvIngredientName.setText(ingredient.getName());

        String quantityText =
                formatQuantity(ingredient.getQuantity())
                        + " " + ingredient.getUnit();

        holder.tvIngredientQuantity.setText(quantityText);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    private String formatQuantity(double quantity) {

        if (quantity == Math.floor(quantity)) {
            return String.valueOf((int) quantity);
        }

        return String.valueOf(quantity);
    }

    public static class IngredientViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvIngredientName;
        TextView tvIngredientQuantity;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIngredientName =
                    itemView.findViewById(R.id.tvIngredientName);

            tvIngredientQuantity =
                    itemView.findViewById(R.id.tvIngredientQuantity);
        }
    }
}
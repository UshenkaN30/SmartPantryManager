package com.example.smartpantrymanager;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        // Edit button
        holder.btnEditIngredient.setOnClickListener(v -> {

            android.content.Intent intent =
                    new android.content.Intent(
                            v.getContext(),
                            AddEditIngredientActivity.class
                    );

            intent.putExtra("ingredient_id", ingredient.getId());
            intent.putExtra("ingredient_name", ingredient.getName());
            intent.putExtra("ingredient_quantity", ingredient.getQuantity());
            intent.putExtra("ingredient_unit", ingredient.getUnit());

            v.getContext().startActivity(intent);
        });

        // Delete button
        holder.btnDeleteIngredient.setOnClickListener(v -> {

            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Ingredient")
                    .setMessage("Are you sure you want to delete "
                            + ingredient.getName() + "?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Delete", (dialog, which) -> {

                        DatabaseHelper databaseHelper =
                                new DatabaseHelper(v.getContext());

                        int result =
                                databaseHelper.deleteIngredient(ingredient.getId());

                        if (result > 0) {

                            int currentPosition =
                                    holder.getAdapterPosition();

                            if (currentPosition !=
                                    RecyclerView.NO_POSITION) {

                                ingredientList.remove(currentPosition);
                                notifyItemRemoved(currentPosition);
                            }

                            Toast.makeText(
                                    v.getContext(),
                                    "Ingredient deleted",
                                    Toast.LENGTH_SHORT
                            ).show();

                        } else {

                            Toast.makeText(
                                    v.getContext(),
                                    "Failed to delete ingredient",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    })
                    .show();
        });
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
        Button btnEditIngredient;
        Button btnDeleteIngredient;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIngredientName =
                    itemView.findViewById(R.id.tvIngredientName);

            tvIngredientQuantity =
                    itemView.findViewById(R.id.tvIngredientQuantity);

            btnEditIngredient =
                    itemView.findViewById(R.id.btnEditIngredient);

            btnDeleteIngredient =
                    itemView.findViewById(R.id.btnDeleteIngredient);
        }
    }
}
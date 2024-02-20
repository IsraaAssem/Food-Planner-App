package com.example.foodplanner.search.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.NavGraphDirections;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Ingredient;

import java.util.ArrayList;
import java.util.List;



    public class IngredientSearchAdapter extends RecyclerView.Adapter<com.example.foodplanner.search.view.IngredientSearchAdapter.ItemViewHolder> {
        Context context;
        public ArrayList<Ingredient> ingredients;/////
        private ArrayList<Ingredient> filteredIngredients;/////
        private static final String TAG = "RecyclerView";
        OnItemClickListener listener;
        public IngredientSearchAdapter(Context context, ArrayList<Ingredient> ingredients, OnItemClickListener listener) {
            this.context = context;
            this.ingredients = ingredients;
            this.listener = listener;
            this.filteredIngredients = new ArrayList<>(ingredients);
        }
        public void setList(List<Ingredient> meals) {
            this.ingredients.clear();
            this.filteredIngredients.clear();
            this.ingredients.addAll((ArrayList<Ingredient>) meals);
            this.filteredIngredients.addAll((ArrayList<Ingredient>) meals);
        }
        @NonNull
        @Override
        public com.example.foodplanner.search.view.IngredientSearchAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view =inflater.inflate(R.layout.meal_item2,parent,false);
            com.example.foodplanner.search.view.IngredientSearchAdapter.ItemViewHolder itemViewHolder=new com.example.foodplanner.search.view.IngredientSearchAdapter.ItemViewHolder(view);
            Log.i(TAG, "onCreateViewHolder: ");
            itemViewHolder.btnAddToFav.setText("View");
            return itemViewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull com.example.foodplanner.search.view.IngredientSearchAdapter.ItemViewHolder holder, int position) {
            Ingredient ingredient=(Ingredient) filteredIngredients.get(position);
            Log.i(TAG, "onBindViewHolder: "+position);
            holder.mealName.setText(ingredient.getStrIngredient());

           holder.btnAddToFav.setOnClickListener(new View.OnClickListener() {
            //holder.mealName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onIngredientClick(ingredient);
                    NavGraphDirections.ActionToHomeFragment action=NavGraphDirections.actionToHomeFragment(ingredient.getStrIngredient());
                    Navigation.findNavController(v).navigate(action);
                }

            });
        }

        @Override
        public int getItemCount() {
            Log.d("SearchAdapter", "Number of items: " + ingredients.size()+"  "+filteredIngredients.size());

            if (filteredIngredients == null)
                return 0;
            return filteredIngredients.size();
        }
        public void filter(String query) {
            filteredIngredients.clear();
            if (query.isEmpty()) {
                filteredIngredients.addAll(ingredients);
            } else {
                for (Ingredient ingredient : ingredients) {
                    if (ingredient.getStrIngredient().toLowerCase().contains(query.toLowerCase())) {
                        filteredIngredients.add(ingredient);
                    }
                }
            }
            notifyDataSetChanged();
        }
        class ItemViewHolder extends RecyclerView.ViewHolder{

            ImageView mealImage;
            TextView mealName;
            Button btnAddToFav;
            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                mealImage=itemView.findViewById(R.id.mealImage);
                mealName=itemView.findViewById(R.id.mealName);
                btnAddToFav=itemView.findViewById(R.id.btnAddToFavourite);

            }

        }
    }

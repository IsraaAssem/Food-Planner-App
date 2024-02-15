package com.example.foodplanner.favourites.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Meal;

import java.util.List;

public class FavMealAdapter  extends RecyclerView.Adapter<FavMealAdapter.MealViewHolder>{


    Context context;
    List<Meal> meals;
    private static final String TAG = "FavMealAdapter";
    OnFavouriteClickListener listener;
    public FavMealAdapter(){}

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =inflater.inflate(R.layout.meal_item2,parent,false);
        MealViewHolder mealViewHolder=new MealViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: ");
        return mealViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal currentMeal = meals.get(position);

        holder.mealName.setText(currentMeal.getMealName());

        Glide.with(context)
                .load(meals.get(position).getMealImage())
                .apply(new RequestOptions().override(200, 200))
                .placeholder(R.drawable.loading)
                .error(R.drawable.app_logo)
                .into(holder.mealImage);

        holder.btnRemoveFromFav.setText("Remove from favs");
        holder.btnRemoveFromFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onFavListener(currentMeal);

            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public FavMealAdapter(Context context,List<Meal>meals,OnFavouriteClickListener listener){
        this.context=context;
        this.meals=meals;
        this.listener=listener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
    class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;
        Button btnRemoveFromFav;
        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(R.id.mealName);
            btnRemoveFromFav = itemView.findViewById(R.id.btnAddToFavourite);////////

        }
    }
}

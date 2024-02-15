package com.example.foodplanner.random_meal.view;

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

import com.example.foodplanner.R;
import com.example.foodplanner.model.Meal;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class RandomMealAdapter extends RecyclerView.Adapter<RandomMealAdapter.MealViewHolder> {
    Context context;
    ArrayList<Meal> meals;
    private static final String TAG = "RecyclerView";
    OnMealClickListener listener;
    public RandomMealAdapter(Context context, ArrayList<Meal> meals,OnMealClickListener  mealListener) {
        this.context = context;
        this.meals = meals;
        this.listener=mealListener;
    }
    @NonNull
    @Override
    public RandomMealAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view =inflater.inflate(R.layout.meal_item2,parent,false);
            MealViewHolder mealViewHolder=new MealViewHolder(view);
            Log.i(TAG, "onCreateViewHolder: ");
            return mealViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RandomMealAdapter.MealViewHolder holder, int position) {

            Meal currentMeal=meals.get(position);
            holder.mealName.setText(currentMeal.getMealName());

            Glide.with(context).load(meals.get(position).getMealImage())
                    .apply(new RequestOptions().override(270,255))
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.mealImage);
            holder.btnAddToFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMealClick(currentMeal);
                }
            });

    }
    public void setList(List<Meal> meals) {
        this.meals= (ArrayList<Meal>) meals;
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class MealViewHolder extends RecyclerView.ViewHolder{

        ImageView mealImage;
        TextView mealName;
        Button btnAddToFav;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage=itemView.findViewById(R.id.mealImage);
            mealName=itemView.findViewById(R.id.mealName);
            btnAddToFav=itemView.findViewById(R.id.btnAddToFavourite);

        }
    }
}

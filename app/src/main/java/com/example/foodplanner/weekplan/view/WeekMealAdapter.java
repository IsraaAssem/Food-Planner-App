package com.example.foodplanner.weekplan.view;

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
import com.example.foodplanner.favourites.view.FavMealAdapter;
import com.example.foodplanner.favourites.view.OnFavouriteClickListener;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.PlanMeal;

import java.util.List;

public class WeekMealAdapter  extends RecyclerView.Adapter<WeekMealAdapter.MealViewHolder>{


    Context context;
    List<PlanMeal> meals;
    private static final String TAG = "PlanMealAdapter";
    OnWeekMealClickListener listener;
    public WeekMealAdapter(){

    }
    public WeekMealAdapter(Context context,List<PlanMeal>meals,OnWeekMealClickListener listener){
        this.context=context;
        this.meals=meals;
        this.listener=listener;
    }
    @NonNull
    @Override
    public WeekMealAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =inflater.inflate(R.layout.meal_item2,parent,false);
        WeekMealAdapter.MealViewHolder mealViewHolder=new WeekMealAdapter.MealViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: ");
        return mealViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeekMealAdapter.MealViewHolder holder, int position) {
        PlanMeal currentMeal = meals.get(position);

        holder.mealName.setText(currentMeal.getMealName());
        if(currentMeal.getDate()!=null){
        holder.mealName.append("\n"+currentMeal.getDate());}
        Glide.with(context)
                .load(meals.get(position).getImage())
                .apply(new RequestOptions().override(200, 200))
                .placeholder(R.drawable.loading)
                .error(R.drawable.app_logo)
                .into(holder.mealImage);

        holder.btnRemoveFromFav.setText("Remove from plan");
        holder.btnRemoveFromFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlanListener(currentMeal);//////////////
            }
        });
    }

    @Override
    public int getItemCount() {
        if(meals==null)
            return 0;
        return meals.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<PlanMeal> getMeals() {
        return meals;
    }

    public void setMeals(List<PlanMeal> meals) {
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

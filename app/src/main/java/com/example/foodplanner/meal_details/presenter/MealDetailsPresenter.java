package com.example.foodplanner.meal_details.presenter;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.PlanMeal;

public interface MealDetailsPresenter {
    public void getMeal(int id);
    public void addToFav(Meal meal);

    public void addToWeekPlan(PlanMeal meal);
}

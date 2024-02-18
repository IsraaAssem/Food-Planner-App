package com.example.foodplanner.meal_details.presenter;

import com.example.foodplanner.model.Meal;

public interface MealDetailsPresenter {
    public void getMeal(int id);
    public void  removeFromFav(Meal meal);
}

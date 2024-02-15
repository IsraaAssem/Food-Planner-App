package com.example.foodplanner.random_meal.presenter;

import com.example.foodplanner.model.Meal;

public interface RandomMealPresenter {
    public void getMeals();
    public void addToFavourites(Meal meal);
}

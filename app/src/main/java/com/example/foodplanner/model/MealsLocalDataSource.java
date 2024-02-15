package com.example.foodplanner.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface MealsLocalDataSource {
    public void insertMeal(Meal meal);
    public void deleteMeal(Meal meal);
    public LiveData<List<Meal>> getAllMeals();
}

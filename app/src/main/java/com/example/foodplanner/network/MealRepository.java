package com.example.foodplanner.network;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;

import java.util.List;

public interface MealRepository {
    LiveData<List<Meal>> getStoredMeals();

    void getStoredMeals(NetworkCallback networkCallback);

    void insertMeal(Meal meal);

    void deleteMeal(Meal meal);
}

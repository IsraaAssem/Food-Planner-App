package com.example.foodplanner.network;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepository {
    Flowable<List<Meal>> getStoredMeals();

    void getStoredMeals(NetworkCallback networkCallback);

    void insertMeal(Meal meal);
    Single<Meal> getMealById(int id);

    void deleteMeal(Meal meal);
}

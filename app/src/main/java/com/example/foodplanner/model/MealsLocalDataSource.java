package com.example.foodplanner.model;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface MealsLocalDataSource {
    public void insertMeal(Meal meal);
    public void deleteMeal(Meal meal);
    public Flowable<List<Meal>> getAllMeals();
}

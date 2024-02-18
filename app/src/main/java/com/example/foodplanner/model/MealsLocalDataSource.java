package com.example.foodplanner.model;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsLocalDataSource {
    public void insertMeal(Meal meal);
    public void deleteMeal(Meal meal);
    public Flowable<List<Meal>> getAllMeals();
    public Single<Meal> getMealById(int id);

    public Single<Meal> getMealByDate(String date);
}

package com.example.foodplanner.model;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsLocalDataSource {
    public Completable insertMeal(Meal meal,boolean isFav);
    public void deleteMeal(Meal meal);
    public Flowable<List<Meal>> getAllMeals();
    public Single<Meal> getMealById(int id);

    public Single<Meal> getMealByDate(String date);

    void addToWeekPlan(PlanMeal planMeal);
    Flowable<List<PlanMeal>> getPlanMeals(String userEmail);

    Completable deleteFromPlan (PlanMeal planMeal);

    Flowable<List<Meal>> getFavouriteMeals();
}

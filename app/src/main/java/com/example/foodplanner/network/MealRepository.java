package com.example.foodplanner.network;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.PlanMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepository {
    Flowable<List<Meal>> getStoredMeals();

    void getStoredMeals(NetworkCallback networkCallback);

    void insertMeal(Meal meal);
    Single<Meal> getMealById(int id);

    void deleteMeal(Meal meal);

    Completable insertToWeekPlan(PlanMeal meal);
    Flowable<List<PlanMeal>> getPlanMeals(String userEmail);
    Completable deleteFromPlan (PlanMeal planMeal);
}

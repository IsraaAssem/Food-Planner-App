package com.example.foodplanner.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealsDAO {
    @Query("SELECT * FROM meals_table where userEmail=:userEmail")
    Flowable<List<Meal>> getFavouriteMeals(String userEmail);
    @Query("SELECT * FROM meals_table where idMeal=:id")
    Single<Meal> getMealById(int id);
    @Query("SELECT * FROM meals_table where createdAt=:date")
    Single<Meal> getMealByDate(String date);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(Meal meal);

    @Delete
    Completable deleteMeal (Meal meal);


}

package com.example.foodplanner.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealsDAO {
    @Query("SELECT * FROM meals_table")
    Flowable<List<Meal>> getMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal(Meal meal);
    @Delete
    Completable deleteMeal (Meal meal);
}

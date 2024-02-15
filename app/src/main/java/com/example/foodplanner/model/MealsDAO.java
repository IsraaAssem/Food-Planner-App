package com.example.foodplanner.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealsDAO {
    @Query("SELECT * FROM meals_table")
    LiveData<List<Meal>> getMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(Meal meal);
    @Delete
    void deleteMeal (Meal meal);
}

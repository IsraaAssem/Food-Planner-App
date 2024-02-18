package com.example.foodplanner.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface WeekPlanDAO {
    @Query("SELECT * FROM plan_meals_table where userEmail=:userEmail")
    Flowable<List<PlanMeal>> getPlanMeals(String userEmail);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertToPlan(PlanMeal planMeal);
    @Delete
    Completable deleteFromPlan (PlanMeal planMeal);
}

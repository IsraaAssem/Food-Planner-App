package com.example.foodplanner.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MealLocalDataSourceImpl implements MealsLocalDataSource{
    private LiveData<List<Meal>>storedMeals;
    private MealsDAO dao;
    private static MealLocalDataSourceImpl localSource=null;
    public static MealLocalDataSourceImpl getInstance(Context context){
        if(localSource==null){
            localSource=new MealLocalDataSourceImpl(context);
        }
        return localSource;
    }
    private  MealLocalDataSourceImpl(Context context){
        MealsDB db=MealsDB.getInstance(context.getApplicationContext());
        dao=db.getMealDAO();
        storedMeals=dao.getMeals();
    }

    @Override
    public void insertMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.insertMeal(meal);
            }
        }).start();
    }

    @Override
    public void deleteMeal(Meal meal) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                dao.deleteMeal(meal);
            }
        }).start();
    }

    @Override
    public LiveData<List<Meal>> getAllMeals() {
        return storedMeals;
    }
}

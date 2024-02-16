package com.example.foodplanner.network;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealsLocalDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class MealRepositoryImpl implements MealRepository{
    private MealRemoteDataSource  mealRemoteDataSource;
    private MealsLocalDataSource mealsLocalDataSource;
    private static MealRepositoryImpl repo=null;
    private MealRepositoryImpl(MealRemoteDataSource  mealRemoteDataSource,MealsLocalDataSource mealsLocalDataSource){
        this.mealRemoteDataSource=mealRemoteDataSource;
        this.mealsLocalDataSource=mealsLocalDataSource;
    }
    public static MealRepositoryImpl getInstance(MealRemoteDataSource  mealRemoteDataSource,MealsLocalDataSource mealsLocalDataSource){
        if(repo==null){
            repo=new MealRepositoryImpl(  mealRemoteDataSource, mealsLocalDataSource);
        }
        return repo;
    }

    @Override
    public Flowable<List<Meal>> getStoredMeals() {
        return mealsLocalDataSource.getAllMeals();
    }

    @Override
    public void getStoredMeals(NetworkCallback networkCallback) {
        mealRemoteDataSource.makeNetworkCall(networkCallback);
    }

    @Override
    public void insertMeal(Meal meal) {
        mealsLocalDataSource.insertMeal(meal);
        Log.i("TAG", "insert Meal: ");
    }

    @Override
    public void deleteMeal(Meal meal) {
        mealsLocalDataSource.deleteMeal(meal);
    }
}

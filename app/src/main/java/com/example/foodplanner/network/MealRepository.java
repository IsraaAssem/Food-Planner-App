package com.example.foodplanner.network;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.PlanMeal;
import com.example.foodplanner.search.presenter.SearchPresenterImpl;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepository {
    Flowable<List<Meal>> getStoredMeals();

    void getStoredMeals(NetworkCallback networkCallback);
    void getMealsByCategory(String Category,NetworkCallback networkCallback);
    void getCategories(SearchCallback networkCallback);
    void getCountries(SearchCallback networkCallback);

    void insertMeal(Meal meal,boolean isFav);
    Single<Meal> getMealById(int id);

    void deleteMeal(Meal meal);

    void insertToWeekPlan(PlanMeal meal);
    Flowable<List<PlanMeal>> getPlanMeals(String userEmail);
    Completable deleteFromPlan (PlanMeal planMeal);

    Flowable<List<Meal>> getFavouriteMeals();

    void getIngredients(SearchCallback networkCallback);
}

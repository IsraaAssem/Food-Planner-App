package com.example.foodplanner.random_meal.view;

import com.example.foodplanner.model.Meal;

import java.util.List;

public interface RandomMealView {
    public void ShowMeals(List<Meal> meals);
    public void showErrMsg(String errMsg);
}

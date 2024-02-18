package com.example.foodplanner.meal_details.view;

import com.example.foodplanner.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


public interface MealDetailsView {
    public void showData(Single<Meal> meal);
    public void showErrMsg(String error);
}

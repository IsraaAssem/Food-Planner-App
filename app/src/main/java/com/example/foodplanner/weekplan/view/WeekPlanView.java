package com.example.foodplanner.weekplan.view;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.PlanMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface WeekPlanView {
    public void showData(Flowable<List<PlanMeal>> meals);
    public void showErrMsg(String error);
}

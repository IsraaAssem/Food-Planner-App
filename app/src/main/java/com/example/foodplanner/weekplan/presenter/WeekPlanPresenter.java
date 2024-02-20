package com.example.foodplanner.weekplan.presenter;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.PlanMeal;

public interface WeekPlanPresenter {
    public void getPlanMeals();
    public void  removeFromPlan(PlanMeal meal);
}

package com.example.foodplanner.random_meal.presenter;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.network.MealRepository;
import com.example.foodplanner.network.NetworkCallback;
import com.example.foodplanner.random_meal.view.RandomMealView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class RandomMealPresenterImpl implements RandomMealPresenter, NetworkCallback {
    RandomMealView pView;
    MealRepository repo;
    public RandomMealPresenterImpl(RandomMealView pView,MealRepository repo){
        this.pView=pView;
        this.repo=repo;
    }
    @Override
    public void getMeals() {
        repo.getStoredMeals(this);
    }
    @Override
    public void addToFavourites(Meal meal) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            meal.setUserEmail(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }else{
            meal.setUserEmail("");
        }
        repo.insertMeal(meal);
    }
    @Override
    public void onSuccessResult(List<Meal> meals) {
        pView.ShowMeals(meals);
    }
    @Override
    public void onFailureResult(String errMsg) {
        pView.showErrMsg(errMsg);
    }
}

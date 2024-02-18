package com.example.foodplanner.meal_details.presenter;

import com.example.foodplanner.favourites.view.FavView;
import com.example.foodplanner.meal_details.view.MealDetailsView;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.PlanMeal;
import com.example.foodplanner.network.MealRepository;

public class MealDetailsPresenterImpl implements MealDetailsPresenter {
    private MealRepository _repo;
    private MealDetailsView _view;

    public MealDetailsPresenterImpl( MealDetailsView _view,MealRepository _repo) {
        this._repo = _repo;
        this._view = _view;
    }

    @Override
    public void getMeal(int id) {
        _view.showData(_repo.getMealById(id));
    }

    @Override
    public void addToFav(Meal meal) {
        _repo.insertMeal(meal);
    }

    @Override
    public void addToWeekPlan(PlanMeal meal) {
         _repo.insertToWeekPlan(meal);
    }


}



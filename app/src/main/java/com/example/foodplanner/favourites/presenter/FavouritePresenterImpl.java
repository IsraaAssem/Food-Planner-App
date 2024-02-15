package com.example.foodplanner.favourites.presenter;

import com.example.foodplanner.favourites.view.FavView;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.network.MealRepository;

public class FavouritePresenterImpl implements FavouritePresenter{
    private FavView _view;
    private MealRepository _repo;

    public FavouritePresenterImpl(FavView _view, MealRepository _repo) {
        this._view = _view;
        this._repo = _repo;
    }
    @Override
    public void getFavMeals() {
        _view.showData(_repo.getStoredMeals());
    }

    @Override
    public void removeFromFav(Meal meal) {
          _repo.deleteMeal(meal);
    }
}

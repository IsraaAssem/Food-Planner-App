package com.example.foodplanner.favourites.presenter;

import com.example.foodplanner.model.Meal;

public interface FavouritePresenter {
    public void getFavMeals();
    public void  removeFromFav(Meal meal);
}

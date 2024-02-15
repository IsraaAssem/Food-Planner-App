package com.example.foodplanner.favourites.view;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;

import java.util.List;

public interface FavView {
    public void showData(LiveData<List<Meal>> meals);
    public void showErrMsg(String error);
}

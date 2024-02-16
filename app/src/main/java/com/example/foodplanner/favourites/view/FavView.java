package com.example.foodplanner.favourites.view;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface FavView {
    public void showData(Flowable<List<Meal>> meals);
    public void showErrMsg(String error);
}

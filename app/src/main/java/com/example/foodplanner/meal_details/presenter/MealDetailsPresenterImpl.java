package com.example.foodplanner.meal_details.presenter;

import android.widget.Toast;

import com.example.foodplanner.favourites.view.FavView;
import com.example.foodplanner.meal_details.view.MealDetailsView;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.PlanMeal;
import com.example.foodplanner.network.MealRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
        _repo.insertMeal(meal,true);
    }

    @Override
    public void addToWeekPlan(PlanMeal meal) {
        System.out.println(meal);

         _repo.insertToWeekPlan(meal);
//                 .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
//                 () -> {
//
//                     //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                 },
//                 throwable -> {
//                     System.out.println("meal esraa");
//                     throwable.printStackTrace();
//                 }
//         );
    }


}



package com.example.foodplanner.weekplan.presenter;

import com.example.foodplanner.favourites.view.FavView;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.PlanMeal;
import com.example.foodplanner.network.MealRepository;
import com.example.foodplanner.network.MealRepositoryImpl;
import com.example.foodplanner.weekplan.view.WeekPlanView;
import com.example.foodplanner.weekplan.view.weekPlanFragment;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WeekPlanPresenterImpl implements  WeekPlanPresenter{
    private WeekPlanView _view;
    private MealRepository _repo;

    public WeekPlanPresenterImpl(WeekPlanView _view, MealRepository _repo) {
        this._view=_view;
        this._repo=_repo;
    }

    @Override
    public void getPlanMeals() {
        String email="";
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            email=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        _view.showData(_repo.getPlanMeals(email));
    }

    @Override
    public void removeFromPlan(PlanMeal meal) {
        _repo.deleteFromPlan(meal).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(

        );
    }
}

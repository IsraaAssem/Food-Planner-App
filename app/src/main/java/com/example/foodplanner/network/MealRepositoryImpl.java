package com.example.foodplanner.network;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealsLocalDataSource;
import com.example.foodplanner.model.PlanMeal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealRepositoryImpl implements MealRepository {
    private MealRemoteDataSource mealRemoteDataSource;
    private MealsLocalDataSource mealsLocalDataSource;
    private static MealRepositoryImpl repo = null;

    private MealRepositoryImpl(MealRemoteDataSource mealRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        this.mealRemoteDataSource = mealRemoteDataSource;
        this.mealsLocalDataSource = mealsLocalDataSource;
    }

    public static MealRepositoryImpl getInstance(MealRemoteDataSource mealRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        if (repo == null) {
            repo = new MealRepositoryImpl(mealRemoteDataSource, mealsLocalDataSource);
        }
        return repo;
    }

    @Override
    public Flowable<List<Meal>> getStoredMeals() {
        return mealsLocalDataSource.getAllMeals();
    }

    @Override
    public void getStoredMeals(NetworkCallback networkCallback) {
        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyy-MMM-dd").format(date);
        Log.e("MealTodayDate",dateString);
        mealsLocalDataSource.getMealByDate(dateString).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<Meal>() {
            void getRemoteMeals(){

                mealRemoteDataSource.makeNetworkCall(new NetworkCallback() {
                    @Override
                    public void onSuccessResult(List<Meal> meals) {

                        if (meals != null && meals.size() > 0) {
                            meals.get(0).setCreatedAt(dateString);
                            insertMeal(meals.get(0));
                        }
                        networkCallback.onSuccessResult(meals);
                    }

                    @Override
                    public void onFailureResult(String errMsg) {
                        networkCallback.onFailureResult(errMsg);
                    }
                });
            }
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull Meal meal) {
                if (meal != null)
                {
                    ArrayList<Meal> meals = new ArrayList<Meal>();
                    meals.add(meal);
                    networkCallback.onSuccessResult(meals);
                }else{
                    getRemoteMeals();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getRemoteMeals();
                e.printStackTrace();
            }
        });
    }

    @Override
    public void insertMeal(Meal meal) {
        mealsLocalDataSource.insertMeal(meal);
        Log.i("TAG", "insert Meal: ");
    }

    @Override
    public Single<Meal> getMealById(int id) {
        return mealsLocalDataSource.getMealById(id);
    }

    @Override
    public void deleteMeal(Meal meal) {
        mealsLocalDataSource.deleteMeal(meal);
    }

    @Override
    public Completable insertToWeekPlan(PlanMeal meal) {
        return mealsLocalDataSource.addToWeekPlan(meal);
    }

    @Override
    public Flowable<List<PlanMeal>> getPlanMeals(String userEmail) {
        return mealsLocalDataSource.getPlanMeals(userEmail);
    }

    @Override
    public Completable deleteFromPlan(PlanMeal planMeal) {
        return mealsLocalDataSource.deleteFromPlan(planMeal);
    }

//    @Override
//    public void insertToWeekPlan(PlanMeal planMeal) {
//        mealsLocalDataSource.addToWeekPlan(planMeal);
//    }
}

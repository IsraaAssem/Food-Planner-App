package com.example.foodplanner.network;

import android.util.Log;
import android.widget.Toast;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
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
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
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
                            insertMeal(meals.get(0),false);
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
    public void getMealsByCategory(String category,NetworkCallback networkCallback) {


        mealRemoteDataSource.makeNetworkCall(category ,new NetworkCallback() {
            @Override
            public void onSuccessResult(List<Meal> meals) {

                networkCallback.onSuccessResult(meals);
            }

            @Override
            public void onFailureResult(String errMsg) {
                networkCallback.onFailureResult(errMsg);
            }
        });
    }



    @Override
    public void getCategories(SearchCallback networkCallback) {
        mealRemoteDataSource.getCategories(new SearchCallback() {
            @Override
            public void onSuccessCategoryResult(List<Category> categories) {
                networkCallback.onSuccessCategoryResult(categories);
            }

            @Override
            public void onSuccessCountriesResult(List<Country> categories) {

            }
            @Override
            public void onFailureCategoryResult(String errMsg) {
                networkCallback.onFailureCategoryResult(errMsg);
            }

        });
    }
    @Override
    public void getCountries(SearchCallback networkCallback) {
        mealRemoteDataSource.getCountries(new SearchCallback() {
            @Override
            public void onSuccessCategoryResult(List<Category> categories) {
                networkCallback.onSuccessCategoryResult(categories);
            }

            @Override
            public void onSuccessCountriesResult(List<Country> categories) {
                networkCallback.onSuccessCountriesResult(categories);

            }

            @Override
            public void onFailureCategoryResult(String errMsg) {
                networkCallback.onFailureCategoryResult(errMsg);
            }

        });
    }

    @Override
    public void insertMeal(Meal meal,boolean isFav) {
        mealsLocalDataSource.insertMeal(meal,isFav).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnError(throwable -> {
            throwable.printStackTrace();
           // Toast.makeText(, "Error inserting meals!", Toast.LENGTH_SHORT).show();
        }).subscribe(new Action() {
            @Override
            public void run() throws Throwable {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                throwable.printStackTrace();
            }
        });
        Log.i("TAG", "insert Meal: ");
    }

    @Override
    public Single<Meal> getMealById(int id) {
        return mealsLocalDataSource.getMealById(id);
                //.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

//                .doOnError(throwable -> {
//            throwable.printStackTrace();
//            //Toast.makeText(context, "Error retrieving meals!", Toast.LENGTH_SHORT).show();
//        });
    }

    @Override
    public void deleteMeal(Meal meal) {
        mealsLocalDataSource.deleteMeal(meal);
//        .doOnError(throwable -> {
//           throwable.printStackTrace();
//          // Toast.makeText(context, "Error retrieving meals!", Toast.LENGTH_SHORT).show();
//       });
    }

    @Override
    public void insertToWeekPlan(PlanMeal meal) {
        mealsLocalDataSource.addToWeekPlan(meal);
    }

    @Override
    public Flowable<List<PlanMeal>> getPlanMeals(String userEmail) {
        return mealsLocalDataSource.getPlanMeals(userEmail);
    }

    @Override
    public Flowable<List<Meal>> getFavouriteMeals() {
        return mealsLocalDataSource.getFavouriteMeals();
    }

    @Override
    public Completable deleteFromPlan(PlanMeal planMeal) {
        return mealsLocalDataSource.deleteFromPlan(planMeal);
    }


}

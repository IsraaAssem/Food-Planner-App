package com.example.foodplanner.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealLocalDataSourceImpl implements MealsLocalDataSource{
    private Flowable<List<Meal>> storedMeals;
    private MealsDAO dao;
    Context context;
    private static MealLocalDataSourceImpl localSource=null;
    public static MealLocalDataSourceImpl getInstance(Context context){
        if(localSource==null){
            localSource=new MealLocalDataSourceImpl(context);
        }
        return localSource;
    }
    private  MealLocalDataSourceImpl(Context context){
        MealsDB db=MealsDB.getInstance(context.getApplicationContext());
        dao=db.getMealDAO();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Log.e("FoodUser",FirebaseAuth.getInstance().getCurrentUser().getEmail()+":"+FirebaseAuth.getInstance().getCurrentUser().getUid());
            storedMeals = dao.getFavouriteMeals(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }else{
            storedMeals = dao.getFavouriteMeals("");
        }

        this.context= context;
    }

    @Override
    public void insertMeal(Meal meal) {
        dao.insertMeal(meal).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
//                ()->{ Toast.makeText(context, "Meal inserted Successfully!", Toast.LENGTH_SHORT).show();},
                ()->{},
                (Throwable throwable)-> {
                    throwable.printStackTrace();
                    Toast.makeText(context, "Error inserting meal!", Toast.LENGTH_SHORT).show();
                }
        );
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                dao.insertMeal(meal);
//            }
//        }).start();
    }

    @Override
    public void deleteMeal(Meal meal) {
        dao.deleteMeal(meal).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                ()->{},
                (Throwable throwable)-> Toast.makeText(context, "Error deleting meal!", Toast.LENGTH_SHORT).show()
        );
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//               dao.deleteMeal(meal);
//            }
//        }).start();
    }

    @Override
    public Flowable<List<Meal>> getAllMeals() {
        return storedMeals;
    }

    @Override
    public Single<Meal> getMealById(int id) {
        return dao.getMealById(id);
    }
    @Override
    public Single<Meal> getMealByDate(String date) {
        return dao.getMealByDate(date);
    }
}

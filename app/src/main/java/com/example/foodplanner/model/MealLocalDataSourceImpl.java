package com.example.foodplanner.model;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlinx.coroutines.flow.Flow;

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
        storedMeals=dao.getMeals();
        this.context= context;
    }

    @Override
    public void insertMeal(Meal meal) {
        dao.insertMeal(meal).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                ()->{},
                (Throwable throwable)-> Toast.makeText(context, "Error inserting meal!", Toast.LENGTH_SHORT).show()
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
}

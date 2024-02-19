package com.example.foodplanner.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealLocalDataSourceImpl implements MealsLocalDataSource{
    private Flowable<List<Meal>> storedMeals;
    private Flowable<List<PlanMeal>> weekPlanMeals;
    private MealsDAO dao;
    private WeekPlanDAO weekPlanDAO;
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
        weekPlanDAO=db.getWeekPlanDAO();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Log.e("FoodUser",FirebaseAuth.getInstance().getCurrentUser().getEmail()+":"+FirebaseAuth.getInstance().getCurrentUser().getEmail());
            storedMeals = dao.getFavouriteMeals(FirebaseAuth.getInstance().getCurrentUser().getEmail()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            weekPlanMeals=weekPlanDAO.getPlanMeals(FirebaseAuth.getInstance().getCurrentUser().getEmail()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }else{
            weekPlanMeals = weekPlanDAO.getPlanMeals("").subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            storedMeals = dao.getFavouriteMeals("").subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        this.context= context;
    }

    @Override
    public Completable insertMeal(Meal meal,boolean isFav) {
        if (isFav){
            if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                meal.setUserEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            else meal.setUserEmail("");
        }
      return dao.insertMeal(meal).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
              .doOnError(throwable -> {
            throwable.printStackTrace();
            Toast.makeText(context, "Error adding meals!", Toast.LENGTH_SHORT).show();
        });
//                .subscribe(
//                ()->{ Toast.makeText(context, "Meal inserted Successfully!", Toast.LENGTH_SHORT).show();}
//                ,
//                (Throwable throwable)-> {
//                    throwable.printStackTrace();
//                    Toast.makeText(context, "Error inserting meal!", Toast.LENGTH_SHORT).show();
//                }
//        );
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                dao.insertMeal(meal);
//            }
//        }).start();
    }

    @Override
    public void deleteMeal(Meal meal) {
          dao.deleteMeal(meal).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnError(throwable -> {
             throwable.printStackTrace();
             Toast.makeText(context, "Error deleting meal!", Toast.LENGTH_SHORT).show();
         }).subscribe(
                ()->{},
                (Throwable throwable)-> {
                    Toast.makeText(context, "Error deleting meal!", Toast.LENGTH_SHORT).show()
                            ;
                    throwable.printStackTrace();
                }
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
        return storedMeals.observeOn(AndroidSchedulers.mainThread()).doOnError(throwable -> {
            throwable.printStackTrace();
            Toast.makeText(context, "Error retrieving meals!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public Single<Meal> getMealById(int id) {
        return dao.getMealById(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnError(throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(context, "Error retrieving meal!", Toast.LENGTH_SHORT).show();
                });
    }
    @Override
    public Single<Meal> getMealByDate(String date) {
        return dao.getMealByDate(date);
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe().doOnError(throwable -> {
//                    throwable.printStackTrace();
//                    Toast.makeText(context, "Error retrieving meal!", Toast.LENGTH_SHORT).show();
//                });
    }
//    @Override
//    public Single<Meal> getMealByDate(String date) {
//        return dao.getMealByDate(date);
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread()).subscribe().doOnError(throwable -> {
////                    throwable.printStackTrace();
////                    Toast.makeText(context, "Error retrieving meal!", Toast.LENGTH_SHORT).show();
////                });
//    }

    @Override
    public void addToWeekPlan(PlanMeal planMeal) {
         weekPlanDAO.insertToPlan(planMeal).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Action() {
                    @Override
                    public void run() throws Throwable {
                        Toast.makeText(context, "Plan Meal was added Successfully!", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Toast.makeText(context, "Error adding meal!", Toast.LENGTH_SHORT).show();
                        throwable.printStackTrace();
                    }
                }
        );
    }

    @Override
    public Flowable<List<PlanMeal>> getPlanMeals(String userEmail) {
        return weekPlanMeals.observeOn(AndroidSchedulers.mainThread()).doOnError(throwable -> {
            throwable.printStackTrace();
            Toast.makeText(context, "Error retrieving meals!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public Flowable<List<Meal>> getFavouriteMeals() {
        String email = "";
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        return dao.getFavouriteMeals(email).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteFromPlan(PlanMeal planMeal) {
        return weekPlanDAO.deleteFromPlan(planMeal).observeOn(AndroidSchedulers.mainThread()).doOnError(throwable -> {
            throwable.printStackTrace();
            Toast.makeText(context, "Error deleting meals!", Toast.LENGTH_SHORT).show();
        });
//        .subscribe(
//                ()->{Toast.makeText(context, "Plan Meal was deleted Successfully!", Toast.LENGTH_SHORT).show();},
//                (Throwable error)->{Toast.makeText(context, "Error deleting meal!", Toast.LENGTH_SHORT).show();
//                    error.printStackTrace();}
//        );

    }


}

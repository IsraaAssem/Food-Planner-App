package com.example.foodplanner.network;

import android.util.Log;

import com.example.foodplanner.model.Meals;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSourceImpl implements MealRemoteDataSource{

    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static final String TAG = "MealClient";
    private MealService mealService;
    private static MealRemoteDataSourceImpl client=null;
    public static MealRemoteDataSourceImpl getInstance(){
        if(client==null){
            client=new MealRemoteDataSourceImpl();
        }
        return client;
    }
    private  MealRemoteDataSourceImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);

    }
    @Override
    public void makeNetworkCall(NetworkCallback networkCallback) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MealService mealService = retrofit.create(MealService.class);
        Call<Meals> call = mealService.getMeals();
        call.enqueue(new Callback<Meals>() {
            @Override
            public void onResponse(Call<Meals> call, Response<Meals> response) {
                if (response.isSuccessful()) {
                    Meals meals = response.body();

                    networkCallback.onSuccessResult(meals.getMeals());
                } else {
                    Log.e(TAG, "Failed to fetch data: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Meals> call, Throwable t) {
                Log.e(TAG, "Retrofit onFailure: " + t.getMessage());////
                networkCallback.onFailureResult(t.getMessage());
            }
        });

    }
}

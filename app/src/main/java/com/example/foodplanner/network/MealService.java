package com.example.foodplanner.network;

import com.example.foodplanner.model.Meals;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {

        @GET("random.php")
        Call<Meals> getMeals();
}

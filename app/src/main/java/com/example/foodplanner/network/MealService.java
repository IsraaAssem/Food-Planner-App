package com.example.foodplanner.network;
import com.example.foodplanner.model.Meals;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface MealService {
        @GET("random.php")
        Observable<Meals> getMeals();
}

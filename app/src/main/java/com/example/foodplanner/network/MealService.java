package com.example.foodplanner.network;
import com.example.foodplanner.model.CategoriesResponse;
import com.example.foodplanner.model.Countries;
import com.example.foodplanner.model.Meals;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
        @GET("random.php")
        Observable<Meals> getMeals();
        @GET("filter.php")
        Observable<Meals> getMeals(@Query("c")String category);
        @GET("categories.php")
        Observable<CategoriesResponse> getCategories();
        @GET("list.php?a=list")
        Observable<Countries> getCountries();
        @GET("lookup.php?i=52772")
        Observable<Meals> getMealDetails();
}

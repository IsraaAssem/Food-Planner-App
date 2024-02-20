package com.example.foodplanner.network;
import android.annotation.SuppressLint;
import android.util.Log;

import com.example.foodplanner.model.CategoriesResponse;
import com.example.foodplanner.model.Countries;
import com.example.foodplanner.model.Ingredients;
import com.example.foodplanner.model.Meals;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealService = retrofit.create(MealService.class);

    }
    @Override
    public void makeNetworkCall(NetworkCallback networkCallback) {
        Observable<Meals> call = mealService.getMeals();
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                item->{
                    networkCallback.onSuccessResult( item.getMeals());
                },
                error->  {Log.e(TAG, "Failed to fetch data: " + error.getMessage());
                    networkCallback.onFailureResult(error.getMessage());
                }
        );

    }
    @Override
    public void makeNetworkCall(String category,NetworkCallback networkCallback) {

        Observable<Meals> call = mealService.getMeals(category);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                item->{
                    networkCallback.onSuccessResult( item.getMeals());
                },
                error->  {Log.e(TAG, "Failed to fetch data: " + error.getMessage());
                    networkCallback.onFailureResult(error.getMessage());
                }
        );

    }

    @SuppressLint("CheckResult")
    @Override
    public void getCategories(SearchCallback networkCallback) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                .build();
//        MealService mealService = retrofit.create(MealService.class);
        Observable<CategoriesResponse> call = mealService.getCategories();
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                item->{
                    networkCallback.onSuccessCategoryResult(  item.getCategories());
                },
                error->  {Log.e(TAG, "Failed to fetch data: " + error.getMessage());
                    networkCallback.onFailureCategoryResult(error.getMessage());
                }
        );
    }
    @SuppressLint("CheckResult")
    @Override
    public void getCountries(SearchCallback networkCallback) {
        Observable<Countries> call = mealService.getCountries();
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                item->{
                    networkCallback.onSuccessCountriesResult(  item.getCountries());
                },
                error->  {Log.e(TAG, "Failed to fetch data: " + error.getMessage());
                    networkCallback.onFailureCategoryResult(error.getMessage());
                }
        );
    }

    @Override
    public void getIngredients(SearchCallback networkCallback) {
        Observable<Ingredients> call = mealService.getIngredients();
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                item->{
                    networkCallback.onSuccessIngredientsResult(  item.getIngredients());
                },
                error->  {Log.e(TAG, "Failed to fetch data: " + error.getMessage());
                    networkCallback.onFailureCategoryResult(error.getMessage());
                }
        );
    }
}

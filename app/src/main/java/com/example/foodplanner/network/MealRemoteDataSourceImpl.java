package com.example.foodplanner.network;
import android.util.Log;
import com.example.foodplanner.model.Meals;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        MealService mealService = retrofit.create(MealService.class);
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
}

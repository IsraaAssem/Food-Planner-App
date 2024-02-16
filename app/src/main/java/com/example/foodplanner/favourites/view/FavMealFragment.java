package com.example.foodplanner.favourites.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.favourites.presenter.FavouritePresenter;
import com.example.foodplanner.favourites.presenter.FavouritePresenterImpl;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealLocalDataSourceImpl;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;
import com.example.foodplanner.network.MealRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavMealFragment extends Fragment  implements OnFavouriteClickListener, FavView  {

    RecyclerView favRv;
    FavMealAdapter favMealAdapter;
    FavouritePresenter favPresenter;
    LinearLayoutManager linearLayoutManager;
    public FavMealFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager=new LinearLayoutManager(getContext());
        favMealAdapter=new FavMealAdapter(getContext(),new ArrayList<>(),this);
        favPresenter = new FavouritePresenterImpl(this , MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(),
                MealLocalDataSourceImpl.getInstance(getContext())
        ));
        //linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        favRv=view.findViewById(R.id.favRv);
        favRv.setLayoutManager(linearLayoutManager);
        favRv.setAdapter(favMealAdapter);
        favPresenter.getFavMeals();
    }

    @Override
    public void showData(Flowable<List<Meal>> meals) {
        meals.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                mealList->{
                    favMealAdapter.setMeals(mealList);
                    favMealAdapter.notifyDataSetChanged();
        }
        );
//        meals.observe(this, new Observer<List<Meal>>() {
//            @Override
//            public void onChanged(List<Meal> meals) {
//                favMealAdapter.setMeals(meals);
//                favMealAdapter.notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public void showErrMsg(String error) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setMessage(error).setTitle("An error occurred");
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    @Override
    public void onFavListener(Meal meal) {
        favPresenter.removeFromFav(meal);
        Toast.makeText(getContext(),"Removed from favourites" , Toast.LENGTH_LONG).show();

    }
}
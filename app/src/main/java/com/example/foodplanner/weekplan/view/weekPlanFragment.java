package com.example.foodplanner.weekplan.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.favourites.presenter.FavouritePresenter;
import com.example.foodplanner.favourites.presenter.FavouritePresenterImpl;
import com.example.foodplanner.favourites.view.FavMealAdapter;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealLocalDataSourceImpl;
import com.example.foodplanner.model.PlanMeal;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;
import com.example.foodplanner.network.MealRepositoryImpl;
import com.example.foodplanner.weekplan.presenter.WeekPlanPresenter;
import com.example.foodplanner.weekplan.presenter.WeekPlanPresenterImpl;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class weekPlanFragment extends Fragment implements WeekPlanView,OnWeekMealClickListener{
    RecyclerView favRv;
    WeekMealAdapter weekMealAdapter;
    WeekPlanPresenter weekPlanPresenter;
    LinearLayoutManager linearLayoutManager;

    public weekPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_plan, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Toast.makeText(getContext(), "Login to get all features", Toast.LENGTH_SHORT).show();
        }
        else{
        linearLayoutManager = new LinearLayoutManager(getContext());
        weekMealAdapter = new WeekMealAdapter(getContext(), new ArrayList<>(), this);
        weekPlanPresenter = new WeekPlanPresenterImpl(this, MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(),
                MealLocalDataSourceImpl.getInstance(getContext())
        ));
        //linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        favRv = view.findViewById(R.id.favRv);
        favRv.setLayoutManager(linearLayoutManager);
        favRv.setAdapter(weekMealAdapter);
        weekPlanPresenter.getPlanMeals();}
    }

    @Override
    public void onPlanListener(PlanMeal meal) {
        weekPlanPresenter.removeFromPlan(meal);
        Toast.makeText(getContext(), "Removed from Plan", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showData(Flowable<List<PlanMeal>> meals) {

            if(meals != null) {
                meals.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                        mealList -> {
                            weekMealAdapter.setMeals(mealList);
                            weekMealAdapter.notifyDataSetChanged();
                        }
                );
            }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(error).setTitle("An error occurred");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
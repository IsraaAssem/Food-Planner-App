package com.example.foodplanner.random_meal.view;

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
import android.widget.Button;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealLocalDataSourceImpl;
import com.example.foodplanner.network.MealRemoteDataSourceImpl;
import com.example.foodplanner.network.MealRepositoryImpl;
import com.example.foodplanner.random_meal.presenter.RandomMealPresenter;
import com.example.foodplanner.random_meal.presenter.RandomMealPresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnMealClickListener,RandomMealView {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    RandomMealAdapter randomMealAdapter;
    RandomMealPresenter randomMealPresenter;
    Button addToFavourites;
    String category;
    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null && !getArguments().getString("category","").isEmpty())
            category = getArguments().getString("category");
        linearLayoutManager = new LinearLayoutManager(getContext());
        randomMealAdapter = new RandomMealAdapter(getContext(), new ArrayList<>(),this);
        recyclerView=view.findViewById(R.id.mealRecyclerView);
        randomMealPresenter=new RandomMealPresenterImpl(this, MealRepositoryImpl.getInstance(MealRemoteDataSourceImpl.getInstance(), MealLocalDataSourceImpl.getInstance(getContext())));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(randomMealAdapter);
        if (category != null && !category.isEmpty()){
            randomMealPresenter.getMealsByCategory(category);
        }
        else randomMealPresenter.getMeals();
    }

    @Override
    public void onMealClick(Meal meal) {
        randomMealPresenter.addToFavourites(meal);
        Toast.makeText(getContext(),"Added to Favourites." , Toast.LENGTH_LONG).show();

    }

    @Override
    public void ShowMeals(List<Meal> meals) {
        randomMealAdapter.setList(meals);
        randomMealAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrMsg(String errMsg) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(errMsg).setTitle("Error:NOT connected to Network");
            AlertDialog dialog = builder.create();
            dialog.show();
    }
}
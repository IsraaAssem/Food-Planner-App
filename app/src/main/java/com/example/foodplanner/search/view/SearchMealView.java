package com.example.foodplanner.search.view;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Meal;

import java.util.List;

public interface SearchMealView {
    public void ShowMeals(List<Category> categories);
    public void showCountries(List<Country> categories);
    public void showErrMsg(String errMsg);
}

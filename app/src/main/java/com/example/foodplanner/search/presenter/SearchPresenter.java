package com.example.foodplanner.search.presenter;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.Ingredients;

public interface SearchPresenter {
    public void getCategories();
    public void getCountries();
    public void viewMoreDetails(Category meal);
    public void viewMoreDetails(Ingredient ingredient);
    public void viewMoreDetails(Country country);

    void getIngredients();
}

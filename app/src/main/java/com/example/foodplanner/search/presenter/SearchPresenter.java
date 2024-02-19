package com.example.foodplanner.search.presenter;

import com.example.foodplanner.model.Category;

public interface SearchPresenter {
    public void getCategories();
    public void getCountries();
    public void viewMoreDetails(Category meal);
}

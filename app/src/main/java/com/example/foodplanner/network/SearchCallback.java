package com.example.foodplanner.network;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;

import java.util.List;

public interface SearchCallback {
    public void onSuccessCategoryResult(List<Category> categories);
    public void onSuccessCountriesResult(List<Country> categories);
    public void onFailureCategoryResult(String errMsg);
}

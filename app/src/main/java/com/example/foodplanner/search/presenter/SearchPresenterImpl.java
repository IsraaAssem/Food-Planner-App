package com.example.foodplanner.search.presenter;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.network.SearchCallback;
import com.example.foodplanner.network.MealRepository;
import com.example.foodplanner.search.view.SearchMealView;

import java.util.List;

public class SearchPresenterImpl implements SearchPresenter, SearchCallback {
    SearchMealView pView;
    MealRepository repo;
    private List<Category> categories;
    private List<Country> countries;

    public SearchPresenterImpl(SearchMealView pView, MealRepository repo) {
        this.pView = pView;
        this.repo = repo;
    }



    @Override
    public void getCategories() {
        if (categories != null && categories.size()>0)
            onSuccessCategoryResult(categories);
        else repo.getCategories(this);
    }

    @Override
    public void getCountries() {
        if (countries != null && countries.size()>0)
            onSuccessCountriesResult(countries);
        else repo.getCountries(this);
    }

    @Override
    public void viewMoreDetails(Category meal) {

    }

    @Override
    public void onSuccessCategoryResult(List<Category> categories) {
        this.categories = categories;
        pView.ShowMeals(categories);
    }
    @Override
    public void onSuccessCountriesResult(List<Country> countries) {
        this.countries = countries;
        pView.showCountries(countries);
    }

    @Override
    public void onFailureCategoryResult(String errMsg) {
        pView.showErrMsg(errMsg);
    }
}

package com.example.foodplanner.search.view;

import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.Country;
import com.example.foodplanner.model.Ingredient;

public interface OnItemClickListener {
    public void onCategoryClick(Category category);
    public void onCountyClick(Country country);
    public void onIngredientClick(Ingredient ingredient);
}

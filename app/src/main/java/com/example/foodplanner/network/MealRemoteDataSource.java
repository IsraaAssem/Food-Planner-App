package com.example.foodplanner.network;

public interface MealRemoteDataSource {
    void makeNetworkCall(NetworkCallback networkCallback);
    void makeNetworkCall(String category,NetworkCallback networkCallback);
    void getCategories(SearchCallback networkCallback);
    void getCountries(SearchCallback networkCallback);
}

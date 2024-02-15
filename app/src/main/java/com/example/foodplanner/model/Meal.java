package com.example.foodplanner.model;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "meals_table")
public class Meal {
    @NonNull
    @PrimaryKey
    @SerializedName("idMeal")
    private int id;
    @SerializedName("strMeal")
    @Expose
    private String mealName;
    @SerializedName("strMealThumb")
    @Expose
    String mealImage;
    public Meal( String mealName, String mealImage) {
        //this.id = id;
        this.mealName = mealName;
        this.mealImage = mealImage;
    }
    public Meal() {
    }
    @NonNull
    public int getId() {
        return id;
    }
    public void setId(@NonNull int id) {
        this.id = id;
    }
    public String getMealName() {
        return mealName;
    }
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
    @Override
    public String toString() {
        return "Meal{" +
                "mealName='" + mealName + '\'' +
                ", mealImage='" + mealImage + '\'' +
                '}';
    }
    public String getMealImage() {
        return mealImage;
    }
    public void setMealImage(String mealImage) {
        this.mealImage = mealImage;
    }
}

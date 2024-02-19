package com.example.foodplanner.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "plan_meals_table")
public class PlanMeal {
    @PrimaryKey(autoGenerate = true)
    int id;
    int mealId;
    String image;
    String mealName;

    @Override
    public String toString() {
        return "PlanMeal{" +
                "mealId=" + mealId +
                ", image='" + image + '\'' +
                ", mealName='" + mealName + '\'' +
                ", date='" + date + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }

    String date;
    String userEmail;

    public PlanMeal(int mealId, String image, String mealName, String date, String userEmail) {
        this.mealId = mealId;
        this.image = image;
        this.mealName = mealName;
        this.date = date;
        this.userEmail = userEmail;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealName() {
        return mealName;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }
}

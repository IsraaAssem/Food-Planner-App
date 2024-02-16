package com.example.foodplanner.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Meal.class}, version = 1)
public abstract class MealsDB extends RoomDatabase{
        private static MealsDB instance = null;
        public abstract MealsDAO getMealDAO();
        public static synchronized MealsDB getInstance(Context context){
            if (instance == null){
                instance = Room.databaseBuilder(context.getApplicationContext(), MealsDB.class, "mealsdb")
                        .build();
            }
            return instance;
        }

}

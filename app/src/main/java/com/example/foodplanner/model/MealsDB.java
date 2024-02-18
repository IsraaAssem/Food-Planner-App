package com.example.foodplanner.model;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Meal.class,PlanMeal.class},version = 5, exportSchema = false)
public abstract class MealsDB extends RoomDatabase{
        private static MealsDB instance = null;
    private static Migration Migration_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }
    };

    public abstract MealsDAO getMealDAO();
        public static synchronized MealsDB getInstance(Context context){
            if (instance == null){
                instance = Room.databaseBuilder(context.getApplicationContext(), MealsDB.class, "mealsdb")
//                        .addMigrations(Migration_2_3)
                        .fallbackToDestructiveMigration()
                        .build();// .fallbackToDestructiveMigration()
            }
            return instance;
        }
}

package com.example.myexercises.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(/*exportSchema = false,*/entities = {Collection.class, CallExec.class, Exercise.class},version = 1)
@TypeConverters(Converter.class)
public abstract class AppDataBase extends RoomDatabase {
    public abstract CollectionDao collectionDao();
    public abstract ExerciseDao exerciseDao();
    public  abstract LinkDao callexDao();

    public static String DATABASE_NAME = "dataBase";
    public static String DATABASE_NAME_WITH_PREFICS = "dataBase.db";
    public static String TABLE_ANIMALS_NAME = "Exercise";
}

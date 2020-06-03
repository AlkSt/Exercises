package com.example.myexercises.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM exercise" )
    List<Exercise> getAll();

    @Query("SELECT * FROM exercise WHERE id = :id")
    Exercise getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Exercise collection);

    @Update
    void update(Exercise collection);

    @Delete
    void delete(Exercise collection);
}

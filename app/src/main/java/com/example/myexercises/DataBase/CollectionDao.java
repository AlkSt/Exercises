package com.example.myexercises.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CollectionDao {
    @Query("SELECT * FROM collection")
    List<Collection> getAll();
   // Flowable<List<Collection>> getAll();

    @Query("SELECT * FROM collection WHERE id = :id")
    Collection getById(int id);

    @Query("SELECT * FROM collection WHERE button_id = :id")
    Collection getByName(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Collection collection);

    @Update
    void update(Collection collection);

    @Delete
    void delete(Collection collection);
}

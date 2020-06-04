package com.example.myexercises.DataBase;

import android.telecom.Call;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
@Dao
public interface LinkDao {
//    @Query("SELECT * FROM callexec")
//        List<Collection> getAll();
//   // Flowable<List<CallExec>> getAll();
    @Query("SELECT COUNT(*) FROM callexec WHERE ExerciseID=:id")
    int getCount(int id);

    @Query("SELECT * FROM callexec WHERE CollectID =:cid AND ExerciseID=:exid")
    CallExec findLink(int cid,int exid);

    @Query("SELECT * FROM callexec WHERE CollectID =:id")
    List<CallExec> getAlCol(int id);

    @Query("SELECT * FROM callexec WHERE id = :id")
    CallExec getById(int id);

    @Insert
    void insert(CallExec link);

    @Update
    void update(CallExec link);

    @Delete
    void delete(CallExec link);
}

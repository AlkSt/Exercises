package com.example.myexercises.DataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CallExec {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int ExeNumber;
    public int CollectID;
    public int ExerciseID;

    public CallExec(int col, int ex)
    {
        CollectID = col;
        ExerciseID = ex;
    }

    public CallExec()
    {

    }

    public int getPairID() {
        return id;
    }
}

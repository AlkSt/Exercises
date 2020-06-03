package com.example.myexercises.DataBase;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

//@Entity(foreignKeys = @ForeignKey(entity = CallExec.class, parentColumns = "id", childColumns = "ExercseID", onDelete = CASCADE))
@Entity
public class Exercise implements Serializable{

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public int col;
    public String imageUrl;
    public String desctiption;
    public int time;
    public int repeat;
    public Bitmap image;

    public Exercise(String name, String imageUrl, int col){
        this.name = name;
        this.imageUrl = imageUrl;
        this.col = col;
    }

    public Exercise(String name,String imageUrl, String description, int time, int repeat)
    {
        this.name = name;
        this.imageUrl = imageUrl;
        this.desctiption = description;
        this.time = time;
        this.repeat = repeat;
    }
}

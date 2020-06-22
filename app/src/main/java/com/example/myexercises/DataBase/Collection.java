package com.example.myexercises.DataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "collection")
public class Collection implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String button_name;
    public int button_id;//=x
    public int button_y;

    public Collection() {
        id=1;
        name = "fun";
        button_name="b21231";
    }

    public Collection( String _name, int _button_id, int _button_y) {
        name =  _name;
        button_id= _button_id;
        button_y = _button_y;
    }
}

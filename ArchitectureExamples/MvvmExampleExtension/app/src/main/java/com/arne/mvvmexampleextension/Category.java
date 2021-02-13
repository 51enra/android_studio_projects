package com.arne.mvvmexampleextension;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "category_table")
public class Category {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String color;

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }
}

package com.arne.mvvmexampleextension;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Entity(tableName = "note_table")
public class Note {

//   Other useful annotations, see Entity documentation:
//   developer.android.com/training/data-storage/room/defining-data

    @PrimaryKey(autoGenerate = true)
    @Setter
    private int id;

    private String title;

    private String description;

    private int priority;

    private int categoryId;

    public Note(String title, String description, int priority, int categoryId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.categoryId = categoryId;
    }

}

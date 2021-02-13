package com.arne.architectureexample1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.Getter;

@Getter
@Entity(tableName = "note_table")
public class Note {

//   Other useful annotations, see Entity documentation:
//   developer.android.com/training/data-storage/room/defining-data

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }
}

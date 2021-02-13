package com.arne.mvvmexampleextension;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM note_table WHERE id=:id LIMIT 1")
    Note findById(int id);

    @Query("SELECT note_table.id AS id, note_table.title AS title, note_table.description AS description, " +
            "note_table.priority AS priority, note_table.categoryId AS categoryId, " +
            "category_table.name AS categoryName, category_table.color AS categoryColor " +
            "FROM note_table INNER JOIN category_table ON category_table.id = note_table.categoryId")
    LiveData<List<NoteView>> getAllNoteViews();

    @Query("DELETE FROM note_table WHERE id=:id")
    void deleteById(Integer id);
}

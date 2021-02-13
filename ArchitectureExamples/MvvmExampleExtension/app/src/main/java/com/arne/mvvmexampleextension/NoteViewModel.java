package com.arne.mvvmexampleextension;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<NoteView>> allNoteViews;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
        allNoteViews = repository.getAllNoteViews();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void deleteNoteById(int id) {
        repository.deleteById(id);
    }

    public void delete(NoteView noteView) {
        Note note = repository.findById(noteView.getId());
        repository.delete(note);
    }

    public LiveData<List<NoteView>> getAllNoteViews() {
        return allNoteViews;
    }

}

//        allNoteViews.addSource(allNotes, new Observer<List<Note>>() {
//            @Override
//            public void onChanged(@Nullable List<Note> notes) {
//                List<NoteView> noteViews = new ArrayList<>();
//                for (Note note : notes) {
//                    Category category = categoryRepository.findById(note.getCategoryId());
//                    Log.d(TAG, "onChanged: " + category);
//                    noteViews.add(new NoteView(note.getId(), note.getTitle(), note.getDescription(), note.getPriority(),
//                            note.getCategoryId(), "dummy", "#f080f0"));
//                }
//                allNoteViews.setValue(noteViews);
//            }
//        });
package com.arne.mvvmexampleextension;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<NoteView>> allNoteViews;

    NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
        allNoteViews = noteDao.getAllNoteViews();
    }

    void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    void deleteById(int id) { new DeleteNoteByIdAsyncTask(noteDao).execute(id);
    }

    void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    LiveData<List<NoteView>> getAllNoteViews() { return allNoteViews; }


    Note findById(int id) {
        Note note = null;
        try {
            note = new QueryAsyncTask(noteDao).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return note;
    }

    private static class QueryAsyncTask extends AsyncTask<Integer, Void, Note> {
        private NoteDao asyncTaskDao;
        private NoteRepository delegate = null;

        QueryAsyncTask (NoteDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Note doInBackground(final Integer... params) {
            return asyncTaskDao.findById(params[0]);
        }
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteByIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteByIdAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Integer... ids) {
            noteDao.deleteById(ids[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}

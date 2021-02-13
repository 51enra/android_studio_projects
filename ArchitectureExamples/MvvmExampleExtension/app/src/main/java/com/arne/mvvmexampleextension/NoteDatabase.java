package com.arne.mvvmexampleextension;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class, Category.class}, version = 4)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    public abstract NoteDao noteDao();
    public abstract CategoryDao categoryDao();

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;
        private CategoryDao categoryDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
            categoryDao = db.categoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Description 1", 1, 1));
            noteDao.insert(new Note("Title 2", "Description 2", 2, 4));
            noteDao.insert(new Note("Title 3", "Description 3", 3, 2));
            noteDao.insert(new Note("Title 4", "Description 4", 2, 2));

            categoryDao.insert(new Category("General", "#F0F0F0"));
            categoryDao.insert(new Category("Private", "#00F0F0"));
            categoryDao.insert(new Category("Family", "#F0F000"));
            categoryDao.insert(new Category("Business", "#F000F0"));
            return null;
        }
    }
}

package com.arne.mvvmexampleextension;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;
    private MutableLiveData<Category> searchResult = new MutableLiveData<>();

    public CategoryRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        categoryDao = database.categoryDao();
        allCategories = categoryDao.getAllCategories();
    }

    void insert(Category category) {
        new InsertCategoryAsyncTask(categoryDao).execute(category);
    }

    void update(Category category) {
        new UpdateCategoryAsyncTask(categoryDao).execute(category);
    }

    void delete(Category category) {
        new DeleteCategoryAsyncTask(categoryDao).execute(category);
    }

    void deleteAllNotes() {
        new DeleteAllCategoriesAsyncTask(categoryDao).execute();
    }

    LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    private void asyncFinished(Category result) {
        searchResult.setValue(result);
    }

    private static class QueryAsyncTask extends AsyncTask<Integer, Void, Category> {
        private CategoryDao asyncTaskDao;
        private CategoryRepository delegate = null;

        QueryAsyncTask (CategoryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Category doInBackground(final Integer... params) {
            return asyncTaskDao.findById(params[0]);
        }

        @Override
        protected void onPostExecute(Category result) {
            delegate.asyncFinished(result);
        }
    }

    public void findCategory(int id) {
        QueryAsyncTask task = new QueryAsyncTask(categoryDao);
        task.delegate = this;
        task.execute(id);
    }

    public MutableLiveData<Category> findById(int id) {
        findCategory(id);
        return searchResult;
    }


    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao;

        private InsertCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.insert(categories[0]);
            return null;
        }
    }

    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao;

        private UpdateCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.update(categories[0]);
            return null;
        }
    }

    private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao;

        private DeleteCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.delete(categories[0]);
            return null;
        }
    }

    private static class DeleteAllCategoriesAsyncTask extends AsyncTask<Void, Void, Void> {
        private CategoryDao categoryDao;

        private DeleteAllCategoriesAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            categoryDao.deleteAllCategories();
            return null;
        }
    }
}

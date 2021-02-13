package com.arne.mvvmexampleextension;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.arne.mvvmexampleextension.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.arne.mvvmexampleextension.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.arne.mvvmexampleextension.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.arne.mvvmexampleextension.EXTRA_PRIORITY";
    public static final String EXTRA_CATEGORY_ID =
            "com.arne.mvvmexampleextension.EXTRA_CATEGORY_ID";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    private Category selectedCategory;
    private int spinnerPosition;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        //>>********************* SPINNER *********************************************

        final Spinner spinner = findViewById(R.id.spinner_category);
        CategoryViewModel categoryViewModel;

        //categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()).create(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {

            @Override
            public void onChanged(@Nullable List<Category> categories) {
                adapter = new CategoryAdapter(AddEditNoteActivity.this, categories);
                spinner.setAdapter(adapter);
                spinner.setSelection(spinnerPosition);
            }
        });

 //       ArrayList<Category> categoryList = new ArrayList<>();
        // Initial List population:

//        Category category1 = new Category("general", "#00E0D0");
//        categoryList.add(category1);
//        Category category2 = new Category("business", "#D000D0");
//        categoryList.add(category2);
//        Category category3 = new Category("private", "#0000E0");
//        categoryList.add(category3);


//        CategoryAdapter adapter = new CategoryAdapter(this, categoryList);

//        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, categoryList);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = (Category) parent.getSelectedItem();
                String hint = "Category: " + selectedCategory.getName() + "\nColor: " + selectedCategory.getColor();
                Toast.makeText(AddEditNoteActivity.this, hint, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //<<********************* SPINNER *********************************************


        Intent intent = getIntent();

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit note");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra((EXTRA_DESCRIPTION)));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            spinnerPosition = intent.getIntExtra(EXTRA_CATEGORY_ID, 1) - 1;
        } else {
            setTitle("Add Note");
            spinnerPosition = 0;
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int categoryId = selectedCategory.getId();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_CATEGORY_ID, categoryId);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

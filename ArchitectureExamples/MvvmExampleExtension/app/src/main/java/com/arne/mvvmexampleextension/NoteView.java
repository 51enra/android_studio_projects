package com.arne.mvvmexampleextension;

import lombok.Getter;

@Getter
public class NoteView {

    private int id;

    private String title;

    private String description;

    private int priority;

    private int categoryId;

    private String categoryName;

    private String categoryColor;

    public NoteView(int id, String title, String description, int priority, int categoryId, String categoryName, String categoryColor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
    }

}


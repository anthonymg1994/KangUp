package com.mx.bridgestudio.kangup.Models;

import android.graphics.drawable.Drawable;

/**
 * Created by Isaac on 25/11/2016.
 */

public class Category {
    private int id;
    private String name;
    private Drawable view;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Drawable getView() {
        return view;
    }

    public void setView(Drawable view) {
        this.view = view;
    }
}

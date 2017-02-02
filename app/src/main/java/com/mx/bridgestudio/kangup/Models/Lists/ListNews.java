package com.mx.bridgestudio.kangup.Models.Lists;

import android.content.Context;

/**
 * Created by Isaac on 20/12/2016.
 */

public class ListNews {


    private int id;
    private String title;
    private String description;
    private String image;
    private String fecha;



    public ListNews(){}
    public ListNews(int id, String title, String desc,String image,String fecha){
        this.setId(id);
        this.setTitle(title);
        this.setDescription(desc);
        this.setImage(image);
        this.setFecha(fecha);
    }
    public static int getImageId(Context context, int imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

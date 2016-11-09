package com.mx.bridgestudio.kangup.Models;

import android.content.Context;

/**
 * Created by USUARIO on 25/10/2016.
 */
//Adaptador para la lista de tipos de autos
public class ListCar {
    private int id;
    private String Name;
    private String Description;
    private int image;


    public ListCar(){}
    public ListCar(int id,String Nombre,String Descripcion){
        this.id=id;
        this.Name=Nombre;
        this.Description=Descripcion;
    }
    public static int getImageId(Context context, int imageName) {
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public int getImage() {return image;}
    public void setImage(int image) {this.image = image;}
}

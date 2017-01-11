package com.mx.bridgestudio.kangup.Models.Lists;

/**
 * Created by USUARIO on 11/01/2017.
 */

public class ListPublicidad {
    private String nombre;
    private int image;
    private int id;

    public ListPublicidad(){
    }
    public ListPublicidad(int id,int image,String nombre){
        this.nombre = nombre;
        this.image = image;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package com.mx.bridgestudio.kangup.Models;

/**
 * Created by USUARIO on 12/12/2016.
 */

public class ListEspecificaciones {
    private String nombre;
    private int id_image;


    public ListEspecificaciones(){}
    public ListEspecificaciones(int id_image, String nombre){
        this.nombre = nombre;
        this.id_image = id_image;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_image() {
        return id_image;
    }

    public void setId_image(int id_image) {
        this.id_image = id_image;
    }
}

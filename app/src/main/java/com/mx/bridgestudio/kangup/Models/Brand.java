package com.mx.bridgestudio.kangup.Models;

/**
 * Created by USUARIO on 29/11/2016.
 */

public class Brand {

    private int id;
    private String nombre;
    //private String photo;
    private int id_categoria;

    public Brand(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
}

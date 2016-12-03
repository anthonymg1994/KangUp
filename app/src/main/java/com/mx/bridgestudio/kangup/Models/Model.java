package com.mx.bridgestudio.kangup.Models;

/**
 * Created by USUARIO on 01/12/2016.
 */

public class Model {
    private int id;
    private String nombre;
    private int id_categoria;



    public Model(){}
    public int getId() {
        return id;
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

    public void setId(int id) {
        this.id = id;
    }
}

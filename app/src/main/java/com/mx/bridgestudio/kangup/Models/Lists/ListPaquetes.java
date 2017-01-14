package com.mx.bridgestudio.kangup.Models.Lists;

/**
 * Created by Isaac on 05/01/2017.
 */

public class ListPaquetes {
    private int id;
    private String nombre;
    private String descripcion;
    private String precio;

    public ListPaquetes(){}
    public ListPaquetes(int id, String nombre, String descripcion, String precio){
        this.setId(id);
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setPrecio(precio);
    }


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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}

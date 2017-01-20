package com.mx.bridgestudio.kangup.Models.Lists;

import android.content.Context;

/**
 * Created by USUARIO on 25/10/2016.
 */
//Adaptador para la lista de tipos de autos
public class ListCar {
    private int id;
    private String Marca;
    private String Modelo;
    private String anio;

    private String image;


    public ListCar(){}
    public ListCar(int id,String Marca,String Modelo,String anio,String image){
        this.id     =   id;
        this.Marca  =   Marca;
        this.Modelo =   Modelo;
        this.anio   =   anio;
        this.image = image;
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

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

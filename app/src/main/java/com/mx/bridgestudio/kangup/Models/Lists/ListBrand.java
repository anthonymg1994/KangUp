package com.mx.bridgestudio.kangup.Models.Lists;

/**
 * Created by USUARIO on 30/11/2016.
 */

public class ListBrand {
    private int id;
    private String Name;
    private int id_categoria;
    private String image;


    public ListBrand(){}
    public ListBrand(int id,String name,int id_categoria,String image){
        this.id = id;
        this.Name = name;
        this.id_categoria = id_categoria;
        this.image = image;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

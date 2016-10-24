package com.mx.bridgestudio.kangup.Models;

/**
 * Created by USUARIO on 24/10/2016.
 */

public class Vehicle {
    private int id;
    private String model;
    private String year;
    private int id_brand;
    private int id_parther;
    private String description;
    private String specifications;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getId_brand() {
        return id_brand;
    }

    public void setId_brand(int id_brand) {
        this.id_brand = id_brand;
    }

    public int getId_parther() {
        return id_parther;
    }

    public void setId_parther(int id_parther) {
        this.id_parther = id_parther;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
}

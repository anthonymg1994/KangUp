package com.mx.bridgestudio.kangup.Models.Lists;

/**
 * Created by Isaac on 04/01/2017.
 */

public class ListRoutes {

    private int id;
    private String origen;
    private String destiny;
   private int IdSQL;
    private int Posicion;

    public ListRoutes(){}
    public ListRoutes(int id, String origin, String destiny){
        this.setId(id);
        this.setOrigen(origin);
        this.setDestiny(destiny);
    }

    public int getIdSQL() {
        return IdSQL;
    }

    public void setIdSQL(int idSQL) {
        IdSQL = idSQL;
    }

    public int getPosicion() {
        return Posicion;
    }

    public void setPosicion(int posicion) {
        Posicion = posicion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }



}

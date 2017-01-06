package com.mx.bridgestudio.kangup.Models.Lists;

/**
 * Created by Isaac on 04/01/2017.
 */

public class ListRoutes {

    private int id;
    private String origen;
    private String destiny;

    public ListRoutes(){}
    public ListRoutes(int id, String origin, String destiny){
        this.setId(id);
        this.setOrigen(origin);
        this.setDestiny(destiny);
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

package com.mx.bridgestudio.kangup.Models;

/**
 * Created by USUARIO on 24/10/2016.
 */

public class Reservacion {
    private int id;
    private int id_user;
    private int id_vehicle;
    private int id_pack;
    private String date;
    private String HourI;
    private String HourF;
    private String origen;
    private String destination;
    private int idpayment;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_vehicle() {
        return id_vehicle;
    }

    public void setId_vehicle(int id_vehicle) {
        this.id_vehicle = id_vehicle;
    }

    public int getId_pack() {
        return id_pack;
    }

    public void setId_pack(int id_pack) {
        this.id_pack = id_pack;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHourI() {
        return HourI;
    }

    public void setHourI(String hourI) {
        HourI = hourI;
    }

    public String getHourF() {
        return HourF;
    }

    public void setHourF(String hourF) {
        HourF = hourF;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getIdpayment() {
        return idpayment;
    }

    public void setIdpayment(int idpayment) {
        this.idpayment = idpayment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

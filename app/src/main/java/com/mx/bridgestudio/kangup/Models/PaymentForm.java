package com.mx.bridgestudio.kangup.Models;

/**
 * Created by Isaac on 19/12/2016.
 */

public class PaymentForm {

    private int id;
    private int id_usuario;
    private int id_forma_pago;
    private String tipoPago;
    private String num_cuenta;
    private String mes_venc;
    private String anio_venc;
    private String cvv;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_forma_pago() {
        return id_forma_pago;
    }

    public void setId_forma_pago(int id_forma_pago) {
        this.id_forma_pago = id_forma_pago;
    }

    public String getNum_cuenta() {
        return num_cuenta;
    }

    public void setNum_cuenta(String num_cuenta) {
        this.num_cuenta = num_cuenta;
    }

    public String getMes_venc() {
        return mes_venc;
    }

    public void setMes_venc(String mes_venc) {
        this.mes_venc = mes_venc;
    }

    public String getAnio_venc() {
        return anio_venc;
    }

    public void setAnio_venc(String anio_venc) {
        this.anio_venc = anio_venc;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
}

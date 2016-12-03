package com.mx.bridgestudio.kangup.Controllers.ServiciosWeb;

import android.content.Context;

import com.mx.bridgestudio.kangup.AsyncTask.MarcaModelo.AsyncBrands;
import com.mx.bridgestudio.kangup.AsyncTask.MarcaModelo.AsyncVehiculosXmarca;
import com.mx.bridgestudio.kangup.AsyncTask.Usuario.AsyncInsertUser;
import com.mx.bridgestudio.kangup.AsyncTask.Usuario.AsynkTaskUser;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendCarXtype;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendToActivity;
import com.mx.bridgestudio.kangup.Models.Brand;
import com.mx.bridgestudio.kangup.Models.Model;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Models.Vehicle;

/**
 * Created by USUARIO on 28/11/2016.
 */

public class webServices {

    public void Login(Context context, User user){
        new AsynkTaskUser(context,user).execute();
    }

    public void insertUser(Context context, User user){
        new AsyncInsertUser(context,user).execute();
    }

    public void brandByCategory(OnDataSendToActivity OnDataSendToActivity,Context context, Brand brand){
        new AsyncBrands(OnDataSendToActivity,context,brand).execute();
    }
    public void AutosByMarca(OnDataSendCarXtype OnDataSendToActivity, Context context, Vehicle vehicle){
        new AsyncVehiculosXmarca(OnDataSendToActivity,context,vehicle).execute();
    }
/*
    public void Noticias(Context context, News news){
        new AsyncNews(context,news).execute();
    }

    public void Profile(Context context, User user){
        new AsyncProfile(context,user).execute();
    }
    public void FavByUser(Context context, User user){
        new AsyncFavorites(context,user).execute();
    }
    public void HistorialByUser(Context context, User user){
        new AsyncHistorial(context,user).execute();
    }
    public void FormasByUser(Context context, User user){
        new AsyncFormapago(context,user).execute();
    }
    public void AutoDisponiblesByFH(Context context, Reservacion reservacion){
        new AsynAvaibleByFH(context,reservacion).execute();
    }
    public void SearchAuto(Context context, Vehicle vehicle){
        new AsyncSearchAuto(context,vehicle).execute();
    }
    public void DetalleAuto(Context context, Vehicle vehicle){
        new AsyncDetailAuto(context,vehicle).execute();
    }
    public void RecuperarPassword(Context context, User user){
        new AsyncGetpassword(context,user).execute();
    }
    public void updateProfile(Context context, User user){
        new AsyncUpdateProfile(context,user).execute();
    }
    public void TopVehiculosMasRentados(Context context, Vehicle vehiculo){
        new AsyncMasRentados(context,vehiculo).execute();
    }
    public void VehiculosRecomendados(Context context, Vehicle vehiculo){
        new AsyncAutosRecomendados(context,vehiculo).execute();
    }
    public void TopRankingVehiculo(Context context, Vehicle vehiculo){
        new AsyncRankingVehiculos(context,vehiculo).execute();
    }
    public void imageByVehicle(Context context, Vehicle vehiculo){
        new AsyncImagesVehiculos(context,vehiculo).execute();
    }


*/
}

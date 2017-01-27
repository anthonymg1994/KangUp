package com.mx.bridgestudio.kangup.Controllers.ServiciosWeb;

import android.content.Context;

import com.mx.bridgestudio.kangup.AsyncTask.Formas_Pago.AsyncInsertPaymentForms;
import com.mx.bridgestudio.kangup.AsyncTask.Formas_Pago.AsyncPaymentFormsUser;
import com.mx.bridgestudio.kangup.AsyncTask.MarcaModelo.AsyncBrands;
import com.mx.bridgestudio.kangup.AsyncTask.MarcaModelo.AsyncVehiculosXmarca;
import com.mx.bridgestudio.kangup.AsyncTask.Noticias.AsyncNews;
import com.mx.bridgestudio.kangup.AsyncTask.Publicidad.asyncTaskPublicidad;
import com.mx.bridgestudio.kangup.AsyncTask.Reservacion.asyncEmailConfirmacion;
import com.mx.bridgestudio.kangup.AsyncTask.Reservacion.asyncPaquetesXReservacion;
import com.mx.bridgestudio.kangup.AsyncTask.Reservacion.asyncTaskArticulos;
import com.mx.bridgestudio.kangup.AsyncTask.Reservacion.asyncTaskPaquetes;
import com.mx.bridgestudio.kangup.AsyncTask.Usuario.AsyncInsertUser;
import com.mx.bridgestudio.kangup.AsyncTask.Usuario.AsyncTaskUpdateUser;
import com.mx.bridgestudio.kangup.AsyncTask.Usuario.AsynkTaskUser;
import com.mx.bridgestudio.kangup.AsyncTask.Vehiculo.AsyncAddFav;
import com.mx.bridgestudio.kangup.AsyncTask.Vehiculo.AsyncDeleteFav;
import com.mx.bridgestudio.kangup.AsyncTask.Vehiculo.AsyncDetailAuto;
import com.mx.bridgestudio.kangup.AsyncTask.Vehiculo.AsyncFavs;
import com.mx.bridgestudio.kangup.AsyncTask.Vehiculo.AsyncRecommend;
import com.mx.bridgestudio.kangup.AsyncTask.Vehiculo.AsyncScore;
import com.mx.bridgestudio.kangup.AsyncTask.Vehiculo.AsyncTop;
import com.mx.bridgestudio.kangup.AsyncTask.Vehiculo.asyncTaskPhotoById;
import com.mx.bridgestudio.kangup.AsyncTask.Viaje.DetalleViajeByUser;
import com.mx.bridgestudio.kangup.AsyncTask.Viaje.RoutesByTrip;
import com.mx.bridgestudio.kangup.AsyncTask.Viaje.historyByUser;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendAllPackages;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendCarXtype;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendDetail;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFavorites;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFilterRecommend;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFilterScore;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFilterTop;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendHistory;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendHistoryDetail;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendNews;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPackageByReservation;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPaymentFormsUser;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPhotos;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPublicidad;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendRoutesByTrip;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendToActivity;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSentArticlesByPackage;
import com.mx.bridgestudio.kangup.Models.Brand;
import com.mx.bridgestudio.kangup.Models.PaymentForm;
import com.mx.bridgestudio.kangup.Models.Reservacion;
import com.mx.bridgestudio.kangup.Models.Rutas;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Models.Vehicle;

import java.util.Date;

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
    public void historyByUser(OnDataSendHistory OnDataSendToActivity, Context context, User user){
        new historyByUser(OnDataSendToActivity,context,user).execute();
    }

    public void DetailVehicle(OnDataSendDetail OnDataSendToDetail, Context context, Vehicle vehicle){
        new AsyncDetailAuto(OnDataSendToDetail,context,vehicle).execute();
    }
    public void favsByUser(OnDataSendFavorites OnDataSendFavorites, Context context, User user){

        new AsyncFavs(OnDataSendFavorites,context,user).execute();

    }
    public void  EmailConfirmationReservation(Context context, Reservacion reservacion){

        new asyncEmailConfirmacion(context,reservacion).execute();

    }

    public void insertFormaPago(Context context, PaymentForm pay){
        new AsyncInsertPaymentForms(context,pay).execute();
    }

    public void getFormaPagoByUser(OnDataSendPaymentFormsUser dataSendPaymentFormsUser, Context context, PaymentForm paymentForm){
        new AsyncPaymentFormsUser(dataSendPaymentFormsUser,context,paymentForm).execute();
    }

    public void getAllNews(OnDataSendNews dataSendNews, Context context){
        new AsyncNews(dataSendNews,context).execute();
    }

    public void getAllPublicidad(OnDataSendPublicidad dataSendPublicidad, Context context){
        new asyncTaskPublicidad(dataSendPublicidad,context).execute();
    }



    public void destroyFav(Context context, int id_vehiculo, int id_user){
        new AsyncDeleteFav(context,id_vehiculo,id_user).execute();
    }

    public void updateUser(Context context,User user){
        new AsyncTaskUpdateUser(context,user).execute();
    }
    public void addFav(Context context, int id_vehiculo, int id_user){
        new AsyncAddFav(context,id_vehiculo,id_user).execute();
    }
    public void ScoreVehiculo(Context context, Vehicle vehiculo,Date date, Date time,Date time_final, OnDataSendFilterScore OnDataSendToActivity){
        new AsyncScore(context,vehiculo,OnDataSendToActivity,date,time,time_final).execute();
    }
    public void getRecommendCVehicles(Context context, Vehicle vehiculo,OnDataSendFilterRecommend OnDataSendToActivity,Date date, Date time,Date time_final){
        new AsyncRecommend(context,vehiculo,OnDataSendToActivity,date,time,time_final).execute();
    }

    public void getVehiclesByTop(Context context, Vehicle vehiculo,Date date, Date time,Date time_final, OnDataSendFilterTop OnDataSendToActivity){
        new AsyncTop(context,vehiculo,OnDataSendToActivity,date,time,time_final).execute();
    }


    public void getHistoryDetailByUser(OnDataSendHistoryDetail c,Context context, int idUser, int idRes, int idu){
        new DetalleViajeByUser(c,context,idUser,idRes,idu).execute();
    }

    public void getRoutesByTrip(OnDataSendRoutesByTrip dataSendRoutesByTrip, Context context, Rutas rutas){
        new RoutesByTrip(dataSendRoutesByTrip,context,rutas).execute();
    }

    public void getPackagesByReservation(OnDataSendPackageByReservation dataSendPackageByReservation, Context context, int id_reservacion, int id_usuario){
        new asyncPaquetesXReservacion(dataSendPackageByReservation,context,id_reservacion,id_usuario).execute();
    }

    public void getAllPackages(OnDataSendAllPackages dataSendAllPackages, Context context){
        new asyncTaskPaquetes(dataSendAllPackages,context).execute();
    }

    public void getArticlesByPackage(OnDataSentArticlesByPackage dataSendArticlesByPackage, Context context, int id_paquete){
        new asyncTaskArticulos(dataSendArticlesByPackage,context,id_paquete).execute();
    }
    public void getAllPhotoById(OnDataSendPhotos onDataSendPhotos, Context context, Vehicle vehicle){
        new asyncTaskPhotoById(onDataSendPhotos,context,vehicle).execute();
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

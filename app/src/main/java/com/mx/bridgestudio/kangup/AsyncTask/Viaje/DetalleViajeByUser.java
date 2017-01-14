package com.mx.bridgestudio.kangup.AsyncTask.Viaje;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOviajes;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendHistory;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendHistoryDetail;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.DetalleViaje;
import com.mx.bridgestudio.kangup.Models.RoadTrip;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.DetalleActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Isaac on 02/01/2017.
 */

public class DetalleViajeByUser extends AsyncTask<String,Integer,DetalleViaje> implements Serializable {

    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    URL url;
    private DetalleViaje[] arrayViajes;
    private DetalleViaje objDetalle;
    private User user = new User();
    private String sviajes;
    private webServices services = new webServices();
    private DAOviajes Dviajes = new DAOviajes();
    public OnDataSendHistoryDetail SendToActivity;//Call back interface

    private int idUser;
    private int idRes;
    private int idUs;

    Context mContext;

    private boolean flag = false;

    public DetalleViajeByUser(OnDataSendHistoryDetail SendToActivity, Context context, int idUser, int idRes, int idUs) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
        this.idUser = idUser;
        this.idRes = idRes;
        this.idUs = idUs;
    }

    @Override
    protected DetalleViaje doInBackground(String... params) {

        try {
            try {
                objDetalle = Dviajes.getHistoryDetailByUser(idUser,idRes,idUs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return objDetalle;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext, R.style.MyDialogTheme);
        progressDialog.setMessage("Procesando...");
        progressDialog.show();
        progressDialog.setCancelable(false);

    }

    @Override
    protected void onPostExecute(DetalleViaje result) {
        super.onPostExecute(result);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(result == null){
            Toast.makeText(mContext, "Vuelve a intentarlo"+result, Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(mContext, "Bienvenido "+user, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(mContext, HistoryDetailsActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("value", objDetalle);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);



        }
    }
}

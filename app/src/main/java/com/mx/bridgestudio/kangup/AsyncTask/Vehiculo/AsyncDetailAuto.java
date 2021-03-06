package com.mx.bridgestudio.kangup.AsyncTask.Vehiculo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOVehiculo;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendDetail;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.DetalleActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by USUARIO on 07/12/2016.
 */

public class AsyncDetailAuto extends AsyncTask<String,Integer,Vehicle> implements Serializable {
    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    URL url;
    private Vehicle[] arrayVehicle;
    private Vehicle vehicle = new Vehicle();
    private Vehicle objVehicle;
    private webServices services = new webServices();
    private DAOVehiculo Dvehicle = new DAOVehiculo();

    public OnDataSendDetail SendToActivity;//Call back interface


    Context mContext;

    private boolean flag = false;

    public AsyncDetailAuto(OnDataSendDetail SendToActivity, Context context, Vehicle vehicle) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
        this.vehicle = vehicle;

    }

    @Override
    protected Vehicle doInBackground(String... params) {

        try {
            objVehicle = Dvehicle.getVehicleDetail(vehicle);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objVehicle;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressDialog = new ProgressDialog(AsyncDetailAuto.this);
        //progressDialog.setMessage("Procesando...");
        //progressDialog.show();
        //progressDialog.setCancelable(false);

        progressDialog = new ProgressDialog(mContext, R.style.MyDialogTheme);
        progressDialog.setMessage("Procesando...");
        progressDialog.show();
        progressDialog.setCancelable(false);

    }

    @Override
    protected void onPostExecute(Vehicle result) {
        super.onPostExecute(result);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(result.getId() == 0){
            Toast.makeText(mContext, "Vuelve a intentarlo"+result, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext, "Bienvenido "+objVehicle.getModel(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(mContext, DetalleActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("value", objVehicle);
            intent.putExtras(bundle);
            mContext.startActivity(intent);

            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);



        }
    }

}

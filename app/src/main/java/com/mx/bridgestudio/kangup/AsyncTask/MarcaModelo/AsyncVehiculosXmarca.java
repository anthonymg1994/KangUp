package com.mx.bridgestudio.kangup.AsyncTask.MarcaModelo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOVehiculo;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendCarXtype;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CarsXtype;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by USUARIO on 02/12/2016.
 */

public class AsyncVehiculosXmarca extends AsyncTask<String,Integer,String> {
    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    URL url;
    private Vehicle[] arrayVehiculos;
    private Vehicle vehicle = new Vehicle();
    private String svehiculos;
    private webServices services = new webServices();
    private DAOVehiculo Dvehiculo = new DAOVehiculo();

    public OnDataSendCarXtype SendToActivity;//Call back interface


    Context mContext;

    private boolean flag = false;

    public AsyncVehiculosXmarca(OnDataSendCarXtype SendToActivity,Context context,Vehicle vehicle) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
        this.vehicle = vehicle;

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            svehiculos = Dvehiculo.getVehiclesByBrand(vehicle);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return svehiculos;
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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(result.equals("0")){
            Toast.makeText(mContext, "Vuelve a intentarlo"+result, Toast.LENGTH_SHORT).show();
        }else{
            //      Toast.makeText(mContext, "Bienvenido "+vehicle, Toast.LENGTH_SHORT).show();

            try {
                JSONArray jsonarray = new JSONArray(result);
                arrayVehiculos = new Vehicle[jsonarray.length()];

                for (int i = 0; i < jsonarray.length(); i++) {
                    arrayVehiculos[i] = new Vehicle();
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    arrayVehiculos[i].setId(jsonobject.getInt("id"));
                    arrayVehiculos[i].setMarca(jsonobject.getString("Marca"));
                    arrayVehiculos[i].setModel(jsonobject.getString("Modelo"));
                    arrayVehiculos[i].setYear(jsonobject.getString("Anio"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(mContext, CarsXtype.class);
            SendToActivity.sendData(arrayVehiculos);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);



        }
    }



}

package com.mx.bridgestudio.kangup.AsyncTask.Vehiculo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOVehiculo;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFilterRecommend;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFilterScore;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CarsXtype;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by USUARIO on 04/01/2017.
 */

public class AsyncRecommend extends AsyncTask<String,Integer,String> {
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
    private Date date,time,time_final;
    public OnDataSendFilterRecommend SendToActivity;//Call back interface


    Context mContext;

    private boolean flag = false;

    public AsyncRecommend(Context context, Vehicle vehicle, OnDataSendFilterRecommend SendToActivity,Date date, Date time, Date time_final) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
        this.vehicle = vehicle;
        this.date = date;
        this.time = time;
        this.vehicle = vehicle;
        this.time_final = time_final;

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            svehiculos = Dvehiculo.getRecommendCVehicles(vehicle,date,time,time_final);
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
                    arrayVehiculos[i].setValoracion(jsonobject.getInt("puntuacion"));
                    arrayVehiculos[i].setFoto(jsonobject.getString("foto_predeterminada"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mContext, CarsXtype.class);
            SendToActivity.sendDataRecommend(arrayVehiculos);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);
            //  mContext.startActivity(intent);
        }
    }
}

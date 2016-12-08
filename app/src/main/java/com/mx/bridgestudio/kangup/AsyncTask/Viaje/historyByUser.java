package com.mx.bridgestudio.kangup.AsyncTask.Viaje;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOviajes;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendHistory;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.RoadTrip;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by USUARIO on 07/12/2016.
 */

public class historyByUser extends AsyncTask<String,Integer,String> {
    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    URL url;
    private RoadTrip[] arrayViajes;
    private User user = new User();
    private String sviajes;
    private webServices services = new webServices();
    private DAOviajes Dviajes = new DAOviajes();
    public OnDataSendHistory SendToActivity;//Call back interface


    Context mContext;

    private boolean flag = false;

    public historyByUser(OnDataSendHistory SendToActivity, Context context, User user) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
        this.user = user;

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            sviajes = Dviajes.getHistoryByUser(user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sviajes;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext);
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
            Toast.makeText(mContext, "Bienvenido "+user, Toast.LENGTH_SHORT).show();

            try {
                JSONArray jsonarray = new JSONArray(result);
                arrayViajes = new RoadTrip[jsonarray.length()];

                for (int i = 0; i < jsonarray.length(); i++) {
                    arrayViajes[i] = new RoadTrip();
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    arrayViajes[i].setId(jsonobject.getInt("id"));
                    arrayViajes[i].setMarca(jsonobject.getString("Marca"));
                    arrayViajes[i].setFecha(jsonobject.getString("fecha"));
                    arrayViajes[i].setTotal(jsonobject.getString("total"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mContext, HistoryActivity.class);
            SendToActivity.sendDataHistory(arrayViajes);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);



        }
    }

}

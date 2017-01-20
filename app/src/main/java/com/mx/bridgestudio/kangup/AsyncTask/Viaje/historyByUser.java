package com.mx.bridgestudio.kangup.AsyncTask.Viaje;

import android.app.Activity;
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
import com.mx.bridgestudio.kangup.R;
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
            Toast.makeText(mContext, "Bienvenido "+user, Toast.LENGTH_SHORT).show();

            try {
                JSONArray jsonarray = new JSONArray(result);
                arrayViajes = new RoadTrip[jsonarray.length()];

                for (int i = 0; i < jsonarray.length(); i++) {
                    arrayViajes[i] = new RoadTrip();
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    arrayViajes[i].setId(jsonobject.getInt("ID"));
                    arrayViajes[i].setMarca(jsonobject.getString("Marca"));
                    arrayViajes[i].setModelo(jsonobject.getString("Modelo"));
                    arrayViajes[i].setYear(jsonobject.getString("Anio"));
                    arrayViajes[i].setFecha(jsonobject.getString("Fecha"));
                    arrayViajes[i].setTotal(jsonobject.getString("Total"));
                    arrayViajes[i].setId_reservation(jsonobject.getInt("id_reservacion"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mContext, HistoryActivity.class);
            ((Activity)mContext).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            SendToActivity.sendDataHistory(arrayViajes);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);



        }
    }

}


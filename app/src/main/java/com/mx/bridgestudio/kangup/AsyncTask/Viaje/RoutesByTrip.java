package com.mx.bridgestudio.kangup.AsyncTask.Viaje;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOviajes;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendHistory;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendRoutesByTrip;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.RoadTrip;
import com.mx.bridgestudio.kangup.Models.Rutas;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.ViajesProximosActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Isaac on 04/01/2017.
 */

public class RoutesByTrip extends AsyncTask<String,Integer,String> {

    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    URL url;
    private Rutas[] arrayRutas;
    private Rutas rutas = new Rutas();
    private String srutas;
    private webServices services = new webServices();
    private DAOviajes Dviajes = new DAOviajes();
    public OnDataSendRoutesByTrip SendToActivity;//Call back interface
    public static int flagg=0;


    Context mContext;

    private boolean flag = false;

    public RoutesByTrip(OnDataSendRoutesByTrip SendToActivity, Context context, Rutas ru) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
        this.rutas = ru;

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            srutas = Dviajes.getRoutesByTrip(rutas);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return srutas;
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
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (result.equals("0")) {
            Toast.makeText(mContext, "Vuelve a intentarlo" + result, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Bienvenido " + rutas, Toast.LENGTH_SHORT).show();

            try {
                JSONArray jsonarray = new JSONArray(result);
                arrayRutas = new Rutas[jsonarray.length()];

                for (int i = 0; i < jsonarray.length(); i++) {
                    arrayRutas[i] = new Rutas();
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    arrayRutas[i].setOrigen(jsonobject.getString("origen"));
                    arrayRutas[i].setDestino(jsonobject.getString("destino"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(flagg==1){
                Intent intent = new Intent(mContext, HistoryDetailsActivity.class);
                SendToActivity.sendDataRoutesByTrip(arrayRutas);
                mContext.startActivity(intent);
            }
            else{
                Intent intent = new Intent(mContext, ViajesProximosActivity.class);
                SendToActivity.sendDataRoutesByTrip(arrayRutas);
                mContext.startActivity(intent);

            }

            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);


        }
    }
}
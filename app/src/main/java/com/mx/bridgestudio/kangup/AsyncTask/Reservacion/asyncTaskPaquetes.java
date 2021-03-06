package com.mx.bridgestudio.kangup.AsyncTask.Reservacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOPaquetes;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendAllPackages;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPackageByReservation;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Package;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.Reservacion;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Isaac on 11/01/2017.
 */

public class asyncTaskPaquetes extends AsyncTask<String,Integer,String> {

    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    URL url;
    private Package[] arrayPack;
    private Package packs = new Package();
    private String newsString;
    private webServices services = new webServices();
    private DAOPaquetes Dpack = new DAOPaquetes();
    private int id_reservacion;
    private int id_usuario;

    public OnDataSendAllPackages SendToActivity;//Call back interface


    Context mContext;

    private boolean flag = false;

    public asyncTaskPaquetes(OnDataSendAllPackages SendToActivity, Context context) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            newsString = Dpack.getAllPackages();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsString;
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
            //     Toast.makeText(mContext, "Bienvenido "+brands, Toast.LENGTH_SHORT).show();

            try {
                JSONArray jsonarray = new JSONArray(result);
                arrayPack = new Package[jsonarray.length()];

                for (int i = 0; i < jsonarray.length(); i++) {
                    arrayPack[i] = new Package();
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    arrayPack[i].setId(jsonobject.getInt("id"));
                    arrayPack[i].setNombre(jsonobject.getString("Paquete"));
                    arrayPack[i].setDescripcion(jsonobject.getString("descripcion"));
                    arrayPack[i].setPrecio(jsonobject.getString("precio"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mContext, Reservacion.class);
            SendToActivity.sendDataAllPackages(arrayPack);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);



        }
    }
}



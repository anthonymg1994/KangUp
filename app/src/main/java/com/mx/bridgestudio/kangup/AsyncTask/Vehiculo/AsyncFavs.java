package com.mx.bridgestudio.kangup.AsyncTask.Vehiculo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOVehiculo;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendDetail;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFavorites;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CarsXtype;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CatalogCar;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by USUARIO on 09/12/2016.
 */

public class AsyncFavs  extends AsyncTask<String,Integer,String> {
    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    URL url;
    private Vehicle[] arrayVehiculos;
    private Vehicle vehicle = new Vehicle();
    private String objVehicle;
    private User user = new User();
    private webServices services = new webServices();
    private DAOVehiculo Dvehicle = new DAOVehiculo();

    public OnDataSendFavorites SendToActivity;//Call back interface


    Context mContext;

    private boolean flag = false;

    public AsyncFavs(OnDataSendFavorites SendToActivity, Context context, User user) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
        this.user = user;

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            objVehicle = Dvehicle.getFavorites(user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return objVehicle;
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
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (result.equals("0")) {
            Toast.makeText(mContext, "Vuelve a intentarlo" + result, Toast.LENGTH_SHORT).show();
        } else {
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
                    arrayVehiculos[i].setSpecifications(jsonobject.getString("especificaciones"));
                    arrayVehiculos[i].setYear(jsonobject.getString("Anio"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mContext, FavoriteActivity.class);
            SendToActivity.sendDataFavorites(arrayVehiculos);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);


        }
    }
}

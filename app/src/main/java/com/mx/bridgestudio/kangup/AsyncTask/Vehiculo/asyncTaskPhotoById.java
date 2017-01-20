package com.mx.bridgestudio.kangup.AsyncTask.Vehiculo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOPublicidad;
import com.mx.bridgestudio.kangup.Controllers.DAO.DAOVehiculo;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPhotos;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPublicidad;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.News;
import com.mx.bridgestudio.kangup.Models.Photo;
import com.mx.bridgestudio.kangup.Models.Publicidad;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.DetalleActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by USUARIO on 19/01/2017.
 */

public class asyncTaskPhotoById extends AsyncTask<String,Integer,String> {

    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    URL url;
    private Photo[] arrayPhotos;
    private News news = new News();
    private String photoString;
    private webServices services = new webServices();
    private DAOVehiculo Dvehicle = new DAOVehiculo();
    private Vehicle vehicle = new Vehicle();

    public OnDataSendPhotos SendToActivity;//Call back interface


    Context mContext;

    private boolean flag = false;

    public asyncTaskPhotoById(OnDataSendPhotos SendToActivity, Context context, Vehicle vehicle) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
        this.vehicle = vehicle;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            photoString = Dvehicle.getAllPhotoById(vehicle);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return photoString;
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
                 Toast.makeText(mContext, "Bienvenido "+result, Toast.LENGTH_SHORT).show();

            try {
                JSONArray jsonarray = new JSONArray(result);
                arrayPhotos = new Photo[jsonarray.length()];

                for (int i = 0; i < jsonarray.length(); i++) {
                    arrayPhotos[i] = new Photo();
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    arrayPhotos[i].setId(jsonobject.getInt("id_vehiculo"));
                    arrayPhotos[i].setNombre(jsonobject.getString("foto"));
                    //arrayPublicidad[i].setImage(R.drawable.auto);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //        Intent intent = new Intent(mContext, DetalleActivity.class);
            SendToActivity.OnDataSendPhotos(arrayPhotos);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);



        }
    }
}

package com.mx.bridgestudio.kangup.AsyncTask.MarcaModelo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAObrand;

import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendToActivity;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Brand;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CatalogCar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by USUARIO on 29/11/2016.
 */

public class AsyncBrands extends AsyncTask<String,Integer,String> {
    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    URL url;
    private Brand[] arrayBrands;
    private Brand brand = new Brand();
    private String brands;
    private webServices services = new webServices();
    private DAObrand Dbrand = new DAObrand();

    public OnDataSendToActivity SendToActivity;//Call back interface


    Context mContext;

    private boolean flag = false;

    public AsyncBrands(OnDataSendToActivity SendToActivity,Context context,Brand brand) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
        this.brand = brand;

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            brands = Dbrand.getBrandsbyType(brand);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return brands;
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
            final String URL = "http://kangup.com.mx/uploads/Marcas/";
            try {
                JSONArray jsonarray = new JSONArray(result);
                arrayBrands = new Brand[jsonarray.length()];

                    for (int i = 0; i < jsonarray.length(); i++) {
                        arrayBrands[i] = new Brand();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        arrayBrands[i].setId(jsonobject.getInt("id"));
                        arrayBrands[i].setNombre(jsonobject.getString("Marca"));
                        arrayBrands[i].setId_categoria(jsonobject.getInt("id_categoria"));
                        arrayBrands[i].setPhoto(URL + jsonobject.getString("imagen"));
                    }

                } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mContext, CatalogCar.class);
            SendToActivity.sendData(arrayBrands);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

          //  mContext.startActivity(intent);



        }
    }



}

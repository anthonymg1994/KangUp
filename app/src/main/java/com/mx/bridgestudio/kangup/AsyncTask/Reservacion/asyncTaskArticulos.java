package com.mx.bridgestudio.kangup.AsyncTask.Reservacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOArticulos;

import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSentArticlesByPackage;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Article;
import com.mx.bridgestudio.kangup.Models.Package;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.Reservacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Isaac on 12/01/2017.
 */

public class asyncTaskArticulos extends AsyncTask<String,Integer,String> {

    private ProgressDialog progressDialog;
    private Article[] arrayArt;
    private Package packs = new Package();
    private String newsString;
    private webServices services = new webServices();
    private DAOArticulos Dart = new DAOArticulos();
    private int id_paquete;

    public OnDataSentArticlesByPackage SendToActivity;//Call back interface

    Context mContext;

    private boolean flag = false;

    public asyncTaskArticulos(OnDataSentArticlesByPackage SendToActivity, Context context, int id_paquete) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
        this.id_paquete = id_paquete;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            newsString = Dart.getArticlesByPackage(id_paquete);
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
                arrayArt = new Article[jsonarray.length()];

                for (int i = 0; i < jsonarray.length(); i++) {
                    arrayArt[i] = new Article();
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    arrayArt[i].setId(jsonobject.getInt("id"));
                    arrayArt[i].setNombre(jsonobject.getString("Articulo"));
                    arrayArt[i].setDescription(jsonobject.getString("descripcion"));
                    arrayArt[i].setPrecio(jsonobject.getString("precio"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mContext, Reservacion.class);
            SendToActivity.sendDataArticleByPackage(arrayArt);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);



        }
    }
}

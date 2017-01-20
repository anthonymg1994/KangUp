package com.mx.bridgestudio.kangup.AsyncTask.Publicidad;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAONoticias;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOPublicidad;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendNews;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPublicidad;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.News;

import com.mx.bridgestudio.kangup.Models.Publicidad;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by USUARIO on 11/01/2017.
 */

public class asyncTaskPublicidad extends AsyncTask<String,Integer,String> {

    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    URL url;
    private Publicidad[] arrayPublicidad;
    private News news = new News();
    private String pubicidadString;
    private webServices services = new webServices();
    private DAOPublicidad Dpublicidad = new DAOPublicidad();

    public OnDataSendPublicidad SendToActivity;//Call back interface


    Context mContext;

    private boolean flag = false;

    public asyncTaskPublicidad(OnDataSendPublicidad SendToActivity, Context context) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            pubicidadString = Dpublicidad.getAllPublicidad();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pubicidadString;
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
                arrayPublicidad = new Publicidad[jsonarray.length()];

                for (int i = 0; i < jsonarray.length(); i++) {
                    arrayPublicidad[i] = new Publicidad();
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    arrayPublicidad[i].setId(jsonobject.getInt("id"));
                    arrayPublicidad[i].setNombre(jsonobject.getString("nombre"));
                    arrayPublicidad[i].setFormato(jsonobject.getString("formato"));
                    //arrayPublicidad[i].setImage(R.drawable.auto);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
           // Intent intent = new Intent(mContext, CategoryActivity.class);
            SendToActivity.sendDataPublicidad(arrayPublicidad);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);



        }
    }
}

package com.mx.bridgestudio.kangup.AsyncTask.Noticias;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAONoticias;
import com.mx.bridgestudio.kangup.Controllers.DAO.DAObrand;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendNews;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendToActivity;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Brand;
import com.mx.bridgestudio.kangup.Models.News;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CatalogCar;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Isaac on 20/12/2016.
 */

public class AsyncNews extends AsyncTask<String,Integer,String> {

    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    URL url;
    private News[] arrayNews;
    private News news = new News();
    private String newsString;
    private webServices services = new webServices();
    private DAONoticias Dnews = new DAONoticias();

    public OnDataSendNews SendToActivity;//Call back interface


    Context mContext;

    private boolean flag = false;

    public AsyncNews(OnDataSendNews SendToActivity, Context context) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            newsString = Dnews.getAllNews();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsString;
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
            //     Toast.makeText(mContext, "Bienvenido "+brands, Toast.LENGTH_SHORT).show();

            try {
                JSONArray jsonarray = new JSONArray(result);
                arrayNews = new News[jsonarray.length()];

                for (int i = 0; i < jsonarray.length(); i++) {
                    arrayNews[i] = new News();
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    arrayNews[i].setId(jsonobject.getInt("id"));
                    arrayNews[i].setTitulo(jsonobject.getString("titulo"));
                    arrayNews[i].setDescripccion(jsonobject.getString("descripcion"));
                    arrayNews[i].setImagen(jsonobject.getString("image"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mContext, NewsActivity.class);
            SendToActivity.sendDataNews(arrayNews);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);



        }
    }

}

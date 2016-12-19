package com.mx.bridgestudio.kangup.AsyncTask.Usuario;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOuser;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anthony on 02/11/2016.
 */

public class AsynkTaskUser extends AsyncTask<String,Integer,Boolean> {
    private URL url;
    private HttpURLConnection httpURLConnection;
    private JSONObject responseJson;
    private JSONObject jsonParam;
    private ProgressDialog progressDialog;
    private View rootLayout;
    private Context context;
    private SqliteController sql;
    private User u = new User();


    private User user;
    webServices services = new webServices();
    DAOuser Duser = new DAOuser();
    Context mContext;


    public AsynkTaskUser(Context context,User user) {
        super();
        this.mContext = context;
        this.user  =user;

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
    protected Boolean doInBackground(String... params) {
        try {
            user = Duser.authenticate(user);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
       if(result && user.getFirstName()!= null){
       //     Toast.makeText(mContext, "Bienvenido  "+user.getFirstName(), Toast.LENGTH_SHORT).show();
         
           sql = new SqliteController(mContext, "kangup",null, 1);
           sql.Connect();
          sql.insertUsuario(user);
            sql.Close();


            Intent intent = new Intent().setClass(
                    mContext,CategoryActivity.class);
            mContext.startActivity(intent);

       }else{
            Toast.makeText(mContext, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
       }
    }


}

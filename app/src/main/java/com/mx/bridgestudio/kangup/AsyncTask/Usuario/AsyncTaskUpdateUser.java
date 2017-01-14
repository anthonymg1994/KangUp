package com.mx.bridgestudio.kangup.AsyncTask.Usuario;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOuser;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by USUARIO on 04/01/2017.
 */

public class AsyncTaskUpdateUser  extends AsyncTask<String,Integer,Boolean> {
    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    private URL url;
    private User user;
    webServices services = new webServices();
    DAOuser Duser = new DAOuser();
    private Context mContext;
    private boolean flag = false;

    public AsyncTaskUpdateUser(Context context,User user) {
        super();
        mContext = context;
        this.user = user;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext,R.style.MyDialogTheme);
        progressDialog.setMessage("Procesando...");
        progressDialog.show();
        progressDialog.setCancelable(false);
    }
    @Override
    protected Boolean doInBackground(String... params) {
        flag = Duser.updateUser(user);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(result){
            Toast.makeText(mContext, "actualizado nuevo usuario", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(mContext, "vuelve a intentarlo", Toast.LENGTH_SHORT).show();
        }
    }


}

package com.mx.bridgestudio.kangup.AsyncTask.Usuario;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.WindowManager;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOuser;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.CategoryActivity;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by USUARIO on 29/11/2016.
 */

public class AsyncInsertUser  extends AsyncTask<String,Integer,Boolean> {
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

    public AsyncInsertUser(Context context,User user) {
        super();
        mContext = context;
        this.user = user;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.show();
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        progressDialog.setCancelable(false);

    }
    @Override
    protected Boolean doInBackground(String... params) {
        flag = Duser.registerUser(user);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(result){
            Toast.makeText(mContext, "Insertado nuevo usuario", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent().setClass(
                    mContext,CategoryActivity.class);
            mContext.startActivity(intent);

        }else{
            Toast.makeText(mContext, "Vuelve a intentarlo", Toast.LENGTH_SHORT).show();
        }
    }


}
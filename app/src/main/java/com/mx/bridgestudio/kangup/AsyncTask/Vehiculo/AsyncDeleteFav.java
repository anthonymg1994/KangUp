package com.mx.bridgestudio.kangup.AsyncTask.Vehiculo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.WindowManager;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOVehiculo;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Isaac on 21/12/2016.
 */

public class AsyncDeleteFav extends AsyncTask<String,Integer,Boolean> {

    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    private URL url;
    webServices services = new webServices();
    DAOVehiculo Dveh = new DAOVehiculo();
    private Context mContext;
    private boolean flag = false;

    private int id_vehiculo = 0;
    private int id_user = 0;

    public AsyncDeleteFav(Context context, int id_vehiculo, int id_user) {
        super();
        mContext = context;
        this.id_vehiculo = id_vehiculo;
        this.id_user = id_user;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext,R.style.MyDialogTheme);
        progressDialog.show();
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        progressDialog.setCancelable(false);

    }
    @Override
    protected Boolean doInBackground(String... params) {
        flag = Dveh.deleteFavoriteCar(id_vehiculo,id_user);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(result){
            Toast.makeText(mContext, "Favorito eliminado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent().setClass(
                    mContext, FavoriteActivity.class);
            mContext.startActivity(intent);

        }else{
            Toast.makeText(mContext, "Vuelve a intentarlo", Toast.LENGTH_SHORT).show();
        }
    }
}

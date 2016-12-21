package com.mx.bridgestudio.kangup.AsyncTask.Reservacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.AsyncTask.Usuario.AsynkTaskUser;
import com.mx.bridgestudio.kangup.Controllers.DAO.DAOReservaciones;
import com.mx.bridgestudio.kangup.Controllers.DAO.DAOuser;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.Reservacion;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.ForgotActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by USUARIO on 20/12/2016.
 */

public class asyncEmailConfirmacion extends AsyncTask<String, Integer, Boolean> {

    ProgressDialog progressDialog = null;
    private AsynkTaskUser mAuthTask = null;

    Reservacion reservacion;
    Boolean flag = false;
    private Context mContext;

    public asyncEmailConfirmacion(Context mContext, Reservacion reservacion) {
        this.mContext = mContext;
        this.reservacion = reservacion;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.show();
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


    @Override
    protected Boolean doInBackground(String... strings) {
        // TODO: attempt authentication against a network service.


        // Simulate network access.
        DAOReservaciones dao = new DAOReservaciones();
        try {
            flag = dao.SendConfirmationReservationEmail(reservacion);
            Thread.sleep(2000);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return flag;

    }

    @Override
    protected void onPostExecute(final Boolean success) {
        mAuthTask = null;
        String texto = "";
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (success) {
            texto = "Se envio un enlace a tu correo electronico";

        } else {
            texto = "No se pudo enviar enlace a tu correo verifica tu email";
        }
        Toast msg = Toast.makeText(mContext,
                texto, Toast.LENGTH_SHORT);
        msg.show();

    }

    @Override
    protected void onCancelled() {
        mAuthTask = null;
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}



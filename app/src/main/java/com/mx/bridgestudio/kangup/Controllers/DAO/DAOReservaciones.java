package com.mx.bridgestudio.kangup.Controllers.DAO;

import android.app.ProgressDialog;
import android.os.StrictMode;

import com.mx.bridgestudio.kangup.Models.Reservacion;

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
 * Created by USUARIO on 30/11/2016.
 */

public class DAOReservaciones {
    public Boolean SendConfirmationReservationEmail(Reservacion reservacion) throws JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;
        Boolean flag =false ;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://kangup.com.mx/index.php/confirmationReservationEmail");
            //cambiar nombre de metodo de vehiculos
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_reservacion",reservacion.getId());
            OutputStreamWriter os = new OutputStreamWriter(httpURLConnection.getOutputStream());
            os.write(jsonParam.toString());
            os.flush();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line;
        StringBuffer response = new StringBuffer();
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            if (httpURLConnection.getResponseCode() == 500)
                return false;
            int statusCode = httpURLConnection.getResponseCode();
            InputStream is = null;
            if (statusCode >= 200 && statusCode < 400) {
                // Create an InputStream in order to extract the response object
                is = httpURLConnection.getInputStream();
            } else {
                is = httpURLConnection.getErrorStream();
            }
            if (reader != null) {
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } else
                response = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpURLConnection.disconnect();


        return true;
    }
}

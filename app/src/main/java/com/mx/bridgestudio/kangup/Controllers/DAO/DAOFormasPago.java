package com.mx.bridgestudio.kangup.Controllers.DAO;

import android.app.ProgressDialog;
import android.os.StrictMode;

import com.mx.bridgestudio.kangup.Models.PaymentForm;

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
 * Created by Isaac on 19/12/2016.
 */

public class DAOFormasPago {

    PaymentForm pay = null;
    private JSONObject jsonParam  = new JSONObject();
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;

    public boolean registerPaymentForms(PaymentForm pay){
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL("https://kangup.com.mx/index.php/insertFormaPago");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();;
            jsonParam.put("id_usuario",pay.getId_usuario());
            jsonParam.put("id_forma_pago",pay.getId_forma_pago());
            jsonParam.put("numero_cuenta",pay.getNum_cuenta());
            jsonParam.put("mes_venc",pay.getMes_venc());
            jsonParam.put("anio_venc",pay.getAnio_venc());
            jsonParam.put("cvv", pay.getCvv());
            OutputStreamWriter os = new OutputStreamWriter(httpURLConnection.getOutputStream());
            os.write(jsonParam.toString());
            os.flush();
        }catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line;
        StringBuffer response = new StringBuffer();
        try {
            if(httpURLConnection.getResponseCode() == 500)
                return false;
            //return "0";
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            if(reader != null) {
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }else
                response = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpURLConnection.disconnect();
        String json = response.toString();

        return true;
    }

    public String getPaymentFormsByUser(PaymentForm pay) throws JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("https://kangup.com.mx/index.php/formasPagoUsuario");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_usuario",String.valueOf(pay.getId_usuario()));
            OutputStreamWriter os = new OutputStreamWriter(httpURLConnection.getOutputStream());
            os.write(jsonParam.toString());
            os.flush();
        }catch (JSONException e) {
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
            if(httpURLConnection.getResponseCode() == 500)
                return "0";
            int statusCode = httpURLConnection.getResponseCode();
            InputStream is = null;
            if (statusCode >= 200 && statusCode < 400) {
                // Create an InputStream in order to extract the response object
                is = httpURLConnection.getInputStream();
            }
            else {
                is = httpURLConnection.getErrorStream();
            }
            if(reader != null) {
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }else
                response = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpURLConnection.disconnect();


        return response.toString();
    }

}

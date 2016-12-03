package com.mx.bridgestudio.kangup.AsyncTask.Usuario;

/**
 * Created by USUARIO on 01/12/2016.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class asyn extends AsyncTask<URL,String,String> {
    //private final String URL = "http://10.0.3.2/Jappli/public/mobile/";
    //private final String URL = "http://104.236.191.206/api/v1/mobile/";
    private final String URL = "http://kangup.com.mx/index.php/login";
    private URL url;
    private HttpURLConnection httpURLConnection;
    private JSONObject responseJson;
    private JSONObject jsonParam;
    private ProgressDialog progressDialog;
    private View rootLayout;
    private Context context;
    private String strPatientD;
    private String email = "";
    private String password ="";
    private int idObject = -1;
    private int idPatient = -1;

    public asyn(Context context,
                            String email,String password){

        this.context = context;
        this.email = email;
        this.password = password;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Solicitando cita...");
        progressDialog.show();
        progressDialog.setCancelable(false);

    }

    @Override
    protected String doInBackground(URL... params) {
        try {
            url = new URL("http://kangup.com.mx/index.php/login");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            jsonParam = new JSONObject();
                /*jsonParam.put("idDoctor", idObject);
                jsonParam.put("descripcionPaciente", idObject);
                jsonParam.put("estado", 0);*/
            jsonParam.put("email", email);
            jsonParam.put("password", password);
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
            System.out.println("RESPONSE CODE: " +httpURLConnection.getResponseCode());
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
        return response.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(progressDialog.isShowing())
            progressDialog.dismiss();
        System.out.println("RESULTADO : " + result); // DEBUG


    }
}
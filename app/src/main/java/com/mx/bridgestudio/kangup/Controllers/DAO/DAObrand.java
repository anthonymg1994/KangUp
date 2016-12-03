package com.mx.bridgestudio.kangup.Controllers.DAO;

import android.app.ProgressDialog;
import android.os.StrictMode;

import com.mx.bridgestudio.kangup.Models.Brand;
import com.mx.bridgestudio.kangup.Models.User;

import org.json.JSONArray;
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
import java.net.URLEncoder;

import static com.mx.bridgestudio.kangup.R.id.user;

/**
 * Created by USUARIO on 29/11/2016.
 */

public class DAObrand {
    Brand brand = null;

    public String getBrandsbyType(Brand brand) throws JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://kangup.com.mx/index.php/brands");
             httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_categoria",String.valueOf(brand.getId_categoria()));
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


    public Brand sets(JSONObject obj) throws JSONException {
        Brand brand = new Brand();
        brand.setId(obj.getInt("id"));
        brand.setId_categoria(obj.getInt("id_categoria"));
        brand.setNombre(obj.getString("nombre"));

        return brand;
    }
}

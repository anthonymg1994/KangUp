package com.mx.bridgestudio.kangup.Controllers.DAO;

import android.app.ProgressDialog;
import android.os.StrictMode;

import com.mx.bridgestudio.kangup.Models.Brand;
import com.mx.bridgestudio.kangup.Models.DetalleViaje;
import com.mx.bridgestudio.kangup.Models.RoadTrip;
import com.mx.bridgestudio.kangup.Models.Rutas;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Models.Vehicle;

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

/**
 * Created by USUARIO on 07/12/2016.
 */

public class DAOviajes {

    User user = null;
    DetalleViaje det;

    public String getHistoryByUser(User user) throws JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://kangup.com.mx/index.php/historial");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_usuario",String.valueOf(user.getId()));
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

    public DetalleViaje getHistoryDetailByUser(int idUser, int idViaje, int idu) throws IOException, JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://kangup.com.mx/index.php/historialDetalle");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_usuario",String.valueOf(idUser));
            jsonParam.put("id",String.valueOf(idViaje));
            jsonParam.put("id_usuario",String.valueOf(idu));
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
            System.out.println("RESPONSE CODE: " + httpURLConnection.getResponseCode());
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
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
        String json = response.toString();

        JSONArray valarray = new JSONArray(json);

        JSONObject objVehicle = valarray.getJSONObject(0);
        det = new DetalleViaje();
        det = setViajeDetail(objVehicle);


        return det;
    }

    public DetalleViaje setViajeDetail(JSONObject obj) throws JSONException {
        DetalleViaje vehicle = new DetalleViaje();
        vehicle.setMarca(obj.getString("Marca"));
        vehicle.setModelo(obj.getString("Modelo"));
        vehicle.setAnio(obj.getString("Anio"));
        vehicle.setFecha(obj.getString("Fecha"));
        vehicle.setTotal(obj.getString("Total"));
        vehicle.setIdViaje(obj.getInt("ID"));
        vehicle.setHoraInicio(obj.getString("hora_inicio"));
        vehicle.setHoraTermino(obj.getString("hora_termino"));
        vehicle.setValoracion(obj.getString("valoracion"));
        vehicle.setTiempo(obj.getString("tiempo"));
        vehicle.setKm(obj.getString("km"));
        vehicle.setCalificacion(obj.getString("calificacion"));
        vehicle.setFormaPago(obj.getString("FormaPago"));
        vehicle.setNumCuenta(obj.getString("numero_cuenta"));
        vehicle.setSocio(obj.getString("Nombre") + " " + obj.getString("Apellidos"));
        vehicle.setIdReservacion(obj.getInt("idReservacion"));


        return vehicle;
    }

    public String getRoutesByTrip(Rutas ruta) throws JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://kangup.com.mx/index.php/historialRutas");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_reservacion",String.valueOf(ruta.getId_reservacion()));
            jsonParam.put("id_usuario",String.valueOf(ruta.getIdUsuario()));
            jsonParam.put("id_reservacion",String.valueOf(ruta.getId_reservacion()));
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

package com.mx.bridgestudio.kangup.Controllers.DAO;

import android.app.ProgressDialog;
import android.os.StrictMode;

import com.mx.bridgestudio.kangup.Models.Brand;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Models.Vehicle;

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
import java.util.Date;

/**
 * Created by USUARIO on 02/12/2016.
 */

public class DAOVehiculo {
    Vehicle vehicle = null;

    public String getVehiclesByBrand(Vehicle vehicle) throws JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://kangup.com.mx/index.php/vehiculos");
            //cambiar nombre de metodo de vehiculos
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_marca", String.valueOf(vehicle.getId_brand()));
            jsonParam.put("id_categoria", String.valueOf(vehicle.getId_categoria()));
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
                return "0";
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


        return response.toString();
    }

    public Vehicle getVehicleDetail(Vehicle vehicle) throws IOException, JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;

        try {
            URL url = new URL("https://kangup.com.mx/index.php/detalles");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id", vehicle.getId());
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
        JSONObject objVehicle = new JSONObject(json);
        vehicle = new Vehicle();
        vehicle = setsVehicle(objVehicle);


        return vehicle;
    }

    public Brand sets(JSONObject obj) throws JSONException {
        Brand brand = new Brand();
        brand.setId(obj.getInt("id"));
        brand.setId_categoria(obj.getInt("id_categoria"));
        brand.setNombre(obj.getString("nombre"));

        return brand;
    }

    public Vehicle setsVehicle(JSONObject obj) throws JSONException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(obj.getInt("id"));
        vehicle.setMarca(obj.getString("Marca"));
        vehicle.setModel(obj.getString("Modelo"));
        vehicle.setYear(obj.getString("year"));
        vehicle.setValoracion(obj.getInt("puntuacion"));
        // vehicle.setId_brand(obj.getString("id_brand"));
        ///vehicle.setId_categoria(obj.getString("id_categoria"));
        // vehicle.setId_parther(obj.getString("Socio"));
        //apellidos vehicle.setId_parther(obj.getString("apellidos"));
         vehicle.setDescription(obj.getString("descripcion"));
         vehicle.setSpecifications(obj.getString("especificaciones"));
        vehicle.setFoto(obj.getString("foto"));
        vehicle.setTarifa(obj.getString("tarifa"));
        //vehicle.setStatus(obj.getString("status"));

        return vehicle;
    }

    public String getFavorites(User user) throws JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("https://kangup.com.mx/index.php/favoritos");
            //cambiar nombre de metodo de vehiculos
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_usuario", String.valueOf(user.getId()));
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
                return "0";
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


        return response.toString();
    }

    public boolean deleteFavoriteCar(int id_vehiculo, int id_user){
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL("https://kangup.com.mx/index.php/deleteFav");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            //jsonParam.put("id",user.getId());
            jsonParam.put("id_vehiculo",id_vehiculo);
            jsonParam.put("id_usuario",id_user);
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

    public boolean addFavoriteCar(int id_vehiculo, int id_user){
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL("https://kangup.com.mx/index.php/addFav");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            //jsonParam.put("id",user.getId());
            jsonParam.put("id_vehiculo",id_vehiculo);
            jsonParam.put("id_usuario",id_user);
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


    public String getVehiclesTop(Vehicle vehicle, Date date, Date time,Date time_final) throws JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("https://kangup.com.mx/index.php/topCar");
            //cambiar nombre de metodo de vehiculos
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_marca", String.valueOf(vehicle.getId_brand()));
            jsonParam.put("id_categoria", String.valueOf(vehicle.getId_categoria()));
            jsonParam.put("fecha_reservacion", date.getDate());
            jsonParam.put("hora_inicio",time.getTime());
            jsonParam.put("hora_termino", time_final.getTime());
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
                return "0";
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


        return response.toString();
    }


    public String getVehiclesByScoreFilter(Vehicle vehicle, Date date, Date time,Date time_final) throws JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("https://kangup.com.mx/index.php/scoreByDate");
            //cambiar nombre de metodo de vehiculos
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_marca", String.valueOf(vehicle.getId_brand()));
            jsonParam.put("id_categoria", String.valueOf(vehicle.getId_categoria()));
            jsonParam.put("fecha_reservacion", date.getDate());
            jsonParam.put("hora_inicio",time.getTime());
            jsonParam.put("hora_termino", time_final.getTime());
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
                return "0";
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


        return response.toString();
    }

    public String getRecommendCVehicles(Vehicle vehicle, Date date, Date time,Date time_final) throws JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("https://kangup.com.mx/index.php/recommend");
            //cambiar nombre de metodo de vehiculos
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_marca", String.valueOf(vehicle.getId_brand()));
            jsonParam.put("id_categoria", String.valueOf(vehicle.getId_categoria()));
            jsonParam.put("fecha_reservacion", date.getDate());
            jsonParam.put("hora_inicio",time.getTime());
            jsonParam.put("hora_termino", time_final.getTime());
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
                return "0";
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


        return response.toString();
    }

    public String getAllPhotoById(Vehicle vehicle) throws JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://kangup.com.mx/index.php/fotosXVehiculo");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(4000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_vehiculo",String.valueOf(vehicle.getId()));
            OutputStreamWriter os = new OutputStreamWriter(httpURLConnection.getOutputStream());
            os.write(jsonParam.toString());
            os.flush();
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

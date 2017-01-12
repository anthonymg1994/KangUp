package com.mx.bridgestudio.kangup.Controllers.DAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.StrictMode;
import android.text.BoringLayout;

import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Models.UserPhoto;
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

/**
 * Created by USUARIO on 28/11/2016.
 */


//Todos los metodos de usuarios van aqui!
public class DAOuser {
    User user = null;
    private JSONObject jsonParam  = new JSONObject();
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;

    public User authenticate(User user) throws IOException, JSONException {
            try {
               URL url = new URL("http://kangup.com.mx/index.php/login");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();
                jsonParam.put("email", user.getEmail());
                jsonParam.put("password", user.getPassword());
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
            String json = response.toString();
            JSONObject objUser = new JSONObject(json);
            user = new User();
            user = sets(objUser);
        return user;
    }

    public boolean registerUser(User user){
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL("http://kangup.com.mx/index.php/nuevoUsuario");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            //jsonParam.put("id",user.getId());
            jsonParam.put("nombre",user.getFirstName());
            jsonParam.put("apellido_paterno",user.getAp_paterno());
            jsonParam.put("apellido_materno",user.getAp_materno());
            jsonParam.put("telefono",user.getCellphone());
            jsonParam.put("email",user.getEmail());
            jsonParam.put("fecha_nacimiento",user.getFnacimiento());
            jsonParam.put("ciudad",user.getCiudad());
            jsonParam.put("password",user.getPassword());
            //obtener id de pago dependiendo la consulta de pago predeterminado
            jsonParam.put("id_forma_pago",user.getPay());
            jsonParam.put("status","1");
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

    public boolean updateUser(User user){
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL("http://kangup.com.mx/index.php/updateUsuario");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id",user.getId());
            jsonParam.put("nombre",user.getFirstName());
          //  jsonParam.put("apellidos",user.getLastName());
            jsonParam.put("telefono",user.getCellphone());
            jsonParam.put("domicilio",user.getAddress());

            jsonParam.put("email",user.getEmail());
            jsonParam.put("fecha_nacimiento",user.getFnacimiento());
            jsonParam.put("ciudad",user.getCiudad());
            jsonParam.put("password",user.getPassword());
            //obtener id de pago dependiendo la consulta de pago predeterminado
            jsonParam.put("id_forma_pago",user.getPay());
            jsonParam.put("status","1");
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

    public User sets(JSONObject obj) throws JSONException {
        User user = new User();
        user.setId(obj.getInt("id"));
        user.setFirstName(obj.getString("nombre"));
        user.setAp_paterno(obj.getString("apellido_paterno"));
        user.setAp_paterno(obj.getString("apellido_materno"));
        user.setCellphone(obj.getString("telefono"));
        user.setEmail(obj.getString("email"));
        user.setAddress(obj.getString("domicilio"));
        user.setFnacimiento(obj.getString("fecha_nacimiento"));
        user.setCiudad(obj.getString("ciudad"));
        user.setPassword(obj.getString("password"));
        user.setPay(obj.getInt("id_forma_pago"));
        user.setStatus(obj.getString("status"));

        return user;
    }

    //Revisar booleanos
    public Boolean RecuperarEmail(String email) throws JSONException {
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        InputStream in = null;
        Boolean flag =false ;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://kangup.com.mx/index.php/password");
            //cambiar nombre de metodo de vehiculos
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("email", email);
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

    public boolean insertPhotoName(UserPhoto us){///falta asynctask
        ProgressDialog progressDialog;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL("http://kangup.com.mx/index.php/namePhoto");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            JSONObject jsonParam = new JSONObject();
            //jsonParam.put("id",user.getId());
            jsonParam.put("imagen",us.getFoto());
            jsonParam.put("path",us.getPath());
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
}

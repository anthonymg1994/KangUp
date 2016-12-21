package com.mx.bridgestudio.kangup.AsyncTask.Formas_Pago;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOFormasPago;
import com.mx.bridgestudio.kangup.Controllers.DAO.DAOVehiculo;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFavorites;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPaymentFormsUser;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.PaymentForm;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.PaymentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

import static com.mx.bridgestudio.kangup.R.id.user;

/**
 * Created by Isaac on 20/12/2016.
 */

public class AsyncPaymentFormsUser extends AsyncTask<String,Integer,String> {

    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    URL url;
    private PaymentForm[] arrayPay;
    private PaymentForm pay = new PaymentForm();
    private String objPay;

    private webServices services = new webServices();
    private DAOFormasPago Dpay = new DAOFormasPago();

    public OnDataSendPaymentFormsUser SendToActivity;//Call back interface


    Context mContext;

    private boolean flag = false;

    public AsyncPaymentFormsUser(OnDataSendPaymentFormsUser SendToActivity, Context context, PaymentForm p) {
        super();
        this.SendToActivity = SendToActivity;
        mContext = context;
        this.pay = p;

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            objPay = Dpay.getPaymentFormsByUser(pay);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return objPay;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Procesando...");
        progressDialog.show();
        progressDialog.setCancelable(false);

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (result.equals("0")) {
            Toast.makeText(mContext, "Vuelve a intentarlo" + result, Toast.LENGTH_SHORT).show();
        } else {
            //      Toast.makeText(mContext, "Bienvenido "+vehicle, Toast.LENGTH_SHORT).show();

            try {
                JSONArray jsonarray = new JSONArray(result);
                arrayPay = new PaymentForm[jsonarray.length()];

                for (int i = 0; i < jsonarray.length(); i++) {
                    arrayPay[i] = new PaymentForm();
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    arrayPay[i].setId(jsonobject.getInt("id"));
                    arrayPay[i].setTipoPago(jsonobject.getString("nombre"));
                    arrayPay[i].setNum_cuenta(jsonobject.getString("numero_cuenta"));
                    arrayPay[i].setMes_venc(jsonobject.getString("mes_venc"));
                    arrayPay[i].setAnio_venc(jsonobject.getString("anio_venc"));
                    arrayPay[i].setCvv(jsonobject.getString("cvv"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mContext, PaymentActivity.class);
            SendToActivity.sendDataPaymentFormsUser(arrayPay);
            //Toast.makeText(mContext, ", Toast.LENGTH_SHORT).show();
//  intent.putExtra("objBrands",arrayBrands);

            //  mContext.startActivity(intent);


        }
    }
}

package com.mx.bridgestudio.kangup.AsyncTask.Formas_Pago;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.WindowManager;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.DAO.DAOFormasPago;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.PaymentForm;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.MenuActivity.PaymentActivity;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Isaac on 19/12/2016.
 */

public class AsyncInsertPaymentForms extends AsyncTask<String,Integer,Boolean> {

    private JSONObject responseJson;
    private ProgressDialog progressDialog;
    private HttpURLConnection httpURLConnection;
    private String param1;
    private String param2;
    private URL url;
    private PaymentForm pay;
    webServices services = new webServices();
    DAOFormasPago form = new DAOFormasPago();
    private Context mContext;
    private boolean flag = false;

    public AsyncInsertPaymentForms(Context context, PaymentForm pay) {
        super();
        mContext = context;
        this.pay = pay;

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
        flag = form.registerPaymentForms(pay);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if(result){
            Toast.makeText(mContext, "Insertado nueva forma de pago", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent().setClass(
                    mContext, PaymentActivity.class);
            mContext.startActivity(intent);

        }else{
            Toast.makeText(mContext, "Vuelve a intentarlo", Toast.LENGTH_SHORT).show();
        }
    }
}

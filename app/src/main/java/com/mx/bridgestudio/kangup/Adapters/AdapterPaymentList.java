package com.mx.bridgestudio.kangup.Adapters;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Lists.ListPaymentForm;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;

/**
 * Created by Isaac on 08/11/2016.
 */

public class AdapterPaymentList extends ArrayAdapter<ListPaymentForm> {

    Activity context;
    private ArrayList<ListPaymentForm> lista;
    public static int idpago=0;
    public static String numcuenta="";
    public static String mes="";
    public static String anio="";
    public static String cvv="";
    public static int id=0;

    public AdapterPaymentList(Activity context, ArrayList<ListPaymentForm> list) {
        super(context, R.layout.payment_list, list);
        // TODO Auto-generated constructor stub
        this.context = (Activity) context;
        this.lista = list;
    }
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        View item = null;
        ImageView imgImg = null;
        if (item == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.payment_list, null);
            imgImg = (ImageView)item.findViewById(R.id.imagePay);
            TextView Nnom = (TextView) item.findViewById(R.id.textDataPay);

            Nnom.setText(lista.get(arg0).getNum_cuenta());
            imgImg.setImageResource(R.drawable.forma_icon);
            imgImg.setColorFilter(ContextCompat.getColor(item.getContext(),R.color.colorPrimary));


        }
        return item;

    }

    public ListPaymentForm getItem(int position){
        return lista.get(position);
    }
}

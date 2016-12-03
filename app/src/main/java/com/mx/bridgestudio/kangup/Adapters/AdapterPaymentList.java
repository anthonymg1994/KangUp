package com.mx.bridgestudio.kangup.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Payment;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;

/**
 * Created by Isaac on 08/11/2016.
 */

public class AdapterPaymentList extends ArrayAdapter<Payment> {

    Activity context;
    private ArrayList<Payment> lista;
    private Payment[] objects;
    private View listView;

    public AdapterPaymentList(Activity context, ArrayList<Payment> list) {
        super(context, R.layout.payment_list, list);
        // TODO Auto-generated constructor stub
        this.context = (Activity) context;
        this.lista = lista;
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

        }
        return item;

    }
}

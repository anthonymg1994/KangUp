package com.mx.bridgestudio.kangup.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mx.bridgestudio.kangup.Adapters.AdapterPaymentList;
import com.mx.bridgestudio.kangup.Models.Payment;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listPay;
    private ArrayList<Payment> tipos = new ArrayList<>();
    private ArrayAdapter<Payment> AdapterArray;
    private AdapterPaymentList adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        listPay = (ListView)findViewById(R.id.listPayment);
        //fillList();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

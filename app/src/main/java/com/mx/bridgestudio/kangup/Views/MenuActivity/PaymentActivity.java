package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdapterPaymentList;
import com.mx.bridgestudio.kangup.Models.Payment;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;

import java.util.ArrayList;

public class PaymentActivity extends DrawerActivity implements AdapterView.OnItemClickListener {

    private ListView listPay;
    private ArrayList<Payment> tipos = new ArrayList<>();
    private ArrayAdapter<Payment> AdapterArray;
    private AdapterPaymentList adaptador;
    private AlertDialog alertTypePayment;
    public static int type=0;

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;

    CharSequence[] values = {"Tarjeta de débito/crédito","Tiendas de conveniencia"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPay);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listPay = (ListView)findViewById(R.id.listPayment);
        adaptador = new AdapterPaymentList(this,tipos);
        listPay.setAdapter(adaptador);
        fillList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.FabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAlertDialogWithRadioButtonGroup() ;
            }
        });

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(PaymentActivity.this, CategoryActivity.class));
            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(PaymentActivity.this, FavoriteActivity.class));
            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(PaymentActivity.this, HistoryActivity.class));
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void CreateAlertDialogWithRadioButtonGroup(){


        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);

        builder.setTitle("Selecciona el tipo de pago: ");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        finish();
                        startActivity(new Intent(PaymentActivity.this, AddPaymentActivity.class));
                        type=1;
                        break;
                    case 1:
                        Toast.makeText(PaymentActivity.this, "Second Item Clicked", Toast.LENGTH_LONG).show();
                        type=2;
                        break;
                }
                alertTypePayment.dismiss();
            }
        });
        alertTypePayment = builder.create();
        alertTypePayment.show();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            startActivity(new Intent(PaymentActivity.this, CategoryActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void fillList(){
        Payment list = new Payment();
        list.setFormsofpayment( "Tipos de pago");

        for(int x=0;x<1;x++){
            tipos.add(x,list);
        }
    }
}

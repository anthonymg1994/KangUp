package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mx.bridgestudio.kangup.Adapters.AdapterPaymentList;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.DAO.DAOFormasPago;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPaymentFormsUser;
import com.mx.bridgestudio.kangup.Controllers.Paypal.Paypal;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.Lists.ListPaymentForm;
import com.mx.bridgestudio.kangup.Models.Payment;
import com.mx.bridgestudio.kangup.Models.PaymentForm;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CatalogCar;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.Reservacion;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentActivity extends DrawerActivity implements AdapterView.OnItemClickListener,OnDataSendPaymentFormsUser {

    private ListView listPay;
    private ArrayList<ListPaymentForm> tipos = new ArrayList<>();
    private ArrayAdapter<Payment> AdapterArray;
    private AdapterPaymentList adaptador;
    private AlertDialog alertTypePayment;
    private AlertDialog alertPayment;
    public static int type=0;
    private Control control = new Control();
    private webServices webs = new webServices();
    private DAOFormasPago Dpay = new DAOFormasPago();
    private PaymentForm pa = new PaymentForm();
    private SqliteController sql;
    int id_pago=0;
    public static ListPaymentForm pay = new ListPaymentForm();

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;
    private DrawerActivity drw = new DrawerActivity();

    CharSequence[] values = {"Tarjeta de débito/crédito","Tiendas de conveniencia"};
    CharSequence[] values2 = {"Editar","Eliminar"};

    public static PaymentForm payu = new PaymentForm();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPay);
        setSupportActionBar(toolbar);
        //drw.setNameToolbar("Metodos de pago");
        getSupportActionBar().setTitle("Metodos de pago");

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listPay = (ListView)findViewById(R.id.listPayment);

        adaptador = new AdapterPaymentList(this,tipos);
        listPay.setAdapter(adaptador);

        listPay.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Snackbar snackbar = Snackbar.make(view, list.getNum_cuenta(), Snackbar.LENGTH_SHORT);
                //snackbar.show();
            }

        });
        listPay.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                pay = adaptador.getItem(position);
                CardOption();


                return true;
            }

        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.FabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAlertDialogWithRadioButtonGroup() ;
            }
        });

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent));
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    if (control.isOnline()) {
                    finish(); // close this P and return to preview activity (if there is any)
                    startActivity(new Intent(PaymentActivity.this, CategoryActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
              //  } else {
                //    Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(PaymentActivity.this, NewsActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //} else {
                  //  Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(PaymentActivity.this, FavoriteActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //} else {
                  //  Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

    //            if (control.isOnline()) {

                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(PaymentActivity.this, HistoryActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
      //          } else {
        //            Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

          //      }

            }
        });

        sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
        sql.Connect();
        User user = new User();
        user = sql.user();
        pa.setId_usuario(user.getId());
        sql.Close();

       // if(control.isOnline()){
            webs.getFormaPagoByUser(PaymentActivity.this,this,pa);
        //}else{
            //Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

        //}

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
                        //startActivity(new Intent(PaymentActivity.this, AddPaymentActivity.class));
                        startActivity(new Intent(PaymentActivity.this, Paypal.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        type=1;
                        break;
                    case 1:
                        Toast.makeText(PaymentActivity.this, "Tiendas de conveniencia", Toast.LENGTH_LONG).show();
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
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        return super.onOptionsItemSelected(item);
    }

    public void fillList(PaymentForm[] pay){
        ListPaymentForm[] list = new ListPaymentForm[pay.length];
        for(int i = 0 ; i < pay.length ; i++){
            list[i] = new ListPaymentForm();
            list[i].setId(pay[i].getId());
            list[i].setId_usuario(pay[i].getId_usuario());
            list[i].setId_forma_pago(pay[i].getId_forma_pago());
            list[i].setTipoPago(pay[i].getTipoPago());
            list[i].setNum_cuenta(pay[i].getNum_cuenta());
            list[i].setMes_venc(pay[i].getMes_venc());
            list[i].setAnio_venc(pay[i].getAnio_venc());
            list[i].setCvv(pay[i].getCvv());

            tipos.add(i,list[i]);
        }
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void sendDataPaymentFormsUser(PaymentForm[] obj) {
        fillList(obj);
    }

    public void CardOption(){


        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);

        builder.setTitle("¿Qué acción deseas realizar: ");

        builder.setSingleChoiceItems(values2, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        finish();
                        //startActivity(new Intent(PaymentActivity.this, AddPaymentActivity.class));
                        startActivity(new Intent(PaymentActivity.this, EditPaymentActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                    case 1:
                        alertConfirmacionDelete();
                        break;
                }
                alertPayment.dismiss();
            }
        });
        alertPayment = builder.create();
        alertPayment.show();

    }

    public void alertConfirmacionDelete() {
        new AlertDialog.Builder(PaymentActivity.this)
                .setTitle("Confirmación")
                .setMessage("¿Desea eliminar la forma de pago seleccionada?")
                .setIcon(R.drawable.perfil_icon)
                .setPositiveButton("Eliminar",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Ion.with(PaymentActivity.this)
                                        .load("POST", "http://kangup.com.mx/index.php/deleteFormaPago")
                                        .setBodyParameter("id", String.valueOf(AdapterPaymentList.idpago))
                                        .asString()
                                        .setCallback(new FutureCallback<String>() {
                                            @Override
                                            public void onCompleted(Exception e, String result) {
                                                //String info="";
                                                Toast msg = Toast.makeText(getBaseContext(),
                                                        result, Toast.LENGTH_LONG);
                                                msg.show();
                                                adaptador.notifyDataSetChanged();
                                                finish();
                                                startActivity(new Intent(PaymentActivity.this, PaymentActivity.class));
                                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                            }
                                        });
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).show();
    }
}

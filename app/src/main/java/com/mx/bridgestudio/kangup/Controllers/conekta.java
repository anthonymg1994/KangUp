package com.mx.bridgestudio.kangup.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;

import org.apache.commons.net.chargen.CharGenTCPClient;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import io.conekta.conektasdk.Card;
import io.conekta.conektasdk.Conekta;
import io.conekta.conektasdk.Token;
import io.conekta.conektasdk.Connection;
import io.conekta.conektasdk.*;
import io.conekta.conektasdk.Conekta;
/**
 * Created by USUARIO on 23/01/2017.
 */

public class conekta extends DrawerActivity implements Token.CreateToken, View.OnClickListener {
    EditText numeroTarjeta, nombre, anio,mes,cvv;
    protected DrawerLayout mDrawer;
    private ImageButton catalogo,noticias,favoritos,historial;
    List<EditText> campos;
    int MY_SCAN_REQUEST_CODE = 0;
    private String stringTarjeta;
    private Button guardar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_history_details);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //control.changeColorStatusBar(HistoryDetailsActivity.this);

        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.tarjeta, null, false);
        mDrawer.addView(contentView, 0);
        getSupportActionBar().setTitle("Detalle de viaje");

        if (savedInstanceState != null) {
            stringTarjeta = savedInstanceState.getString("tarjeta");
        }
        numeroTarjeta = (EditText)findViewById(R.id.noTarjeta);
        nombre = (EditText)findViewById(R.id.nombre);
        anio = (EditText)findViewById(R.id.anio);
        mes = (EditText)findViewById(R.id.mes);
        cvv = (EditText)findViewById(R.id.cvv);
        campos = Arrays.asList(numeroTarjeta,nombre,anio,mes,cvv);
        guardar = (Button) findViewById(R.id.guardar);
        guardar.setOnClickListener(conekta.this);


        Conekta.setPublicKey("key_G9PF7Lzdxp3wcpykzMqJYcA");
        Conekta.collectDevice(this);





    }

    public boolean onCreateOptionMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.guardarmenu) {
            if (verificaContenido()) {

                stringTarjeta = stringTarjeta.replace(" ", "").trim();///se agrego esta linea para quitar los espacios por que si no no funciona
                Card card = new Card(nombre.getText().toString(), stringTarjeta, cvv.getText().toString(),
                        mes.getText().toString(), anio.getText().toString());

                Token token = new Token(this);

                token.onCreateTokenListener(conekta.this);
                token.create(card);

            } else {
                Toast.makeText(this,"Faltan campos por llenar", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean verificaContenido() {
        boolean falta = false;
        for (EditText texto : campos) {
            texto.setHintTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
        for (EditText texto : campos) {
            if (texto.getText().toString().trim().isEmpty()) {
                texto.setHintTextColor(ContextCompat.getColor(this, R.color.yellow));
                falta = true;
            }
        }
        return !falta;
    }

    public void onScanPress(View v) {
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false);
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, false);
        scanIntent.putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, ContextCompat.getColor(this, R.color.colorPrimaryDark));
        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                stringTarjeta = scanResult.getFormattedCardNumber() + "\n";
                numeroTarjeta.setText(scanResult.getRedactedCardNumber());
                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    mes.setText(String.valueOf(scanResult.expiryMonth));//se agrego el string value of
                    anio.setText(String.valueOf(scanResult.expiryYear));//se agrego el string value of
                }

                if (scanResult.cvv != null) {
                    cvv.setText(scanResult.cvv);
                }

            } else {
                resultDisplayStr = "Scan was canceled.";
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
        }
        // else handle other activity results
    }

    @Override
    public void onCreateTokenReady(JSONObject data) {

        JSONObject payment_params;

        try {
            //TODO: Create charge
             Log.d("Token::::", data.getString("id"));


            Toast.makeText(this, "Token Creado", Toast.LENGTH_SHORT).show();
            payment_params = new JSONObject("{"
                    + "'description':'Stogies',"
                    + "'amount': 20000,"
                    + "'currency':'MXN',"
                    + "'reference_id':'9839-wolf_pack',"
                    + "'card': 'tok_test_visa_4242',"
                    + "'details': {"
                    + "'name': 'Arnulfo Quimare',"
                    + "'phone': '403-342-0642',"
                    + "'email': 'logan@x-men.org',"
                    + "'customer': {"
                    + "'logged_in': true,"
                    + "'successful_purchases': 14,"
                    + "'created_at': 1379784950,"
                    + "'updated_at': 1379784950,"
                    + "'offline_payments': 4,"
                    + "'score': 9"
                    + "},"
                    + "'line_items': [{"
                    + "'name': 'Box of Cohiba S1s',"
                    + "'description': 'Imported From Mex.',"
                    + "'unit_price': 20000,"
                    + "'quantity': 1,"
                    + "'sku': 'cohb_s1',"
                    + "'category': 'food'"
                    + "}],"
                    + "'billing_address': {"
                    + "'street1':'77 Mystery Lane',"
                    + "'street2': 'Suite 124',"
                    + "'street3': null,"
                    + "'city': 'Darlington',"
                    + "'state':'NJ',"
                    + "'zip': '10192',"
                    + "'country': 'Mexico',"
                    + "'tax_id': 'xmn671212drx',"
                    + "'company_name':'X-Men Inc.',"
                    + "'phone': '77-777-7777',"
                    + "'email': 'purshasing@x-men.org'"
                    + "}"
                    + "}"
                    + "}");

            try {

           //     Charge conektaCharge = Charge.create(payment_params);

            }
            catch (Error e){
                System.out.println(e.toString());
            }

        } catch (Exception err) {
            //TODO: Handle error
            Log.d("Error: ", err.toString());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tarjeta", stringTarjeta);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onClick(View v) {
        if(v == guardar){
    //        if (verificaContenido()) {


                stringTarjeta =  numeroTarjeta.getText().toString().replace(" ", "").trim();///se agrego esta linea para quitar los espacios por que si no no funciona

                Card conektaCard = new Card("Jhon Doe Tester","4242424242424242","123","04","2017");

             //   Card card = new Card(nombre.getText().toString(), stringTarjeta, cvv.getText().toString(),
              //          mes.getText().toString(), anio.getText().toString());

                Token token = new Token(this);
                token.onCreateTokenListener(conekta.this);
                token.create(conektaCard);



      //      } else {
       //         Toast.makeText(this,"Faltan campos por llenar", Toast.LENGTH_SHORT).show();
        //    }
        }
    }
}

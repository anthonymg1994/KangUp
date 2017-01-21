package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.CCUtils;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.PaymentForm;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CarsXtype;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;

public class AddPaymentActivity extends AppCompatActivity {

    private Button add,avoid;
    private EditText card,mm,yy,cvv;
    private Spinner countries;
    private ImageButton camara;

    private int value=0;
    private String cvvPattern = "^([0-9]{3,4})$";
    private String numberPattern = "[0-9]*";

    private webServices webs = new webServices();
    private PaymentForm pay = new PaymentForm();
    CCUtils utils = new CCUtils();

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;

    public String colors[] = {"Tarjeta de debito","Tarjeta de credito"};
    Control control = new Control();

    private boolean flag = true;
    private SqliteController sql;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private DrawerActivity drw = new DrawerActivity();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        control.changeColorStatusBar(AddPaymentActivity.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAddPay);
        setSupportActionBar(toolbar);
        //getSupportActionBar().hide();
        //drw.setNameToolbar("Agregar metodos de pago");

        getSupportActionBar().setTitle("Agregar metodos de pago");

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        add  = (Button)findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(card.getText().toString().equals("") || mm.getText().toString().equals("") ||
                        yy.getText().toString().equals("") || cvv.getText().toString().equals(""))
                {
                    Toast msg = Toast.makeText(getBaseContext(),
                            "Faltan campos por llenar", Toast.LENGTH_SHORT);
                    msg.show();
                }
                else{
                    if(utils.getCardID(card.getText().toString()) > -1)
                    {
                        if(LuhnAlgoritm(card.getText().toString())){
                            if(card.getText().toString().matches(numberPattern)){
                                pay.setNum_cuenta(card.getText().toString());

                                if(Integer.valueOf(mm.getText().toString()) > 0 && Integer.valueOf(mm.getText().toString()) < 13){
                                    if(mm.getText().toString().matches(numberPattern))
                                    {
                                        pay.setMes_venc(mm.getText().toString());

                                        if(yy.getText().toString().matches(numberPattern))
                                        {
                                            pay.setAnio_venc(yy.getText().toString());

                                            if(cvv.getText().toString().matches(cvvPattern)){
                                                pay.setCvv(cvv.getText().toString());

                                                pay.setId_forma_pago(value);

                                                sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
                                                sql.Connect();
                                                User user = new User();
                                                user = sql.user();
                                                sql.Close();

                                                pay.setId_usuario(user.getId());

                                                webs.insertFormaPago(AddPaymentActivity.this,pay);
                                                Toast msg = Toast.makeText(getBaseContext(),
                                                        "Forma de pago guardada con exito!", Toast.LENGTH_SHORT);
                                                msg.show();
                                            }
                                            else
                                            {
                                                Toast msg = Toast.makeText(getBaseContext(),
                                                        "CVV invalido, intentelo de nuevo", Toast.LENGTH_SHORT);
                                                msg.show();
                                            }
                                        }
                                        else
                                        {
                                            Toast msg = Toast.makeText(getBaseContext(),
                                                    "Anio invalido, intentelo de nuevo", Toast.LENGTH_SHORT);
                                            msg.show();
                                        }
                                    }
                                    else {
                                        Toast msg = Toast.makeText(getBaseContext(),
                                                "Mes invalido, intentelo de nuevo", Toast.LENGTH_SHORT);
                                        msg.show();
                                    }
                                }
                                else{
                                    Toast msg = Toast.makeText(getBaseContext(),
                                            "Mes invalido, intentelo de nuevo", Toast.LENGTH_SHORT);
                                    msg.show();
                                }

                            }
                            else
                            {
                                Toast msg = Toast.makeText(getBaseContext(),
                                        "Tarjeta invalida, intentelo de nuevo", Toast.LENGTH_SHORT);
                                msg.show();
                            }
                        }
                        else
                        {
                            Toast msg = Toast.makeText(getBaseContext(),
                                    "Tarjeta invalida, intentelo de nuevo", Toast.LENGTH_SHORT);
                            msg.show();
                        }
                    }
                    else
                    {
                        Toast msg = Toast.makeText(getBaseContext(),
                                "Tarjeta invalida, intentelo de nuevo", Toast.LENGTH_SHORT);
                        msg.show();
                    }
                }
            }
        });
        avoid  = (Button)findViewById(R.id.avoid);
        avoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(AddPaymentActivity.this, PaymentActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        card = (EditText) findViewById(R.id.cardNumber);
        mm = (EditText) findViewById(R.id.month);
        yy = (EditText) findViewById(R.id.year);
        cvv = (EditText) findViewById(R.id.cvv);

        countries = (Spinner)findViewById(R.id.formas);
        countries.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        String text = countries.getSelectedItem().toString();
                                if(text.equals("Tarjeta de debito")){
                                    value = 2;
                                } else{
                                    value = 4;
                                }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, colors);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        countries.setAdapter(spinnerArrayAdapter);

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(AddPaymentActivity.this, CategoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(AddPaymentActivity.this, NewsActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(AddPaymentActivity.this, FavoriteActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(AddPaymentActivity.this, HistoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            startActivity(new Intent(AddPaymentActivity.this, PaymentActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean LuhnAlgoritm(String numero){

        int s1 = 0, s2 = 0;
        String reversa = new StringBuffer(numero).reverse().toString();
        for(int i = 0 ;i < reversa.length();i++){
            int digito = Character.digit(reversa.charAt(i), 10);
            if(i % 2 == 0){//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digito;
            }else{//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digito;
                if(digito >= 5){
                    s2 -= 9;
                }
            }
        }
        //System.out.println("La tarjeta es:");
        return (s1 + s2) % 10 == 0;

    }
}

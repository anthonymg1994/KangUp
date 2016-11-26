package com.mx.bridgestudio.kangup.Views;

import android.content.Intent;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.mx.bridgestudio.kangup.R;

public class AddPaymentActivity extends AppCompatActivity {

    private Button add,avoid;
    private EditText card,mm,yy,cvv;
    private Spinner countries;
    private ImageButton camara;

    public String colors[] = {"México","Estados Unidos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAddPay);
        setSupportActionBar(toolbar);
        //getSupportActionBar().hide();

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        add  = (Button)findViewById(R.id.addBtn);
        avoid  = (Button)findViewById(R.id.avoid);
        avoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(AddPaymentActivity.this, PaymentActivity.class));
            }
        });
        card = (EditText) findViewById(R.id.cardNumber);
        mm = (EditText) findViewById(R.id.month);
        yy = (EditText) findViewById(R.id.year);
        cvv = (EditText) findViewById(R.id.cvv);

        countries = (Spinner)findViewById(R.id.countries);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, colors);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        countries.setAdapter(spinnerArrayAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            startActivity(new Intent(AddPaymentActivity.this, PaymentActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

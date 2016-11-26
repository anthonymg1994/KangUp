package com.mx.bridgestudio.kangup.Views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import java.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.SqliteController;
import com.mx.bridgestudio.kangup.Models.Payment;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageView imageViewRound;
    private EditText name, lastname,mail,password;
    private Button next;
    private User u = new User();
    private SqliteController sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRegister);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //getSupportActionBar().set
        }

        name = (EditText)findViewById(R.id.name);
        lastname = (EditText)findViewById(R.id.lastname);
        mail = (EditText)findViewById(R.id.editEmail);
        password = (EditText)findViewById(R.id.password);

        imageViewRound=(ImageView)findViewById(R.id.imageProfile);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("") || lastname.getText().toString().equals("")
                        || mail.getText().toString().equals("") || password.getText().toString().equals("") )
                {
                    Toast msg = Toast.makeText(getBaseContext(),
                            "Faltan campos por llenar", Toast.LENGTH_SHORT);
                    msg.show();
                }
                else{
                    sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
                    sql.insertUsuario(name.getText().toString(),lastname.getText().toString(),"",mail.getText().toString(),"","",password.getText().toString(),1,"1");
                    Toast msg = Toast.makeText(getBaseContext(),
                            "Usuario registrado con éxito", Toast.LENGTH_SHORT);
                    msg.show();
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(RegisterActivity.this, AddPaymentActivity.class));
                }

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

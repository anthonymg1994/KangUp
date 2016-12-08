package com.mx.bridgestudio.kangup.Views.PaginasInicio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.R;

public class ForgotActivity extends AppCompatActivity {

    private Button recover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarForgot);
        setSupportActionBar(toolbar);
        //getSupportActionBar().hide();

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recover = (Button)findViewById(R.id.recover);
        recover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //finish();
                //startActivity(new Intent(ForgotActivity.this, FavoriteActivity.class));
                Toast msg = Toast.makeText(getBaseContext(),
                        "Se ha mandado un correo con tu contraseña", Toast.LENGTH_SHORT);
                msg.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            startActivity(new Intent(ForgotActivity.this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

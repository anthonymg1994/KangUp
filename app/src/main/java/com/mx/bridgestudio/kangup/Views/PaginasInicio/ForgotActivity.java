package com.mx.bridgestudio.kangup.Views.PaginasInicio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.AsyncTask.Usuario.AsynkTaskUser;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.DAO.DAOuser;
import com.mx.bridgestudio.kangup.R;

import org.json.JSONException;

public class ForgotActivity extends AppCompatActivity {

    private Button recover;
    private EditText email;
    private String texto;
    private AsynkTaskUser mAuthTask = null;
    public Boolean flag = false;
    private Control control = new Control();
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

        email = (EditText) findViewById(R.id.emailForgot);
        email.setHintTextColor(getResources().getColor(R.color.white));
        recover = (Button)findViewById(R.id.recover);
        recover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //finish();
                //startActivity(new Intent(ForgotActivity.this, FavoriteActivity.class));
                if(email.getText().toString().equals("")){
                    Toast msg = Toast.makeText(getBaseContext(),
                            "Ingresa tu correo electronico", Toast.LENGTH_SHORT);
                    msg.show();
                }else{
                //    if(control.isOnline()) {
                        Email task = new Email(email.getText().toString().trim());
                        task.execute();
                  //  }else{
                    //    Toast msg = Toast.makeText(getBaseContext(),
                        //        "Verifica tu conexion", Toast.LENGTH_SHORT);
                      //  msg.show();
                    //}


                   //  texto = "Se ha mandado una solicitud de cambio a tu email";
                }


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
    private class Email extends AsyncTask<String, Integer, Boolean> {

        ProgressDialog progressDialog = null;

        private final String mEmail;


        Email(String email) {
            mEmail = email;

        }

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ForgotActivity.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.dialog);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            // TODO: attempt authentication against a network service.


                // Simulate network access.
                DAOuser dao = new DAOuser();
                try {
                    flag = dao.RecuperarEmail(mEmail);
                    Thread.sleep(2000);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            return flag;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            String texto = "";
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (success) {
                texto = "Se envio un enlace a tu correo electronico";
                email.setText(" ");
            }else{
                texto ="No se pudo enviar enlace a tu correo verifica tu email";
            }
            Toast msg = Toast.makeText(getBaseContext(),
                    texto, Toast.LENGTH_SHORT);
            msg.show();

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

}

package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CatalogCar;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends DrawerActivity implements
        View.OnClickListener {

    private ImageView profile_photo;
    private EditText email,address,city,cellphone,name,ap_materno,ap_paterno,lada;
    private int mYear, mMonth, mDay;
    private TextView editBirth;
    protected DrawerLayout mDrawer;
    private SqliteController sql;
    private  Toolbar toolbar;
    private User user = new User();
    private FloatingActionButton edit;
    Control control = new Control();
    private webServices webs;

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;
    private DrawerActivity drw = new DrawerActivity();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        control.changeColorStatusBar(ProfileActivity.this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //setContentView(R.layout.activity_profile);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_profile, null, false);
        mDrawer.addView(contentView, 0);

        //Obtengo informacion obetenida de sqlite tabla usuario
        sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
        sql.Connect();
        user = sql.user();
        sql.Close();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setTitle(user.getFirstName()+ " " +user.getAp_paterno());

//Arreglar cuando selccione edittext solo salg dialg y se esconda teclado
        profile_photo = (ImageView) findViewById(R.id.imageProfilee);
        name = (EditText) findViewById(R.id.editText2);
        ap_paterno = (EditText) findViewById(R.id.editText);
        ap_materno = (EditText) findViewById(R.id.editText3);
        editBirth = (TextView)findViewById(R.id.editBirth);
        cellphone = (EditText) findViewById(R.id.editName);
        email = (EditText)findViewById(R.id.editEmail);
        address = (EditText)findViewById(R.id.editAddress);
        city = (EditText)findViewById(R.id.editCity);
        lada = (EditText)findViewById(R.id.lada);
        edit = (FloatingActionButton) findViewById(R.id.editinfo);


        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                user.setFirstName(name.getText().toString());
                user.setAp_paterno(ap_paterno.getText().toString());
                user.setAp_materno(ap_materno.getText().toString());
                user.setFnacimiento(editBirth.getText().toString());
                user.setCellphone(cellphone.getText().toString());
                user.setEmail(email.getText().toString());
                user.setCiudad(city.getText().toString());
                user.setAddress(address.getText().toString());
// actualizar foto en imageview

                sql = new SqliteController(ProfileActivity.this, "kangup",null, 1);
                sql.Connect();
                sql.updateProfile(user);

                Snackbar snackbar = Snackbar.make(v, "Usuario actualizado", Snackbar.LENGTH_SHORT);
                snackbar.show();
                User user = sql.user();
                //Actualizar informacion de usuario
                webs.updateUser(ProfileActivity.this,user);
                sql.Close();
            }
        });
        editBirth.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.SHOW_FORCED);
                }
            }
        });


        sql = new SqliteController(ProfileActivity.this, "kangup",null, 1);
        sql.Connect();
        getInformationUser(user = sql.user());
        sql.Close();

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ProfileActivity.this, CategoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ProfileActivity.this, NewsActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ProfileActivity.this, FavoriteActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ProfileActivity.this, HistoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    private void showDialog(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Date parseDate = null;

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        try {
                                  parseDate = dateFormat.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd de MMM de yy");
                       String finalString = dateFormat1.format(parseDate);


                        editBirth.setText(""+finalString);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private void getInformationUser(User user){
        //AGREGO Nombre de usuario a toolbar
        toolbar.setTitle(user.getFirstName() + " " + user.getAp_paterno());
        setSupportActionBar(toolbar);

        //Verifico que no este vacio y si lo esta pongo foto predeterminada
        if(!user.getPhoto().equals("vacio")){
            Picasso.with(ProfileActivity.this).load(user.getPhoto()).into(profile_photo);

        }else {
            profile_photo.setImageResource(R.drawable.perfil1);
        }

        //Validao si el campo esta null no muestro nada en los edittext
        if(!user.getFirstName().equals("null"))
            name.setText(user.getFirstName());

        if(!user.getAp_paterno().equals("null"))
            ap_paterno.setText(user.getAp_paterno());

        if(!user.getAp_materno().equals("null"))
            ap_materno.setText(user.getAp_materno());

        if(!user.getFnacimiento().equals("null"))
            editBirth.setText(user.getFnacimiento());
        if(!user.getFnacimiento().equals("null"))
             editBirth.setText(user.getFnacimiento());

        if(!user.getEmail().equals("null"))
             email.setText(user.getEmail());
        //Agregar a bd un campo de domicilio
        //Cambiar ciudad por domicilio get
        if(!user.getCiudad().equals("null"))
            address.setText(user.getCiudad());

        if(!user.getCiudad().equals("null"))
            city.setText(user.getCiudad());

        if(!user.getCellphone().equals("null"))
            cellphone.setText(user.getCellphone());
        //Agregar getPhotoByUser
        lada.setText("+52 ");
    }
    @Override
    public void onClick(View v) {
        if(R.id.editBirth == v.getId()){
            showDialog();
        }
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this,CatalogCar.class);
        startActivity(setIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

}

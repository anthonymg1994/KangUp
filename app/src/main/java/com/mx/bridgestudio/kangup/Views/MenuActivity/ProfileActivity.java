package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.ScriptGroup;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Controllers.Tools;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;

public class ProfileActivity extends DrawerActivity implements
        View.OnClickListener {

    private ImageButton showCalendar;
    private EditText email,address,city,cellphone;
    private int mYear, mMonth, mDay;
    private TextView editBirth;
    private int flag=0;
    protected DrawerLayout mDrawer;
    private SqliteController sql;
    private  Toolbar toolbar;
    private User user = new User();
    Tools tol;

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;
    private DrawerActivity drw = new DrawerActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_profile);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_profile, null, false);
        mDrawer.addView(contentView, 0);

        sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
        sql.Connect();
        user = sql.user();
        sql.Close();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(user.getFirstName()+ " " +user.getLastName());
        //drw.setNameToolbar(user.getFirstName()+ " " +user.getLastName());

        //   showCalendar = (ImageButton) findViewById(R.id.showCalendar);
      //  showCalendar.setOnClickListener(this);
//Arreglar cuando selccione edittext solo salg dialg y se esconda teclado
        editBirth = (TextView)findViewById(R.id.editBirth);
        cellphone = (EditText) findViewById(R.id.editText);
        email = (EditText)findViewById(R.id.editEmail);
        address = (EditText)findViewById(R.id.editAddress);
        city = (EditText)findViewById(R.id.editCity);
     //   cellphone = (EditText)findViewById(R.id.ed);


        editBirth.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //tol = new Tools();
                    //tol.hideKeyBoard(v,ProfileActivity.this);
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.SHOW_FORCED);
                }
            }
        });



        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                if(flag ==1)
                {
                    fab.setImageResource(R.drawable.ic_menu_send);
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    flag=0;
                }
                else {

                }

            }
        });




        sql = new SqliteController(ProfileActivity.this, "kangup",null, 1);
        sql.Connect();
        //user = sql.user();
        getInformationUser(user = sql.user());
        sql.Close();

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ProfileActivity.this, CategoryActivity.class));
            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ProfileActivity.this, NewsActivity.class));
            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ProfileActivity.this, FavoriteActivity.class));
            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ProfileActivity.this, HistoryActivity.class));
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
        toolbar.setTitle(user.getFirstName() + " " + user.getLastName());
        setSupportActionBar(toolbar);

        //Validar si el campo esta null no muestro nada en los edittext
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

    }
    @Override
    public void onClick(View v) {
        if(R.id.editBirth == v.getId()){
            showDialog();
        }
        /*
        if(v.getId() == R.id.showCalendar)
        {
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

                            editBirth.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        */
    }

}

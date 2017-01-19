package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.CardAdapter;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.GooglePlaces.PlacesAutoCompleteActivity;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.PaymentActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.RegisterActivity;
import com.mx.bridgestudio.kangup.Views.tabs.TabRecommend;
import com.mx.bridgestudio.kangup.Views.tabs.TabVotados;
import com.mx.bridgestudio.kangup.Views.tabs.TabTop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by USUARIO on 24/10/2016.
 */

public class CarsXtype extends DrawerActivity implements
        AdapterView.OnItemClickListener,MaterialTabListener, View.OnClickListener {

    MaterialTabHost tabHost;
    ViewPager viewPager;
    ViewPagerAdapterTab androidAdapter;
    private int mYear, mMonth, mDay,mHour, mMinute,pm;
    private Control control = new Control();
    private ImageButton time,date;
    private TextView hora,fecha;
    protected DrawerLayout mDrawer;

    private SqliteController sql;
    private DrawerActivity drw = new DrawerActivity();

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;

    // private List items = new ArrayList();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.content_types);
        control.changeColorStatusBar(CarsXtype.this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.typeofcar, null, false);
        mDrawer.addView(contentView, 0);

        //drw.setNameToolbar(""+CardAdapter.marca);
        getSupportActionBar().setTitle(""+CardAdapter.marca);

        //tab host
        tabHost = (MaterialTabHost)findViewById(R.id.tabHost);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        time = (ImageButton) findViewById(R.id.changeHour);
        time.setOnClickListener(this);

        date = (ImageButton) findViewById(R.id.changeDate);
        date.setOnClickListener(this);

        hora = (TextView) findViewById(R.id.hour);
        fecha = (TextView) findViewById(R.id.date);
        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setColorFilter(ContextCompat.getColor(CarsXtype.this,R.color.colorAccent));
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  if (control.isOnline()) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(CarsXtype.this, CategoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //} else {
                //Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();
                //}
            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   if (control.isOnline()) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(CarsXtype.this, NewsActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                // } else {
                //Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  if (control.isOnline()) {
                if(LoginActivity.guestFlag == 1){
                    alertGuest();
                }
                else
                {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(CarsXtype.this, FavoriteActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    //} else {
                    //Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();
                }
                //}

            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //   if (control.isOnline()) {
                if(LoginActivity.guestFlag == 1){
                    alertGuest();
                }
                else{
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(CarsXtype.this, HistoryActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                // } else {
                //                   Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();
//
                //              }

            }
        });


        //adapter view
        androidAdapter = new ViewPagerAdapterTab(getSupportFragmentManager(), CarsXtype.this);
        viewPager.setAdapter(androidAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int tabposition) {
                tabHost.setSelectedNavigationItem(tabposition);
                //androidAdapter.getItem(tabposition);
            }
        });

        //for tab position
        for (int i = 0; i < androidAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(androidAdapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,CatalogCar.class);
        startActivity(setIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_drawer_drawer, menu);
        return true;
    }


    @Override
    public void onTabSelected(MaterialTab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        //androidAdapter.getItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.changeDate){
            if(LoginActivity.guestFlag == 1)
            {
                alertGuest();
            }
            else{
                showDialogCalendar();
            }
        }
        if(v.getId() == R.id.changeHour){
            if(LoginActivity.guestFlag == 1)
            {
                alertGuest();
            }
            else{
                showDialogTime();
            }
        }
    }
    /*
    @Override
    public void sendData(Vehicle[] vehicle) {
        Toast.makeText(this, "string"+response, Toast.LENGTH_SHORT).show();
        for(int i = 0; i < vehicle.lenght(); i ++){
            items.add(i,vehicle[i]);
        }
         adapter.notifyDataSetChanged();
    }
    */
    private void showDialogCalendar(){
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
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("EEE, d MMM yyyy");
                        String finalString = dateFormat1.format(parseDate);
                        //sql = new SqliteController(getApplicationContext(),"kangup",null,1);

                        //sql.updateReservacionFecha(finalString);

                        fecha.setText(""+finalString);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }
    private void showDialogTime(){

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        pm = c.get(Calendar.AM_PM);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        //hora.setText(hourOfDay + ":" + minute + " " + pm);
                        //sql = new SqliteController(getApplicationContext(),"kangup",null,1);
                        //sql.updateReservacionHora(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);

        timePickerDialog.show();
    }

    // view pager adapter
    private class ViewPagerAdapterTab extends FragmentStatePagerAdapter {
        Context context;
        Fragment fragment = null;
        public ViewPagerAdapterTab(FragmentManager fragmentManager,Context c) {
            super(fragmentManager);
            this.context = c;
        }

        public Fragment getItem(int num) {
            switch (num){
                case 0: fragment = new TabTop();

                    break;
                case 1: fragment = new TabRecommend();
                    break;
                case 2: fragment = new TabTop();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int tabposition) {
            CharSequence tab;
            switch (tabposition)
            {
                case 0: tab = "Top";
                    break;
                case 1: tab = "Recomendados";
                    break;
                case 2: tab="Más votados";
                    break;
                default: tab ="";
            }
            return tab;
        }

    }

    protected int getLayoutId() {
        return R.layout.activity_drawer;
    }

    public void alertGuest() {
        new AlertDialog.Builder(CarsXtype.this)
                .setTitle("Invitado")
                .setMessage("Gracias por visitar la aplicación de KangUp!! Para realizar la siguiente acción necesita estar registrado.")
                .setIcon(R.drawable.perfil_icon)
                .setPositiveButton("Registrar",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Intent setIntent = new Intent(CarsXtype.this, RegisterActivity.class);
                                startActivity(setIntent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
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

package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mx.bridgestudio.kangup.Adapters.SlidingImage_Adapter;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendDetail;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DetalleActivity extends DrawerActivity implements OnDataSendDetail{


    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    //Aqui se cargara las imagenes del servidor
    private static final Integer[] IMAGES= {R.drawable.auto,R.drawable.auto,R.drawable.auto,R.drawable.auto};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private webServices webs = new webServices();
    private Vehicle vehicle = new Vehicle();
    private int id_vehiculo = 0;
    private String nombre_vehiculo = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarr);
        toolbar.setTitle("Detalle de auto");
        setSupportActionBar(toolbar);

        id_vehiculo = CarsXtype.id_vehiculo;
        nombre_vehiculo = CarsXtype.nombre_vehiculo;


        //webs.DetalleAuto(Vehicle vehicle);


        init();
    }
    /*
    @Override
    public void sendData(Vehicle vehicle) {
        Toast.makeText(this, "string"+response, Toast.LENGTH_SHORT).show();
        vehicle = vehicle;
    }
    */
    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(DetalleActivity.this,ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,CarsXtype.class);
        startActivity(setIntent);
        finish();
    }

    @Override
    public void sendData(Vehicle obj) {

    }
}
package com.mx.bridgestudio.kangup.Views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdapterCategory;
import com.mx.bridgestudio.kangup.Adapters.AdapterFavoriteList;
import com.mx.bridgestudio.kangup.Adapters.AndroidImageAdapter;
import com.mx.bridgestudio.kangup.Adapters.CardAdapter;
import com.mx.bridgestudio.kangup.AsyncTask.MarcaModelo.AsyncBrands;
import com.mx.bridgestudio.kangup.Controllers.BaseActivity;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendToActivity;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.MainActivity;
import com.mx.bridgestudio.kangup.Models.Brand;
import com.mx.bridgestudio.kangup.Models.Category;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;

public class CategoryActivity extends MainActivity implements AdapterView.OnItemClickListener,NavigationView.OnNavigationItemSelectedListener{

    private ListView list;
    private ArrayList<Category> tipos = new ArrayList<>();
    private AdapterCategory adaptador;
    private webServices webs = new webServices();
    private Brand brand = new Brand();
    public static int opcionSeleccionada;
    AsyncBrands asyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        AndroidImageAdapter adapterView = new AndroidImageAdapter(this);
        mViewPager.setAdapter(adapterView);
        

        list = (ListView)findViewById(R.id.listCategory);
        adaptador = new AdapterCategory(this,tipos);
        list.setAdapter(adaptador);
        //list.setOnClickListener(this);
        list.setOnItemClickListener(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                 opcionSeleccionada = ((Category) arg0.getAdapter().getItem(position)).getId();
                brand.setId_categoria(opcionSeleccionada);
                Intent intent = new Intent(CategoryActivity.this, CatalogCar.class);
                //SendToActivity.sendData(arrayBrands);
                intent.putExtra("option",brand.getId_categoria());
                startActivity(intent);
//                webs.brandByCategory(CategoryActivity.this,CategoryActivity.this,brand);
            }
        });

        fillList();

    }

    public void fillList(){
        Category[] list = new Category[4];

        list[0] = new Category();
        list[0].setId(1);
        list[0].setName("Limusina");

        list[1] = new Category();
        list[1].setId(2);
        list[1].setName("Lujo");

        list[2] = new Category();
        list[2].setId(3);
        list[2].setName("Clasicos");

        list[3] = new Category();
        list[3].setId(4);
        list[3].setName("Suvs");

        for(int x=0; x<list.length;x++)
        {
            tipos.add(x,list[x]);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent setIntent = new Intent(this,CatalogCar.class);
        startActivity(setIntent);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,LoginActivity.class);
        startActivity(setIntent);
        finish();
    }

/*
    @Override
    public void sendData(Brand[] obj) {
        {
            Toast.makeText(this, "paso"+obj.length, Toast.LENGTH_SHORT).show();
        }
    }
    */



}

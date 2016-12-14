package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Adapters.AdapterFavoriteList;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;

import java.util.ArrayList;

/**
 * Created by USUARIO on 14/12/2016.
 */

public class Reservacion extends DrawerActivity implements View.OnClickListener {
    private ListView listFav;
    private ArrayList<ListCar> tipos = new ArrayList<>();
    private ArrayAdapter<ListCar> AdapterArray;
    private AdapterFavoriteList adaptador;
    protected DrawerLayout mDrawer;
    private webServices webs = new webServices();
    private SqliteController sql;
    private User user = new User();


    private Button reservar,bAdd;
    private ImageButton bDate,bTime;
    private ListView listPaquetes,listRutas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_favorite);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_reservacion, null, false);
        mDrawer.addView(contentView, 0);


        reservar = (Button) findViewById(R.id.creservar);
        reservar.setOnClickListener(this);

        bDate = (ImageButton) findViewById(R.id.imageButtonCalendar);
        bDate.setOnClickListener(this);

        bTime = (ImageButton) findViewById(R.id.imageButtonTime);
        bTime.setOnClickListener(this);

        bAdd = (Button) findViewById(R.id.addruta);
        bAdd.setOnClickListener(this);

        listPaquetes = (ListView) findViewById(R.id.listViewPaquetes);
        listRutas = (ListView) findViewById(R.id.listViewRuta);




       // webs.favsByUser(Reservacion.this,Reservacion.this,user);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.creservar){
            //confirmar reservacion
        }
        if(v.getId() == R.id.imageButtonCalendar){
            // seleccionar fecha de viaje
        }
        if(v.getId() == R.id.imageButtonTime){
            //seleccionar hota de viaje
        }
        if(v.getId() == R.id.addruta){
            //Agregar origenes y destinos
        }
    }
}

package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mx.bridgestudio.kangup.Adapters.AdapterFavoriteList;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFavorites;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.Lists.ListViaje;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;

import java.util.ArrayList;

public class FavoriteActivity extends DrawerActivity implements AdapterView.OnItemClickListener,OnDataSendFavorites {

    private ListView listFav;
    private ArrayList<ListCar> tipos = new ArrayList<>();
    private ArrayAdapter<ListCar> AdapterArray;
    private AdapterFavoriteList adaptador;
    protected DrawerLayout mDrawer;
    private webServices webs = new webServices();
    private SqliteController sql;
    private User user = new User();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_favorite);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_favorite, null, false);
        mDrawer.addView(contentView, 0);

        listFav = (ListView)findViewById(R.id.listFav);
        adaptador = new AdapterFavoriteList(this,tipos);
        listFav.setAdapter(adaptador);

        sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
        sql.Connect();
        user = sql.user();
        sql.Close();


        webs.favsByUser(FavoriteActivity.this,FavoriteActivity.this,user);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void fillList(Vehicle[] vehicles){
        ListCar[] list = new ListCar[vehicles.length];
        for(int i = 0 ; i < vehicles.length ; i++){
            list[i] = new ListCar();
            list[i].setId(vehicles[i].getId());
            list[i].setMarca(vehicles[i].getMarca());
            list[i].setModelo(vehicles[i].getModel());
            list[i].setAnio(vehicles[i].getYear());
            // list[i].setFecha(listCar[i].getFecha());
            //list[i].setTotal(listCar[i].getTotal());
            //Cmbiar por imagen del servidor
            list[i].setImage(1);

            tipos.add(i,list[i]);
        }
        adaptador.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,LoginActivity.class);
        startActivity(setIntent);
        finish();
    }

    @Override
    public void sendDataFavorites(Vehicle[] obj) {
        fillList(obj);
    }
}

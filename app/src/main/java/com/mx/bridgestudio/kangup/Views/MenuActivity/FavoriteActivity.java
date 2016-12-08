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
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;

import java.util.ArrayList;

public class FavoriteActivity extends DrawerActivity implements AdapterView.OnItemClickListener {

    private ListView listFav;
    private ArrayList<ListCar> tipos = new ArrayList<>();
    private ArrayAdapter<ListCar> AdapterArray;
    private AdapterFavoriteList adaptador;
    protected DrawerLayout mDrawer;

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
        fillList();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void fillList(){
        ListCar list = new ListCar();
        //list.setName( "Carro");

        for(int x=0;x<3;x++){
            tipos.add(x,list);
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,LoginActivity.class);
        startActivity(setIntent);
        finish();
    }
}

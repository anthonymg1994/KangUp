package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdapterViaje;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendHistory;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.Lists.ListViaje;
import com.mx.bridgestudio.kangup.Models.RoadTrip;
import com.mx.bridgestudio.kangup.Models.SampleDivider;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Controllers.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by USUARIO on 07/12/2016.
 */

public class HistoryActivity extends DrawerActivity implements AdapterView.OnItemClickListener,OnDataSendHistory {

    private webServices webs = new webServices();
    protected DrawerLayout mDrawer;
    private String opcionSeleccionada="";
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    ArrayList<ListViaje> items= new ArrayList<>();
    private SqliteController sql;
    private User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_favorite);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_history, null, false);
        mDrawer.addView(contentView, 0);


        recycler = (RecyclerView) findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);



        sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
        sql.Connect();
        user = sql.userById();
        sql.Close();
        webs.historyByUser(HistoryActivity.this,HistoryActivity.this,user);




        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);
        recycler.addItemDecoration(itemDecoration);
        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(view.getContext(), "position = " +items.get(position).getModelo(), Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        // Crear un nuevo adaptador
        adapter = new AdapterViaje(items);
        recycler.setAdapter(adapter);




    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void fillList(RoadTrip[] RoadTrip){
        ListViaje[] list = new ListViaje[RoadTrip.length];
        for(int i = 0 ; i < RoadTrip.length ; i++){
            list[i] = new ListViaje();
            list[i].setId(RoadTrip[i].getId());
            list[i].setMarca(RoadTrip[i].getMarca());
            list[i].setModelo(RoadTrip[i].getModelo());
            list[i].setYear(RoadTrip[i].getYear());
            list[i].setFecha(RoadTrip[i].getFecha());
            list[i].setTotal(RoadTrip[i].getTotal());
            //Cmbiar por imagen del servidor
            list[i].setImage(1);
            items.add(i,list[i]);
        }
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,CategoryActivity.class);
        startActivity(setIntent);
        finish();
    }



    @Override
    public void sendDataHistory(RoadTrip[] obj) {
        Toast.makeText(this, "Marcas"+obj.length, Toast.LENGTH_SHORT).show();
        fillList(obj);
    }
}


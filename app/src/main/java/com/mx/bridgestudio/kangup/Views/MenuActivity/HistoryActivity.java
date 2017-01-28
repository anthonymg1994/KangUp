package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdapterViaje;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendHistory;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendHistoryDetail;
import com.mx.bridgestudio.kangup.Controllers.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.DetalleViaje;
import com.mx.bridgestudio.kangup.Models.DividerItemDecoration;
import com.mx.bridgestudio.kangup.Models.Lists.ListViaje;
import com.mx.bridgestudio.kangup.Models.RoadTrip;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;

import java.util.ArrayList;

/**
 * Created by USUARIO on 07/12/2016.
 */

public class HistoryActivity extends DrawerActivity implements AdapterView.OnItemClickListener,OnDataSendHistoryDetail,OnDataSendHistory{

    private webServices webs = new webServices();
    protected DrawerLayout mDrawer;
    private String opcionSeleccionada="";
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    ArrayList<ListViaje> items= new ArrayList<>();
    private SqliteController sql;
    private User user = new User();
    Control control = new Control();

    public static int id_viaje=0;

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;

    private DrawerActivity drw = new DrawerActivity();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_favorite);
        control.changeColorStatusBar(HistoryActivity.this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_history, null, false);
        mDrawer.addView(contentView, 0);

        //drw.setNameToolbar("Historial");


        recycler = (RecyclerView) findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);


        getSupportActionBar().setTitle("Historial de viajes");


        sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
        sql.Connect();
        user = sql.user();
        sql.Close();

        //if(control.isOnline()){
            webs.historyByUser(HistoryActivity.this,HistoryActivity.this,user);
        //}else{
          //  Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

        //}

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);

        recycler.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(this ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(view.getContext(), "position = " +items.get(position).getModelo(), Toast.LENGTH_SHORT).show();
                        int opcionSeleccionada = items.get(position).getId();
                        id_viaje = opcionSeleccionada;

                        webs.getHistoryDetailByUser(HistoryActivity.this,HistoryActivity.this,user.getId(),id_viaje,user.getId());
                        //Intent intent = new Intent(HistoryActivity.this, HistoryDetailsActivity.class);
                        //startActivity(intent);
                    }


                })

        );
        // Crear un nuevo adaptador
        adapter = new AdapterViaje(items);
        recycler.setAdapter(adapter);

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent));
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         //       if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(HistoryActivity.this, CategoryActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
           //     } else {
             //       Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

               // }

            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(HistoryActivity.this, NewsActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //} else {
                  //  Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(HistoryActivity.this, FavoriteActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //} else {
                  //  Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if (control.isOnline()) {

                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(HistoryActivity.this, HistoryActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //} else {
                 //   Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });

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
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }



    @Override
    public void sendDataHistory(RoadTrip[] obj) {
      //  Toast.makeText(this, "Marcas"+obj.length, Toast.LENGTH_SHORT).show();
        fillList(obj);
    }

    @Override
    public void sendDataHistoryDetail(DetalleViaje[] obj) {

    }
}


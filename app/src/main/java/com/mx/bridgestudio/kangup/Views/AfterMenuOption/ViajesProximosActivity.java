package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mx.bridgestudio.kangup.Adapters.AdapterArticle;
import com.mx.bridgestudio.kangup.Adapters.AdapterReservacion;
import com.mx.bridgestudio.kangup.Adapters.AdapterRoutes;
import com.mx.bridgestudio.kangup.Adapters.RouteTouchHelper;
import com.mx.bridgestudio.kangup.AsyncTask.Viaje.RoutesByTrip;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendRoutesByTrip;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendViajesProximos;
import com.mx.bridgestudio.kangup.Controllers.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.*;
import com.mx.bridgestudio.kangup.Models.Lists.ListArticles;
import com.mx.bridgestudio.kangup.Models.Lists.ListReservacion;
import com.mx.bridgestudio.kangup.Models.Lists.ListRoutes;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Models.Reservacion;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.PaymentActivity;

import java.util.ArrayList;

public class ViajesProximosActivity extends AppCompatActivity implements OnDataSendViajesProximos, OnDataSendRoutesByTrip{

    private RecyclerView listViajes;
    private RecyclerView.LayoutManager lManagerViajes;
    public AdapterReservacion adapter;
    public ArrayList<ListReservacion> items = new ArrayList<>();
    private webServices webs = new webServices();
    private SqliteController sql;
    private ListReservacion res = new ListReservacion();

    private TextView emptyView;
    private AlertDialog alert;
    private Rutas rutas = new Rutas();
    AdapterRoutes adapterRoutes;
    ArrayList<ListRoutes> list = new ArrayList<>();


    //toolbardown
    private ImageButton catalogo, favoritos, historial,noticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes_proximos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarViajes);
        setSupportActionBar(toolbar);

        emptyView = (TextView)findViewById(R.id.empty_view_viajes);

        RoutesByTrip.flagg =0;

        sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
        sql.Connect();
        User user = sql.user();
        sql.Close();

        webs.getAllReservationsByUser(this,this,user.getId());

        //RecyclerView
        //Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);

        listViajes = (RecyclerView) findViewById(R.id.ViajesProxRecyclerView);

        //listRutas.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        listViajes.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManagerViajes = new LinearLayoutManager(this);
        listViajes.setLayoutManager(lManagerViajes);


        final RecyclerView.ItemDecoration itemDecorations = new SampleDivider(this);
        listViajes.addItemDecoration(itemDecorations);



        adapter = new AdapterReservacion(items);
        listViajes.setAdapter(adapter);

        listViajes.addOnItemTouchListener(
                new RecyclerItemClickListener(this ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        res = adapter.getItem(position);
                        rutas.setId_reservacion(res.getId());
                        dialogOpciones();
                        Toast msg = Toast.makeText(getBaseContext(),
                                String.valueOf(res.getId()), Toast.LENGTH_LONG);
                        msg.show();
                    }


                })
        );

        catalogo = (ImageButton) findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ViajesProximosActivity.this, CategoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        noticias = (ImageButton) findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ViajesProximosActivity.this, NewsActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        favoritos = (ImageButton) findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ViajesProximosActivity.this, FavoriteActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        historial = (ImageButton) findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ViajesProximosActivity.this, HistoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


    }

    public void fillList(Reservacion[] res){
        ListReservacion[] list = new ListReservacion[res.length];
        for(int i = 0 ; i < res.length ; i++){
            list[i] = new ListReservacion();
            list[i].setId(res[i].getId());
            list[i].setAuto(res[i].getAuto());
            list[i].setModelo(res[i].getModelo());
            list[i].setYear(res[i].getYear());
            list[i].setDate(res[i].getDate());
            list[i].setHourI(res[i].getHourI());
            list[i].setHourF(res[i].getHourF());
            list[i].setTotal(res[i].getTotal());
            //list[i].setImage(news[i].getImagen());
            //Cmbiar por imagen del servidor
            items.add(i,list[i]);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this, CategoryActivity.class);
        finish();
        startActivity(setIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            startActivity(new Intent(this, CategoryActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendDataViajesProximos(Reservacion[] obj) {
        fillList(obj);
    }

    public void alertCancelarReservacion() {
        new AlertDialog.Builder(this)
                .setTitle("Cancelar reservación")
                .setMessage("¿Desea cancelar su reservación seleccionada?")
                .setIcon(R.drawable.perfil_icon)
                .setPositiveButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Ion.with(ViajesProximosActivity.this)
                                        .load("POST", "http://kangup.com.mx/index.php/cancelarViaje")
                                        .setBodyParameter("id", String.valueOf(res.getId()))
                                        .setBodyParameter("cancelado","1")
                                        .asString()
                                        .setCallback(new FutureCallback<String>() {
                                            @Override
                                            public void onCompleted(Exception e, String result) {
                                                //String info="";
                                                Toast msg = Toast.makeText(getBaseContext(),
                                                        result, Toast.LENGTH_LONG);
                                                msg.show();
                                                finish();
                                                startActivity(new Intent(ViajesProximosActivity.this, ViajesProximosActivity.class));
                                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                            }
                                        });
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void dialogOpciones(){

        CharSequence[] values2 = {"Ver detalles","Cancelar reservación"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ViajesProximosActivity.this);

        builder.setTitle("Selecciona: ");

        builder.setSingleChoiceItems(values2, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        alertRutas(rutas);
                        break;
                    case 1:
                        alertCancelarReservacion();
                        break;
                }
                alert.dismiss();
            }
        });
        alert = builder.create();
        alert.show();

    }

    public void alertRutas(Rutas r) {

    /*
     * Inflate the XML view. activity_main is in
     * res/layout/form_elements.xml
     */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.dialog_rutas,
                null, false);

        webs.getRoutesByTrip(ViajesProximosActivity.this,this,r);

        final RecyclerView recycler;
        final RecyclerView.LayoutManager lManager;

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        recycler = (RecyclerView) formElementsView.findViewById(R.id.routesRutas);
        recycler.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);
        recycler.addItemDecoration(itemDecoration);
        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(this ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                    }


                })
        );


        // Crear un nuevo adaptador
        adapterRoutes = new AdapterRoutes(list);
        recycler.setAdapter(adapterRoutes);

        // the alert dialog
        new AlertDialog.Builder(ViajesProximosActivity.this,R.style.MyDialogTheme).setView(formElementsView)
                .setTitle("Rutas")
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        list.clear();
                        dialog.dismiss();
                    }

                }).show();
    }

    public void fillListRoutes(Rutas[] r){
        ListRoutes[] lis = new ListRoutes[r.length];
        for(int i = 0 ; i < r.length ; i++){
            lis[i] = new ListRoutes();
            lis[i].setId(r[i].getId());
            lis[i].setOrigen(r[i].getOrigen());
            lis[i].setDestiny(r[i].getDestino());
            list.add(i,lis[i]);
        }
        adapterRoutes.notifyDataSetChanged();

    }

    @Override
    public void sendDataRoutesByTrip(Rutas[] obj) {
        fillListRoutes(obj);
    }
}

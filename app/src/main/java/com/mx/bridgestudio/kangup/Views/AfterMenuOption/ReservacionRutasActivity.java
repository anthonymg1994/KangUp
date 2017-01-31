package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdapterRoutes;
import com.mx.bridgestudio.kangup.Adapters.RouteTouchHelper;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendRutas;
import com.mx.bridgestudio.kangup.Controllers.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.DividerItemDecoration;
import com.mx.bridgestudio.kangup.Models.Lists.ListRoutes;
import com.mx.bridgestudio.kangup.Models.SampleDivider;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.GooglePlaces.PlacesAutoCompleteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReservacionRutasActivity extends AppCompatActivity {

    private static final String LOG_TAG = "kangup";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyCjWw7BILSM1YnBwf48nU-RglJP1fmOKUE";

    private FloatingActionButton addRoute;
    private RecyclerView listRutas;
    private RecyclerView.LayoutManager lManagerRoutes;
    public  RecyclerView.Adapter adapterRoutes;
    public  ArrayList<ListRoutes> itemsRoute = new ArrayList<>();
    public static int itemsSize=0;
    private TextView emptyView;

    private ListRoutes[] rut;

    private SqliteController sql;
    OnDataSendRutas rutas;


    //toolbardown
    private ImageButton catalogo, favoritos, historial,noticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion_rutas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarRutas);
        setSupportActionBar(toolbar);

        emptyView = (TextView)findViewById(R.id.empty_view);

        addRoute = (FloatingActionButton)findViewById(R.id.RouteAdd);
        addRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent setIntent = new Intent(ReservacionRutasActivity.this, PlacesAutoCompleteActivity.class);
              //  setIntent.putExtra("option", (Parcelable) ReservacionRutasActivity.this);
                //  setIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(setIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        //RecyclerView
        //Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);

        listRutas = (RecyclerView) findViewById(R.id.rutasRecyclerView);

        //listRutas.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        listRutas.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManagerRoutes = new LinearLayoutManager(this);
        listRutas.setLayoutManager(lManagerRoutes);


        final RecyclerView.ItemDecoration itemDecorations = new SampleDivider(this);
        listRutas.addItemDecoration(itemDecorations);
        listRutas.addOnItemTouchListener(
                new RecyclerItemClickListener(this ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                    }


                })
        );



        // Crear un nuevo adaptador

        sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
        sql.Connect();
        rut = sql.getRutas();
        sql.Close();

        adapterRoutes = new AdapterRoutes(itemsRoute);
        listRutas.setAdapter(adapterRoutes);
        itemsRoute = fillRoutes(rut,itemsRoute);


        if(itemsRoute.isEmpty()){
            listRutas.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else{
            listRutas.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }


        ItemTouchHelper.Callback callback = new RouteTouchHelper((AdapterRoutes) adapterRoutes);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(listRutas);

        catalogo = (ImageButton) findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ReservacionRutasActivity.this, CategoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        noticias = (ImageButton) findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ReservacionRutasActivity.this, NewsActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        favoritos = (ImageButton) findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ReservacionRutasActivity.this, FavoriteActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        historial = (ImageButton) findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ReservacionRutasActivity.this, HistoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


    }

    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,Reservacion.class);
        finish();
        startActivity(setIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            startActivity(new Intent(ReservacionRutasActivity.this, Reservacion.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        return super.onOptionsItemSelected(item);
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.reservacion_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText origen = (EditText) dialogView.findViewById(R.id.origentxt);
        origen.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Intent setIntent = new Intent(ReservacionRutasActivity.this, PlacesAutoCompleteActivity.class);
                setIntent.putExtra("option",1);
                //  setIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(setIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        final EditText destino = (EditText) dialogView.findViewById(R.id.destinotxt);
        destino.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Intent setIntent = new Intent(ReservacionRutasActivity.this, PlacesAutoCompleteActivity.class);
                setIntent.putExtra("option",2);
                setIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(setIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });


        dialogBuilder.setTitle("Nueva ruta");
        dialogBuilder.setMessage("Introduzca su nueva ruta:");
        dialogBuilder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //String ruta = "Origen: " + origen.getText().toString() + "\n" + "Destino: " + destino.getText().toString();
                //addres.add(ruta);
                //insertRoutesReservation(origen.getText().toString(),destino.getText().toString());
                sql = new SqliteController(getApplicationContext(),"kangup",null,1);
                int ix = sql.getReservacionIdNext();
                //sql.insertRutas(origen.getText().toString(),destino.getText().toString(),ix);

            }
        });
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public ArrayList<ListRoutes> fillRoutes(ListRoutes[] rou, ArrayList<ListRoutes> v){
        ArrayList<ListRoutes> list = new ArrayList<>();
        if(v.isEmpty()){
            ListRoutes r [] = new ListRoutes[rou.length];
            for(int i = 0; i < rou.length; i++){
                r[i] = new ListRoutes();
                r[i].setIdSQL(rou[i].getIdSQL());
                r[i].setOrigen(rou[i].getOrigen());
                r[i].setDestiny(rou[i].getDestiny());
                r[i].setId(rou[i].getId());
                r[i].setPosicion(rou[i].getPosicion());

                list.add(i,r[i]);
            }
            adapterRoutes.notifyDataSetChanged();
        }
        else{
            v.clear();
            ListRoutes r [] = new ListRoutes[rou.length];
            for(int i = 0; i < rou.length; i++){
                r[i] = new ListRoutes();
                r[i].setIdSQL(rou[i].getIdSQL());
                r[i].setOrigen(rou[i].getOrigen());
                r[i].setDestiny(rou[i].getDestiny());
                r[i].setId(rou[i].getId());
                r[i].setPosicion(rou[i].getPosicion());

                list.add(i,r[i]);
            }
            adapterRoutes.notifyDataSetChanged();
        }
        //list.clear();

        return list;
    }


    /*@Override
    public void sendData(ArrayList<ListRoutes> itemsRoutes) {

        itemsRoute = (ArrayList<ListRoutes>)itemsRoutes.clone();

    }*/
}

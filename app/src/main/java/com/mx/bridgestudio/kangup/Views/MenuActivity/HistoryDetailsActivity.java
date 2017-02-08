package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdapterNews;
import com.mx.bridgestudio.kangup.Adapters.AdapterPaquetes;
import com.mx.bridgestudio.kangup.Adapters.AdapterRoutes;
import com.mx.bridgestudio.kangup.AsyncTask.Viaje.RoutesByTrip;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendDetail;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPackageByReservation;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendRoutesByTrip;
import com.mx.bridgestudio.kangup.Controllers.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.DetalleViaje;
import com.mx.bridgestudio.kangup.Models.DividerItemDecoration;
import com.mx.bridgestudio.kangup.Models.History;
import com.mx.bridgestudio.kangup.Models.Lists.ListNews;
import com.mx.bridgestudio.kangup.Models.Lists.ListPaquetes;
import com.mx.bridgestudio.kangup.Models.Lists.ListRoutes;
import com.mx.bridgestudio.kangup.Models.Package;
import com.mx.bridgestudio.kangup.Models.Rutas;
import com.mx.bridgestudio.kangup.Models.SampleDivider;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.DetalleActivity;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;

import java.util.ArrayList;

public class HistoryDetailsActivity extends DrawerActivity implements OnDataSendDetail,OnDataSendRoutesByTrip, OnDataSendPackageByReservation {

    protected DrawerLayout mDrawer;

    private TextView marca,modelo,anio,socio,total;
    private RatingBar rateTrip;
    private Button verViaje,verPaquetes;
    private DetalleViaje det = new DetalleViaje();
    private Package pa = new Package();
    private webServices webs = new webServices();
    private User u = new User();
    private RatingBar rating;
    RecyclerView.Adapter adapter;
    ArrayList<ListRoutes> items= new ArrayList<>();
    RecyclerView.Adapter adapterPacks;
    ArrayList<ListPaquetes> items1= new ArrayList<>();

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;
    Control control = new Control();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_history_details);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //control.changeColorStatusBar(HistoryDetailsActivity.this);

        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_history_details, null, false);
        mDrawer.addView(contentView, 0);

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        det = (DetalleViaje) bundle.getSerializable("value");

        getSupportActionBar().setTitle("Detalle de viaje");

        RoutesByTrip.flagg =1;

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(HistoryDetailsActivity.this, CategoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(HistoryDetailsActivity.this, NewsActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(HistoryDetailsActivity.this, FavoriteActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(HistoryDetailsActivity.this, HistoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        marca = (TextView)findViewById(R.id.marcaTxt);
        modelo= (TextView)findViewById(R.id.modeloTxt);
        anio = (TextView)findViewById(R.id.anioTxt);
        socio= (TextView)findViewById(R.id.socioTxt);
        total= (TextView)findViewById(R.id.totalTxt);
        rating = (RatingBar) findViewById(R.id.ratingViaje);

        marca.setText(det.getMarca());
        modelo.setText(det.getModelo());
        anio.setText(det.getAnio());
        socio.setText(det.getSocio());
        total.setText("$ "+ det.getTotal());
        rating.setRating(Float.parseFloat(det.getValoracion()));

        SqliteController sql = new SqliteController(getApplicationContext(), "kangup", null, 1);
        sql.Connect();
        u = sql.user();
        sql.Close();

        verViaje = (Button)findViewById(R.id.viajeButton);
        verViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertFormElementsViajes(det);
            }
        });
        verPaquetes = (Button)findViewById(R.id.paqueteButton);
        verPaquetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertFormElementsPaquetes(pa);
            }
        });
    }

    @Override
    public void sendData(Vehicle obj) {}

    public void alertFormElementsViajes(DetalleViaje det) {

    /*
     * Inflate the XML view. activity_main is in
     * res/layout/form_elements.xml
     */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.dialog_viajes,
                null, false);

        // You have to list down your form elements
        final EditText fecha = (EditText) formElementsView
                .findViewById(R.id.fechaTxt);
        fecha.setText(det.getFecha());
        final EditText horaIni = (EditText) formElementsView
                .findViewById(R.id.horaIniTxt);
        horaIni.setText(det.getHoraInicio());
        final EditText horaFin = (EditText) formElementsView
                .findViewById(R.id.horaFinTxt);
        horaFin.setText(det.getHoraTermino());
        final EditText tiempo = (EditText) formElementsView
                .findViewById(R.id.tiempoTxt);
        tiempo.setText(det.getTiempo());

        Rutas ru = new Rutas();
        ru.setIdUsuario(u.getId());
        ru.setId_reservacion(det.getIdReservacion());

        webs.getRoutesByTrip(HistoryDetailsActivity.this,this,ru);

        final RecyclerView recycler;
        final RecyclerView.LayoutManager lManager;

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        recycler = (RecyclerView) formElementsView.findViewById(R.id.routesRecycler);
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

                        Snackbar snackbar = Snackbar.make(view, getString(R.string.app_name), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.app_name), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(HistoryDetailsActivity.this, getText(R.string.accept), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }


                })
        );


        // Crear un nuevo adaptador
        adapter = new AdapterRoutes(items);
        recycler.setAdapter(adapter);

        // the alert dialog
        new AlertDialog.Builder(HistoryDetailsActivity.this,R.style.MyDialogTheme).setView(formElementsView)
                .setTitle("Detalles de viaje")
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        items.clear();
                        dialog.dismiss();
                    }

                }).show();
    }

    public void fillList(Rutas[] rutas){
        ListRoutes[] list = new ListRoutes[rutas.length];
        for(int i = 0 ; i < rutas.length ; i++){
            list[i] = new ListRoutes();
            list[i].setOrigen(rutas[i].getOrigen());
            list[i].setDestiny(rutas[i].getDestino());
            items.add(i,list[i]);
        }
        adapter.notifyDataSetChanged();


    }

    @Override
    public void sendDataRoutesByTrip(Rutas[] obj) {
        fillList(obj);
    }

    public void alertFormElementsPaquetes(Package pack) {

    /*
     * Inflate the XML view. activity_main is in
     * res/layout/form_elements.xml
     */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.dialog_paquetes,
                null, false);

        // You have to list down your form elements

        webs.getPackagesByReservation(HistoryDetailsActivity.this,this,det.getIdReservacion(),u.getId());

        final RecyclerView recycler;
        final RecyclerView.LayoutManager lManager;

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        recycler = (RecyclerView) formElementsView.findViewById(R.id.paquetesRecycler);
        recycler.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);
        recycler.addItemDecoration(itemDecoration);
        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(this,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        Snackbar snackbar = Snackbar.make(view, getString(R.string.app_name), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.app_name), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(HistoryDetailsActivity.this, getText(R.string.accept), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }


                })
        );


        // Crear un nuevo adaptador
        adapterPacks = new AdapterPaquetes(items1);
        recycler.setAdapter(adapterPacks);

        // the alert dialog
        new AlertDialog.Builder(HistoryDetailsActivity.this,R.style.MyDialogTheme).setView(formElementsView)
                .setTitle("Paquetes escogidos")
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        items1.clear();
                        dialog.dismiss();
                    }

                }).show();
    }

    @Override
    public void sendDataPackageByReservation(Package[] obj) {
        fillListPacks(obj);
    }

    public void fillListPacks(Package[] packs){
        ListPaquetes[] list = new ListPaquetes[packs.length];
        for(int i = 0 ; i < packs.length ; i++){
            list[i] = new ListPaquetes();
            list[i].setNombre(packs[i].getNombre());
            list[i].setDescripcion(packs[i].getDescripcion());
            list[i].setPrecio(packs[i].getPrecio());
            items1.add(i,list[i]);
        }
        adapterPacks.notifyDataSetChanged();


    }
}

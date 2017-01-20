package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mx.bridgestudio.kangup.Adapters.AdapterAllPackages;
import com.mx.bridgestudio.kangup.Adapters.AdapterArticle;
import com.mx.bridgestudio.kangup.Adapters.AdapterPaquetes;
import com.mx.bridgestudio.kangup.Adapters.AdapterRoutes;
import com.mx.bridgestudio.kangup.Adapters.SpinnerAdapterPayment;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.DAO.DAOReservaciones;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendAllPackages;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPaymentFormsUser;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSentArticlesByPackage;
import com.mx.bridgestudio.kangup.Controllers.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.Article;
import com.mx.bridgestudio.kangup.Models.DetalleViaje;
import com.mx.bridgestudio.kangup.Models.DividerItemDecoration;
import com.mx.bridgestudio.kangup.Models.Lists.ListArticles;
import com.mx.bridgestudio.kangup.Models.Lists.ListPaquetes;
import com.mx.bridgestudio.kangup.Models.Lists.ListPaymentForm;
import com.mx.bridgestudio.kangup.Models.Lists.ListRoutes;
import com.mx.bridgestudio.kangup.Models.Package;
import com.mx.bridgestudio.kangup.Models.PaymentForm;
import com.mx.bridgestudio.kangup.Models.Rutas;
import com.mx.bridgestudio.kangup.Models.SampleDivider;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.GooglePlaces.PlacesAutoCompleteActivity;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryDetailsActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by USUARIO on 14/12/2016.
 */

public class Reservacion extends DrawerActivity implements View.OnClickListener, AdapterView.OnItemClickListener , OnDataSendAllPackages, OnDataSentArticlesByPackage, OnDataSendPaymentFormsUser {


    private static final String LOG_TAG = "kangup";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyCjWw7BILSM1YnBwf48nU-RglJP1fmOKUE";

    protected DrawerLayout mDrawer;
    private webServices webs = new webServices();
    private SqliteController sql;
    private User user = new User();
    private ArrayList<String> addres = new ArrayList<>();
    private EditText fecha, hora;
    private Button reservar, bAdd;
    private ImageButton bDate, bTime,noticias;
    private Date dt = new Date();
    private Date dtf = new Date();
    private com.mx.bridgestudio.kangup.Models.Reservacion reservacion = new com.mx.bridgestudio.kangup.Models.Reservacion();
    private Bundle bundle;
    private Date d = null;
    private String date;
    private int mes;
    private int anio;
    private int dia;
    private int id;
    Calendar ca = Calendar.getInstance();
    private Toast mToast;
    Calendar evento = Calendar.getInstance();
    Calendar time = Calendar.getInstance();
    DAOReservaciones dao = new DAOReservaciones();
    public int id_reservacion;
    public int getId;

    Control control = new Control();
    private DrawerActivity drw = new DrawerActivity();

    RecyclerView listPaquetes, listRutas;
    RecyclerView.LayoutManager lManagerPacks, lManagerRoutes;
    RecyclerView.Adapter adapterRoutes;
    ArrayList<ListRoutes> itemsRoutes= new ArrayList<>();
    RecyclerView.Adapter adapterPacks;
    ArrayList<ListPaquetes> itemsPacks= new ArrayList<>();
    public static String descripcionPaquete="";
    public static String nombrePaquete="";
    public int id_paquete=0;
    public static int idPack=0;
    public static int countPack=0;
    int id_pago_usuario=0;

    //Alert articulos
    RecyclerView.Adapter adapterArticulos;
    ArrayList<ListArticles> itemsArt= new ArrayList<>();

    //Alert spinner
    private ArrayList<ListPaymentForm> tipos = new ArrayList<>();
    //private ArrayList<String> tiposPagos = new ArrayList<>();
    private PaymentForm pa = new PaymentForm();
    private SpinnerAdapterPayment adapterPayment;
    private ListPaymentForm[] listPay;


    //toolbardown
    private ImageButton catalogo, favoritos, historial;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_favorite);
        control.changeColorStatusBar(Reservacion.this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_reservacion, null, false);
        mDrawer.addView(contentView, 0);

        //drw.setNameToolbar("Reservacion");
        getSupportActionBar().setTitle("Reservacion");

        webs.getAllPackages(Reservacion.this,this);

        getIdReservacion();


        reservar = (Button) findViewById(R.id.confirmarButton);
        reservar.setOnClickListener(this);

        bDate = (ImageButton) findViewById(R.id.imageButtonCalendar);
        bDate.setOnClickListener(this);

        bTime = (ImageButton) findViewById(R.id.imageButtonTime);
        bTime.setOnClickListener(this);

        bAdd = (Button) findViewById(R.id.addruta);
        bAdd.setOnClickListener(this);

        //RecyclerView
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);

        listPaquetes = (RecyclerView) findViewById(R.id.packsRecyclerView);
        listPaquetes.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        listPaquetes.setHasFixedSize(true);
        listRutas = (RecyclerView) findViewById(R.id.rutasRecyclerView);
        listRutas.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        listRutas.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
                lManagerPacks = new LinearLayoutManager(this);
        listPaquetes.setLayoutManager(lManagerPacks);
        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);
        listPaquetes.addItemDecoration(itemDecoration);
        listPaquetes.addOnItemTouchListener(
                new RecyclerItemClickListener(this, listPaquetes ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        id_paquete = itemsPacks.get(position).getId();
                        nombrePaquete = itemsPacks.get(position).getNombre();
                        descripcionPaquete = itemsPacks.get(position).getDescripcion();
                        alertFormElementsArticulos(id_paquete);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        // Usar un administrador para LinearLayout
        lManagerRoutes = new LinearLayoutManager(this);
        listRutas.setLayoutManager(lManagerRoutes);
        final RecyclerView.ItemDecoration itemDecorations = new SampleDivider(this);
        listRutas.addItemDecoration(itemDecorations);
        listRutas.addOnItemTouchListener(
                new RecyclerItemClickListener(this, listRutas,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {


                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        // Crear un nuevo adaptador
        adapterRoutes = new AdapterRoutes(itemsRoutes);
        listRutas.setAdapter(adapterRoutes);
        adapterPacks = new AdapterAllPackages(itemsPacks,this);
        listPaquetes.setAdapter(adapterPacks);


        catalogo = (ImageButton) findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(Reservacion.this, CategoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        noticias = (ImageButton) findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(Reservacion.this, NewsActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        favoritos = (ImageButton) findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(Reservacion.this, FavoriteActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        historial = (ImageButton) findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(Reservacion.this, HistoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        fecha = (EditText) findViewById(R.id.fecha);
        hora = (EditText) findViewById(R.id.hora);
        //     sql = new SqliteController(getApplicationContext(),"kangup",null,1);
        //    re = sql.getReservacion();
        //   fecha.setText(re.getDate());
        //  hora.setText(re.getHourI());

        //fillPackagesByReservation();

        sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
        sql.Connect();
        User user = new User();
        user = sql.user();
        pa.setId_usuario(user.getId());
        sql.Close();
        webs.getFormaPagoByUser(this,this,pa);

    }


    // webs.favsByUser(Reservacion.this,Reservacion.this,user);

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirmarButton) {
            //confirmar reservacion
            FormaPagoAlert();
            //Confirmacion(v);
        }
        if (v.getId() == R.id.imageButtonCalendar) {
            // seleccionar fecha de viaje
            control.showDialogCalendar(Reservacion.this,fecha);
        }
        if (v.getId() == R.id.imageButtonTime) {
            //seleccionar hota de viaje
            control.showDialogTime(Reservacion.this);
        }
        if (v.getId() == R.id.addruta) {
            //Agregar origenes y destinos
            showChangeLangDialog();

        }
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
                Intent setIntent = new Intent(Reservacion.this, PlacesAutoCompleteActivity.class);
                setIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(setIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        final EditText destino = (EditText) dialogView.findViewById(R.id.destinotxt);


        dialogBuilder.setTitle("Nueva ruta");
        dialogBuilder.setMessage("Introduzca su nueva ruta:");
        dialogBuilder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //String ruta = "Origen: " + origen.getText().toString() + "\n" + "Destino: " + destino.getText().toString();
                //addres.add(ruta);
               //insertRoutesReservation(origen.getText().toString(),destino.getText().toString());
                sql = new SqliteController(getApplicationContext(),"kangup",null,1);
                int ix = sql.getReservacionIdNext();
                sql.insertRutas(origen.getText().toString(),destino.getText().toString(),ix);

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

    public void Confirmacion() {


        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Confirmacion");
        dialogo.setMessage("¿Esta seguro de realizar la reservacion?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                insertRoutesReservation();
                insertPackagesReservation();
                com.mx.bridgestudio.kangup.Models.Reservacion r = new com.mx.bridgestudio.kangup.Models.Reservacion();
                r.setId_user(pa.getId_usuario());
                r.setId_vehicle(DetalleActivity.id_vehiculo_seleccionado);
                //r.setDate();
                //r.setHourI();
                //r.setHourF();
                r.setIdpayment(id_pago_usuario);
                insertReservation(r);

                onAddEventClicked();
                webs.EmailConfirmationReservation(Reservacion.this,reservacion);
                idPack=0;
                countPack=0;

            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Toast.makeText(getApplicationContext(),String.valueOf(countPack),Toast.LENGTH_SHORT).show();
            }
        });
        dialogo.show();


    }

    public void FormaPagoAlert() {

        /*
     * Inflate the XML view. activity_main is in
     * res/layout/form_elements.xml
     */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.forma_pago_dialog,
                null, false);


        final Spinner spinnerPagos = (Spinner)view.findViewById(R.id.spinnerPagos);


        spinnerPagos.setAdapter(adapterPayment);
        spinnerPagos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                // your code here
                    id_pago_usuario = listPay[position].getId_forma_pago();
                Toast.makeText(getApplicationContext(),String.valueOf(id_pago_usuario),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this,R.style.MyDialogTheme).setView(view);
        dialogo.setTitle("Elige tu forma de pago");
        dialogo.setMessage("¿Que forma de pago desea utilizar?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            Confirmacion();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Toast.makeText(getApplicationContext(),String.valueOf(countPack),Toast.LENGTH_SHORT).show();
            }
        });
        dialogo.show();


    }


    public void onAddEventClicked() {
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy", new Locale("es_ES"));
        SimpleDateFormat formatoo = new SimpleDateFormat("EEEE dd 'de' MMM 'del 'yyyy", new Locale("es_ES"));
        try {
            String fecha = "14/11/2016";
            d = formatoDeFecha.parse(fecha.toString());
            System.out.println("agenda" + d);
            ca.setTime(d);
            System.out.println("agenda" + ca.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        SimpleDateFormat formato = new SimpleDateFormat("hh:mm");
        try {
            String horaa = "14:00";
            String horaFinal = "15:00";

            dt = formato.parse(horaa);

            dtf = formato.parse(horaFinal);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        anio = ca.get(ca.YEAR);
        mes = ca.get(ca.MONTH);
        dia = ca.get(ca.DAY_OF_MONTH);
        System.out.println("minutos  " + anio + " mes  " + mes + "  dia  " + dia);
        //Asignar Tiempos de Inicio y Final
        evento.set(anio, mes, dia, dt.getHours(), dt.getMinutes());
        time.set(anio, mes, dia, dtf.getHours(), dtf.getMinutes());


        //  Calendar cal = Calendar.getInstance();
        // long startTime = cal.getTimeInMillis();
        // long endTime = cal.getTimeInMillis()  + 60 * 60 * 1000;

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, evento.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, time.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
        //CalendarContract.Events.

        intent.putExtra(CalendarContract.Events.TITLE, "Neel Birthday");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is a sample description");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Guest House");
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void fillListPacks(Package[] packs){
        ListPaquetes[] list = new ListPaquetes[packs.length];
        for(int i = 0 ; i < packs.length ; i++){
            list[i] = new ListPaquetes();
            list[i].setId(packs[i].getId());
            list[i].setNombre(packs[i].getNombre());
            list[i].setDescripcion(packs[i].getDescripcion());
            //list[i].setImage(news[i].getImagen());
            //Cmbiar por imagen del servidor
            list[i].setPrecio(packs[i].getPrecio());
            itemsPacks.add(i,list[i]);
        }
        adapterPacks.notifyDataSetChanged();

    }

    public void fillListArticulos(Article[] art){
        ListArticles[] list = new ListArticles[art.length];
        for(int i = 0 ; i < art.length ; i++){
            list[i] = new ListArticles();
            list[i].setId(art[i].getId());
            list[i].setNombre(art[i].getNombre());
            list[i].setDescripcion(art[i].getDescription());
            list[i].setPrecio(art[i].getPrecio());
            itemsArt.add(i,list[i]);
        }
        adapterArticulos.notifyDataSetChanged();

    }


    @Override
    public void sendDataAllPackages(Package[] obj) {
        fillListPacks(obj);
    }

    public void alertFormElementsArticulos(int id_paquete) {

    /*
     * Inflate the XML view. activity_main is in
     * res/layout/form_elements.xml
     */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.articulos_dialog,
                null, false);

        webs.getArticlesByPackage(Reservacion.this,this,id_paquete);

        final RecyclerView recycler;
        final RecyclerView.LayoutManager lManager;

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        recycler = (RecyclerView) formElementsView.findViewById(R.id.articleRecycle);
        recycler.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        final TextView desc = (TextView)formElementsView.findViewById(R.id.descripcionPacks);
        desc.setText(Reservacion.descripcionPaquete);
        final ImageView image = (ImageView)formElementsView.findViewById(R.id.imagePaquete);
        image.setImageResource(R.drawable.auto);

        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);
        recycler.addItemDecoration(itemDecoration);
        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        // Crear un nuevo adaptador
        adapterArticulos = new AdapterArticle(itemsArt);
        recycler.setAdapter(adapterArticulos);

        // the alert dialog
        new AlertDialog.Builder(Reservacion.this,R.style.MyDialogTheme).setView(formElementsView)
                .setTitle(Reservacion.nombrePaquete)
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        itemsArt.clear();
                        dialog.dismiss();
                    }

                }).show();
    }


    @Override
    public void sendDataArticleByPackage(Article[] obj) {
        fillListArticulos(obj);
    }

    public void insertRoutesReservation(){
        sql = new SqliteController(getApplicationContext(),"kangup",null,1);
        Rutas r [];
        r = sql.getRutas();
        for(int i=0; i<r.length;i++){
            Ion.with(Reservacion.this)
                    .load("POST", "http://kangup.com.mx/index.php/routes")
                    .setBodyParameter("origen", r[i].getOrigen())
                    .setBodyParameter("destino",r[i].getDestino())
                    .setBodyParameter("id_reservacion", String.valueOf(r[i].getId_reservacion()))
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            //String info="";
                            Toast msg = Toast.makeText(getBaseContext(),
                                    result, Toast.LENGTH_SHORT);
                            msg.show();
                        }
                    });
        }

        sql.deleteRoutes();
    }

    public void insertPackagesReservation(){
        sql = new SqliteController(getApplicationContext(),"kangup",null,1);
        int getPack[];
        int idRes=0;
        idRes = sql.getReservacionIdNext();
        getPack = sql.getIdPackages(countPack);
        for(int i=0; i<getPack.length; i++){
            Ion.with(Reservacion.this)
                    .load("POST", "http://kangup.com.mx/index.php/packages")
                    .setBodyParameter("id_paquete", String.valueOf(getPack[i]))
                    .setBodyParameter("id_reservacion", String.valueOf(idRes))
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            //String info="";
                            Toast msg = Toast.makeText(getBaseContext(),
                                    result, Toast.LENGTH_SHORT);
                            msg.show();
                        }
                    });
        }
        sql.deletePackages();
    }

    public void getIdReservacion(){
        Ion.with(Reservacion.this)
                .load("POST", "http://kangup.com.mx/index.php/idRes")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        //String info="";
                        try {
                            JSONObject objUser = new JSONObject(result);
                            //info = String.valueOf(objUser.getInt("id"));
                            id_reservacion = objUser.getInt("id")+1;
                            sql = new SqliteController(getApplicationContext(),"kangup",null,1);
                            sql.updateReservacionNext(id_reservacion);
                            Toast msg = Toast.makeText(getBaseContext(),
                                    "IdRes: "+String.valueOf(id_reservacion), Toast.LENGTH_LONG);
                            msg.show();

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
    }

    public void insertReservation(com.mx.bridgestudio.kangup.Models.Reservacion reservacion){
        Ion.with(Reservacion.this)
                .load("POST", "http://kangup.com.mx/index.php/insertReservation")
                .setBodyParameter("id_usuario",  String.valueOf(reservacion.getId_user()))
                .setBodyParameter("id_vehiculo",String.valueOf(reservacion.getId_vehicle()))
                .setBodyParameter("fecha", reservacion.getDate() )
                .setBodyParameter("hora_inicio", reservacion.getHourI())
                .setBodyParameter("hora_termino", reservacion.getHourF())
                .setBodyParameter("status", String.valueOf(0))
                .setBodyParameter("cancelado", String.valueOf(0))
                .setBodyParameter("id_pago_usuario", String.valueOf(reservacion.getIdpayment()))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        //String info="";
                        Toast msg = Toast.makeText(getBaseContext(),
                                result, Toast.LENGTH_SHORT);
                        msg.show();
                    }
                });
    }

    public ListPaymentForm[] fillspinner(PaymentForm[] pay){
        ListPaymentForm[] list = new ListPaymentForm[pay.length];
        for(int i = 0 ; i < pay.length ; i++){
            list[i] = new ListPaymentForm();
            list[i].setId_forma_pago(pay[i].getId_forma_pago());
            list[i].setNum_cuenta(pay[i].getNum_cuenta());
            list[i].setTipoPago(pay[i].getTipoPago());
            //tipos.add(i,list[i]);
        }

        return list;

    }

    /*public void fillStringPay(ListPaymentForm[] pay){
        String account[] = new String[pay.length];
        for(int i=0; i<pay.length;i++){
            account[i] = pay[i].getTipoPago();
            tiposPagos.add(i,account[i]);
        }
    }*/

    @Override
    public void sendDataPaymentFormsUser(PaymentForm[] obj) {

        listPay=fillspinner(obj);
        adapterPayment = new SpinnerAdapterPayment(this,
                android.R.layout.simple_spinner_item,
                listPay);
        adapterPayment.notifyDataSetChanged();
    }
}

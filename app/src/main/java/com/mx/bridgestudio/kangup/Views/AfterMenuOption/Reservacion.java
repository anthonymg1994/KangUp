package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mx.bridgestudio.kangup.Adapters.AdapterAllPackages;
import com.mx.bridgestudio.kangup.Adapters.AdapterArticle;
import com.mx.bridgestudio.kangup.Adapters.AdapterRoutes;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.DAO.DAOReservaciones;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendAllPackages;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSentArticlesByPackage;
import com.mx.bridgestudio.kangup.Controllers.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.Article;
import com.mx.bridgestudio.kangup.Models.DividerItemDecoration;
import com.mx.bridgestudio.kangup.Models.Lists.ListArticles;
import com.mx.bridgestudio.kangup.Models.Lists.ListPaquetes;
import com.mx.bridgestudio.kangup.Models.Lists.ListRoutes;
import com.mx.bridgestudio.kangup.Models.Package;
import com.mx.bridgestudio.kangup.Models.SampleDivider;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.GooglePlaces.PlacesAutoCompleteActivity;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by USUARIO on 14/12/2016.
 */

public class Reservacion extends DrawerActivity implements View.OnClickListener, AdapterView.OnItemClickListener , OnDataSendAllPackages, OnDataSentArticlesByPackage {


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
    public int id_reservacion=0;

    //Alert articulos
    RecyclerView.Adapter adapterArticulos;
    ArrayList<ListArticles> itemsArt= new ArrayList<>();


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
        adapterPacks = new AdapterAllPackages(itemsPacks);
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

    }


    // webs.favsByUser(Reservacion.this,Reservacion.this,user);

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirmarButton) {
            //confirmar reservacion
            Confirmacion(v);
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
                Intent setIntent = new Intent(Reservacion.this, PlacesAutoCompleteActivity.class);
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
               getId = getReservationId();
                Ion.with(Reservacion.this)
                        .load("POST", "http://kangup.com.mx/index.php/routes")
                        .setBodyParameter("origen", origen.getText().toString())
                        .setBodyParameter("destino",destino.getText().toString())
                        .setBodyParameter("id_reservacion", String.valueOf(getId))
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

    public void Confirmacion(final View v) {


        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Confirmacion");
        dialogo.setMessage("¿Esta seguro de realizar la reservacion?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                reservacion.setId(4);
                reservacion.setOrigen("s");
                reservacion.setDate("s");
                reservacion.setDestination("s");
                reservacion.setHourF("d");
                reservacion.setHourI("sad");
                reservacion.setId_user(4);
                reservacion.setId_vehicle(1);
                reservacion.setIdpayment(1);
                reservacion.setStatus(1);
                onAddEventClicked(v);
                webs.EmailConfirmationReservation(Reservacion.this,reservacion);

            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });
        dialogo.show();


    }




    public void onAddEventClicked(View view) {
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

        /*
        ContentResolver cr = getContentResolver();


        ContentValues v = new ContentValues();
        v.clear();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, v);

        v.clear();
        //Agrega Recordatorio Method:Notificacion 15 min antes
        String eventID = uri.getLastPathSegment();
        System.out.println("intent"+eventID);
        Uri REMINDERS_URI=Uri.parse(getCalendarUriBase(this) + "reminders");
        v=new ContentValues();
        v.put("event_id",eventID);
        v.put("method",1);
        v.put("minutes",15); //15=Minutos antes del evento
        cr.insert(REMINDERS_URI, v);
*/

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

    public int getReservationId(){
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
                            Toast msg = Toast.makeText(getBaseContext(),
                                    "IdRes: "+String.valueOf(id_reservacion), Toast.LENGTH_LONG);
                            msg.show();

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                });
        return id_reservacion;
    }
}

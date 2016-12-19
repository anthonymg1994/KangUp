package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdapterFavoriteList;
import com.mx.bridgestudio.kangup.Adapters.PlacesAutoCompleteAdapter;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.GooglePlaces.PlacesAutoCompleteActivity;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

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
    private ArrayList<String> addres = new ArrayList<>();


    private Button reservar, bAdd;
    private ImageButton bDate, bTime;
    private ListView listPaquetes, listRutas;
    private Date dt = new Date();
    private Date dtf = new Date();
    private EditText Evento;
    private EditText Fecha;
    private EditText Descrip;
    private EditText hora;
    private EditText horaFinal;
    private Bundle bundle;
    private Date d = null;
    private String date;
    private int mes;
    private int anio;
    private int dia;
    Uri EVENTS_URI = Uri.parse(getCalendarUriBase(Reservacion.this) + "events");
    Uri uri = null;
    private int id;
    Calendar ca = Calendar.getInstance();
    private Toast mToast;
    Calendar evento = Calendar.getInstance();
    Calendar time = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_favorite);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_reservacion, null, false);
        mDrawer.addView(contentView, 0);


        reservar = (Button) findViewById(R.id.button8);
        reservar.setOnClickListener(this);



       // listRutas.setAdapter(arrayAdapter);
    }


    // webs.favsByUser(Reservacion.this,Reservacion.this,user);

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button8) {
            //confirmar reservacion
            crearEvento();
        }

    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
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
                startActivity(setIntent);

            }
        });

        final EditText destino = (EditText) dialogView.findViewById(R.id.destinotxt);

        dialogBuilder.setTitle("Nueva ruta");
        dialogBuilder.setMessage("Introduzca su nueva ruta:");
        dialogBuilder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                //com.mx.bridgestudio.kangup.Models.Reservacion res = new com.mx.bridgestudio.kangup.Models.Reservacion();
                String ruta = "Origen: " + origen.getText().toString() + "\n" + "Destino: " + destino.getText().toString();
                addres.add(ruta);
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


    private String getCalendarUriBase(Reservacion agendar) {
        // TODO Auto-generated method stub
        String calendarUriBase = null;
        Uri calendars = Uri.parse("content://calendar/calendars");
        Cursor managedCursor = null;
        try {
            managedCursor = agendar.managedQuery(calendars, null, null, null, null);
        } catch (Exception e) {
        }
        if (managedCursor != null) {
            calendarUriBase = "content://calendar/";
        } else {
            calendars = Uri.parse("content://com.android.calendar/calendars");
            try {
                managedCursor = agendar.managedQuery(calendars, null, null, null, null);
            } catch (Exception e) {
            }
            if (managedCursor != null) {
                calendarUriBase = "content://com.android.calendar/";
            }
        }
        return calendarUriBase;
    }

    public void crearEvento() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Agenda");
        dialogo.setMessage("¿Desea Agendar el evento?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Aceptar();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });
        dialogo.show();
    }

    public void Aceptar() {
        int minutos = dt.getMinutes();
        System.out.println("minutos  ");
        int MinutosAlarma = dtf.getMinutes();
        anio = ca.get(ca.YEAR);
        mes = ca.get(ca.MONTH);
        dia = ca.get(ca.DAY_OF_MONTH);
        System.out.println("minutos  " + anio + " mes  " + mes + "  dia  " + dia);
        //Asignar Tiempos de Inicio y Final
        evento.set(anio, mes, dia, dt.getHours(), dt.getMinutes());
        time.set(anio, mes, dia, dtf.getHours(), dtf.getMinutes());
        //Agrega Evento
        ContentResolver cr = getContentResolver();
        ContentValues v = new ContentValues();
        v.clear();
        v.put(CalendarContract.Events.DTSTART, evento.getTimeInMillis());
        v.put(CalendarContract.Events.DTEND, time.getTimeInMillis());
        v.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Mexico_City");
        v.put(CalendarContract.Events.TITLE, "prueba");
        v.put(CalendarContract.Events.DESCRIPTION,"prueba");
        v.put(CalendarContract.Events.CALENDAR_ID, 1);
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
        System.out.println(v);
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
        Toast t=Toast.makeText(this,"Evento Agendado", Toast.LENGTH_SHORT);
        t.show();
        anio=0;
        mes=0;
        dia=0;
        minutos=0;
        MinutosAlarma=0;

    }




}

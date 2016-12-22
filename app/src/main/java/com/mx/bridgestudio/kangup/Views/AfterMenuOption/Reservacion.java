package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.DAO.DAOReservaciones;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.GooglePlaces.PlacesAutoCompleteActivity;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by USUARIO on 14/12/2016.
 */

public class Reservacion extends DrawerActivity implements View.OnClickListener {



    protected DrawerLayout mDrawer;
    private webServices webs = new webServices();
    private SqliteController sql;
    private User user = new User();
    private ArrayList<String> addres = new ArrayList<>();
    private EditText fecha, hora;
    private Button reservar, bAdd;
    private ImageButton bDate, bTime,noticias;
    private ListView listPaquetes, listRutas;
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

    Control control = new Control();
    private DrawerActivity drw = new DrawerActivity();


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


        reservar = (Button) findViewById(R.id.confirmarButton);
        reservar.setOnClickListener(this);

        bDate = (ImageButton) findViewById(R.id.imageButtonCalendar);
        bDate.setOnClickListener(this);

        bTime = (ImageButton) findViewById(R.id.imageButtonTime);
        bTime.setOnClickListener(this);

        bAdd = (Button) findViewById(R.id.addruta);
        bAdd.setOnClickListener(this);

        listPaquetes = (ListView) findViewById(R.id.listViewPaquetes);
        listRutas = (ListView) findViewById(R.id.listViewRuta);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                addres);

        listRutas.setAdapter(arrayAdapter);

        catalogo = (ImageButton) findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(Reservacion.this, CategoryActivity.class));
            }
        });
        noticias = (ImageButton) findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(Reservacion.this, NewsActivity.class));
            }
        });
        favoritos = (ImageButton) findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(Reservacion.this, FavoriteActivity.class));
            }
        });
        historial = (ImageButton) findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(Reservacion.this, HistoryActivity.class));
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
        }
        if (v.getId() == R.id.imageButtonTime) {
            //seleccionar hota de viaje
        }
        if (v.getId() == R.id.addruta) {
            //Agregar origenes y destinos
            showChangeLangDialog();
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
    }


}

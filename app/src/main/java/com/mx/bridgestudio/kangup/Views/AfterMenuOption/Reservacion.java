package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;

import java.util.ArrayList;

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


    private Button reservar,bAdd;
    private ImageButton bDate,bTime;
    private ListView listPaquetes,listRutas;

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_favorite);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_reservacion, null, false);
        mDrawer.addView(contentView, 0);


        reservar = (Button) findViewById(R.id.button8);
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
                addres );

        listRutas.setAdapter(arrayAdapter);

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(Reservacion.this, CategoryActivity.class));
            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(Reservacion.this, FavoriteActivity.class));
            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(Reservacion.this, HistoryActivity.class));
            }
        });

    }




       // webs.favsByUser(Reservacion.this,Reservacion.this,user);

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button8){
            //confirmar reservacion
        }
        if(v.getId() == R.id.imageButtonCalendar){
            // seleccionar fecha de viaje
        }
        if(v.getId() == R.id.imageButtonTime){
            //seleccionar hota de viaje
        }
        if(v.getId() == R.id.addruta){
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
                Intent setIntent = new Intent(Reservacion.this,PlacesAutoCompleteActivity.class);
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
                String ruta= "Origen: "+ origen.getText().toString() + "\n" + "Destino: " + destino.getText().toString();
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
}

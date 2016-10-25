package com.mx.bridgestudio.kangup.Views;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mx.bridgestudio.kangup.Adapters.AdaptadorList;

import com.mx.bridgestudio.kangup.Controllers.BaseActivity;
import com.mx.bridgestudio.kangup.Models.ListCar;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;

/**
 * Created by USUARIO on 24/10/2016.
 */

public class TypesOfAutomobiles extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener,NavigationView.OnNavigationItemSelectedListener {

    private ListView lista;
    private ArrayList<ListCar> tipos = new ArrayList<ListCar>();
    private ArrayAdapter<ListCar> AdapterArray;
    private ListView list;
    private AdaptadorList adaptador;
    private String opcionSeleccionada="";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types);
        lista = (ListView)findViewById(R.id.listView);



    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    public void fillList(){
         ListCar[] categorias = new ListCar[1];
        categorias[0] = new ListCar(1,"Nombre","Descripccion");

        tipos.add(categorias[0]);


        adaptador = new AdaptadorList(this, R.layout.celltype, tipos);
        list.setAdapter(adaptador);
        list.setOnItemClickListener(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                opcionSeleccionada = ((ListCar) arg0.getAdapter().getItem(position)).getName();
                //  String index = ((ListCategory) arg0.getAdapter().getItem(position)).getDescripcion();
                //    Toast.makeText(categorias.this,opcionSeleccionada, Toast.LENGTH_SHORT).show();
                /*
                if (opcionSeleccionada != "" || index != "") {
                    Intent intent = new Intent().setClass(categorias.this, NotificationDetail.class);
                    intent.putExtra("titulo", titulo);
                    intent.putExtra("descripcion", opcionSeleccionada);
                    intent.putExtra("fecha", fechc);
                    intent.putExtra("emisor", emmisor);
                    startActivity(intent);
                    finish();
                }

                */

            }
        });
    }
}

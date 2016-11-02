package com.mx.bridgestudio.kangup.Views;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.mx.bridgestudio.kangup.Adapters.AdaptadorType;
import com.mx.bridgestudio.kangup.Controllers.BaseActivity;
import com.mx.bridgestudio.kangup.Models.ListCar;
import com.mx.bridgestudio.kangup.Models.SampleDivider;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 24/10/2016.
 */

public class CarsXtype extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener,NavigationView.OnNavigationItemSelectedListener {

    //  private ListView lista;
    // private ArrayList<ListCar> tipos = new ArrayList<ListCar>();
    // private ArrayAdapter<ListCar> AdapterArray;
    // private ListView list;
    // private AdaptadorList adaptador;
    private String opcionSeleccionada="";
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List items = new ArrayList();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_types);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tipos de autos");
        setSupportActionBar(toolbar);
        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);
        recycler.addItemDecoration(itemDecoration);
        // Crear un nuevo adaptador
        adapter = new AdaptadorType(items);
        recycler.setAdapter(adapter);
        fillList();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    public void fillList(){
        items.add(new ListCar(R.drawable.ic_menu_camera, "Tipos de automoviles","Breve descripcion del tipo de automovil"));
        items.add(new ListCar(R.drawable.ic_menu_camera, "Tipos de automoviles","Breve descripcion del tipo de automovil"));
        items.add(new ListCar(R.drawable.ic_menu_camera, "Tipos de automoviles","Breve descripcion del tipo de automovil"));
        items.add(new ListCar(R.drawable.ic_menu_camera, "Tipos de automoviles","Breve descripcion del tipo de automovil"));

    }
}

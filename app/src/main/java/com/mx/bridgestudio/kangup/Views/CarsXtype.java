package com.mx.bridgestudio.kangup.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdaptadorType;
import com.mx.bridgestudio.kangup.Adapters.CardAdapter;
import com.mx.bridgestudio.kangup.Controllers.BaseActivity;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendCarXtype;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Lists.ListBrand;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.SampleDivider;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;

/**
 * Created by USUARIO on 24/10/2016.
 */

public class CarsXtype extends BaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener,NavigationView.OnNavigationItemSelectedListener,OnDataSendCarXtype {

    //  private ListView lista;
    // private ArrayList<ListCar> tipos = new ArrayList<ListCar>();
    // private ArrayAdapter<ListCar> AdapterArray;
    // private ListView list;
    // private AdaptadorList adaptador;
    private String opcionSeleccionada="";
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private webServices webs = new webServices();
    private Vehicle vehicle = new Vehicle();

    // private List items = new ArrayList();
    ArrayList<ListCar> items= new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_types);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarr);
        toolbar.setTitle(""+CardAdapter.marca);
        setSupportActionBar(toolbar);

        vehicle.setId_categoria(CategoryActivity.opcionSeleccionada);
        vehicle.setId_brand(CardAdapter.id_marca);
        webs.AutosByMarca(CarsXtype.this,CarsXtype.this,vehicle);



        // Obtener el Recycler
        CatalogCar.flagDate = 1;
        recycler = (RecyclerView) findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);
        recycler.addItemDecoration(itemDecoration);
        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(view.getContext(), "position = " +items.get(position).getId(), Toast.LENGTH_SHORT).show();
                       // int opcionSeleccionada = items.get(position).getId();
                        Intent intent = new Intent().setClass(
                                CarsXtype.this, DetalleActivity.class);
                        //vehicle.setId(opcionSeleccionada);
                        //webs.DetalleAuto(CarsXtype.this, vehicle);
                        startActivity(intent);
                        finish();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

      // webs.AutosByMarca(this,vehicle);


        // Crear un nuevo adaptador
        adapter = new AdaptadorType(items);
        recycler.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    public void fillList(Vehicle[] vehicle){
        ListCar[] list = new ListCar[vehicle.length];
        for(int i = 0 ; i < vehicle.length ; i++){
            list[i] = new ListCar();
            list[i].setId(vehicle[i].getId());
            list[i].setModelo(vehicle[i].getModel());
            list[i].setMarca(vehicle[i].getMarca());
            list[i].setAnio(vehicle[i].getYear());
            //Cmbiar por imagen del servidor
            list[i].setImage(1);
            items.add(i,list[i]);
        }
        adapter.notifyDataSetChanged();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,CatalogCar.class);
        startActivity(setIntent);
        finish();
    }

    @Override
    public void sendData(Vehicle[] obj) {
        Toast.makeText(this, "Marcas"+obj.length, Toast.LENGTH_SHORT).show();
        fillList(obj);
    }
    /*
    @Override
    public void sendData(Vehicle[] vehicle) {
        Toast.makeText(this, "string"+response, Toast.LENGTH_SHORT).show();
        for(int i = 0; i < vehicle.lenght(); i ++){
            items.add(i,vehicle[i]);
        }
         adapter.notifyDataSetChanged();
    }
    */
}

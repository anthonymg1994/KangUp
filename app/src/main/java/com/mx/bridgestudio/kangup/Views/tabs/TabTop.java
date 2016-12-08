package com.mx.bridgestudio.kangup.Views.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdaptadorType;
import com.mx.bridgestudio.kangup.Adapters.CardAdapter;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendCarXtype;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.SampleDivider;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.CarsXtype;
import com.mx.bridgestudio.kangup.Views.CatalogCar;
import com.mx.bridgestudio.kangup.Views.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.DetalleActivity;
import com.mx.bridgestudio.kangup.Views.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Views.TypesOfAutomobiles;

import java.util.ArrayList;

/**
 * Created by Isaac on 06/12/2016.
 */

public class TabTop extends Fragment implements OnDataSendCarXtype {

    private String opcionSeleccionada="";
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private webServices webs = new webServices();
    private Vehicle vehicle = new Vehicle();

    ArrayList<ListCar> items= new ArrayList<>();

    //Context context,act;

    public TabTop() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_top,container,false);

        //context = v.getContext();

        vehicle.setId_categoria(CategoryActivity.opcionSeleccionada);
        vehicle.setId_brand(CardAdapter.id_marca);
        webs.AutosByMarca(TabTop.this,getActivity(),vehicle);

        // Obtener el Recycler
        CatalogCar.flagDate = 1;

        recycler = (RecyclerView) v.findViewById(R.id.recycler_view);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);
        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(getActivity());
        recycler.addItemDecoration(itemDecoration);
        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "position = " +items.get(position).getId(), Toast.LENGTH_SHORT).show();
                        // int opcionSeleccionada = items.get(position).getId();
                        Intent intent = new Intent(getActivity(), DetalleActivity.class);
                        TabTop.this.startActivity(intent);
                        //finish();
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

        return v;
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
    public void sendData(Vehicle[] obj) {
        Toast.makeText(getActivity(), "Marcas"+obj.length, Toast.LENGTH_SHORT).show();
        fillList(obj);
    }

}

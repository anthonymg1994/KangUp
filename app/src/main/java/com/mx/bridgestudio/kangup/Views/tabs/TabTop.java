package com.mx.bridgestudio.kangup.Views.tabs;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdaptadorType;
import com.mx.bridgestudio.kangup.Adapters.CardAdapter;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendCarXtype;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendDetail;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFilterScore;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFilterTop;
import com.mx.bridgestudio.kangup.Controllers.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.DividerItemDecoration;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.SampleDivider;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CarsXtype;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CatalogCar;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Isaac on 06/12/2016.
 */

public class TabTop extends Fragment implements OnDataSendCarXtype,OnDataSendDetail,OnDataSendFilterTop {

    private String opcionSeleccionada="";
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private webServices webs = new webServices();
    private Vehicle vehicle = new Vehicle();
    public static int id_vehiculo = 0;
    public static String nombre_vehiculo = "";
    public static int flag = 0;
    ArrayList<ListCar> items= new ArrayList<>();

    //Context context,act;

    public TabTop() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // int c =((CarsXtype)this.getActivity()).getJob();
       // Toast.makeText(getActivity(), "HOLA :"+ c, Toast.LENGTH_SHORT).show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_top,container,false);

        //context = v.getContext();

        vehicle.setId_categoria(CategoryActivity.opcionSeleccionada);
        vehicle.setId_brand(CardAdapter.id_marca);
        Date fecha = CardAdapter.datee;
        String hora = CardAdapter.hour;
        String hour_final = CardAdapter.hour_final;

        try {

            DateFormat sdf = new SimpleDateFormat("hh:mm");
            Date date2 = sdf.parse(hora);

            DateFormat sdf1 = new SimpleDateFormat("hh:mm");
            Date date3 = sdf1.parse(hour_final);



            webs.getVehiclesByTop(getActivity(),vehicle,fecha,date2,date3,TabTop.this);


        } catch (ParseException e) {
            e.printStackTrace();
        }



        // Obtener el Recycler
        CatalogCar.flagDate = 1;

        recycler = (RecyclerView) v.findViewById(R.id.recycler_view);
        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider);

        recycler.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);
        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(getActivity());
        recycler.addItemDecoration(itemDecoration);
        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity() ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {




                        Toast.makeText(getActivity(), "position = " +items.get(position).getId(), Toast.LENGTH_SHORT).show();
                        int opcionSeleccionada = items.get(position).getId();
                        //Intent intent = new Intent(getActivity(), DetalleActivity.class);
                        id_vehiculo = opcionSeleccionada;
                        nombre_vehiculo = items.get(position).getMarca() + " " + items.get(position).getModelo() + " " + items.get(position).getAnio();
                        vehicle.setId(id_vehiculo);
                        //if(view.getId() == R.id.starButton){
                         //   Toast.makeText(getActivity(), "ghola", Toast.LENGTH_SHORT).show();

                       // }else {
                           // webs.DetailVehicle(TabTop.this, getActivity(), vehicle);
                        //}
                      //  TabTop.this.startActivity(intent);
                        //finish();
                    }


                })
        );

        // webs.AutosByMarca(this,vehicle);


        // Crear un nuevo adaptador
        adapter = new AdaptadorType(getActivity(),items);
        recycler.setAdapter(adapter);

        return v;
    }

    public void fillList(Vehicle[] vehicle){

        final String URL = "http://kangup.com.mx/uploads/Vehiculos/";


        ListCar[] list = new ListCar[vehicle.length];
        for(int i = 0 ; i < vehicle.length ; i++){
            list[i] = new ListCar();
            list[i].setId(vehicle[i].getId());
            list[i].setModelo(vehicle[i].getModel());
            list[i].setMarca(vehicle[i].getMarca());
            list[i].setAnio(vehicle[i].getYear());
            //Cmbiar por imagen del servidor
            list[i].setImage(URL+vehicle[i].getFoto());
            items.add(i,list[i]);
        }
        adapter.notifyDataSetChanged();


    }

    @Override
    public void sendData(Vehicle[] obj) {
        Toast.makeText(getActivity(), "Marcas"+obj.length, Toast.LENGTH_SHORT).show();
       // fillList(obj);
    }


    @Override
    public void sendData(Vehicle obj) {

        vehicle = obj;
    }


    public ArrayList<ListCar> getItemsFromFragment(){
        return items;
    }

    @Override
    public void sendDataTops(Vehicle[] obj) {
        fillList(obj);
    }
}

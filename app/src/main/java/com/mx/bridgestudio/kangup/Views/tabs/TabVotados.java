package com.mx.bridgestudio.kangup.Views.tabs;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdaptadorType;
import com.mx.bridgestudio.kangup.Adapters.CardAdapter;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendCarXtype;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendDetail;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFilterScore;
import com.mx.bridgestudio.kangup.Controllers.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.DividerItemDecoration;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.SampleDivider;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CatalogCar;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by USUARIO on 04/01/2017.
 */

public class TabVotados extends Fragment implements OnDataSendFilterScore,OnDataSendDetail {

    private String opcionSeleccionada="";
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private webServices webs = new webServices();
    private Vehicle vehicle = new Vehicle();
    public static int id_vehiculo = 0;
    public static String nombre_vehiculo = "";
    ArrayList<ListCar> items= new ArrayList<>();

    //Context context,act;

    public TabVotados() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_votados,container,false);

        //context = v.getContext();

        vehicle.setId_categoria(CategoryActivity.opcionSeleccionada);
        vehicle.setId_brand(CardAdapter.id_marca);
        String fecha = CardAdapter.datee;
        String hora = CardAdapter.hour;
        String hour_final = CardAdapter.hour_final;

        // parseo de string para convertir a date
        String dateString = fecha;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //parseo de string a time

        String time = hora;
        String time_final = hour_final;

        SimpleDateFormat timFormat = new SimpleDateFormat("hh:mm");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");
        Date date3 = null,date2 = null;


        try {
            Date date = dateFormat.parse(time);
            String out = dateFormat2.format(date);
             date2 = dateFormat2.parse(out);

            Date date1 = dateFormat.parse(time);
            String out2 = dateFormat2.format(date);
             date3 = dateFormat2.parse(out2);


        } catch (ParseException e) {
        }

        webs.TopRankingVehiculo(getActivity(),vehicle,convertedDate,date2,date3,TabVotados.this);

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
                new RecyclerItemClickListener(getActivity(), recycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "position = " +items.get(position).getId(), Toast.LENGTH_SHORT).show();
                        int opcionSeleccionada = items.get(position).getId();
                        //Intent intent = new Intent(getActivity(), DetalleActivity.class);
                        id_vehiculo = opcionSeleccionada;
                        nombre_vehiculo = items.get(position).getMarca() + " " + items.get(position).getModelo() + " " + items.get(position).getAnio();
                        vehicle.setId(id_vehiculo);
                        webs.DetailVehicle(TabVotados.this,getActivity(),vehicle);

                        //  TabTop.this.startActivity(intent);
                        //finish();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
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
    public void sendDataScore(Vehicle[] obj) {
        fillList(obj);
    }

    @Override
    public void sendData(Vehicle obj) {
        vehicle = obj;

    }
}

package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.SlidingImage_Adapter;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendDetail;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.ListEspecificaciones;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;
import com.mx.bridgestudio.kangup.Views.tabs.TabTop;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DetalleActivity extends DrawerActivity implements OnDataSendDetail, View.OnClickListener {


    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    //Aqui se cargara las imagenes del servidor
    private static final Integer[] IMAGES= {R.drawable.auto,R.drawable.auto,R.drawable.auto,R.drawable.auto};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private webServices webs = new webServices();
    private Vehicle vehicle = new Vehicle();
    private int id_vehiculo = 0;
    private String nombre_vehiculo = "";
    RecyclerView horizontal_recycler_view;
    HorizontalAdapter horizontalAdapter;
    private List<ListEspecificaciones> data;
    protected DrawerLayout mDrawer;
    private Button vermas, reservar;
    private TextView modelo, descripcion;
    Vehicle car = new Vehicle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_detalle);
     //   vehicle.setId(id_vehiculo);
      //  webs.DetailVehicle(this,this,vehicle);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!




        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_detalle, null, false);
        mDrawer.addView(contentView, 0);

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();

        car=(Vehicle)bundle.getSerializable("value");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detalle de automovil");
        setSupportActionBar(toolbar);


        vermas = (Button)findViewById(R.id.vermas);
        vermas.setOnClickListener(this);
        reservar = (Button)findViewById(R.id.reservarr);
        reservar.setOnClickListener(this);
        modelo = (TextView) findViewById(R.id.modelo);
        descripcion = (TextView) findViewById(R.id.descripcion);
        horizontal_recycler_view= (RecyclerView) findViewById(R.id.horizontal_recycler_view);

        data = fill_with_data();

        horizontalAdapter=new HorizontalAdapter(data, getApplication());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DetalleActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManager);
        horizontal_recycler_view.setAdapter(horizontalAdapter);


        fillFields(car);
    }
    /*
    @Override
    public void sendData(Vehicle vehicle) {
        Toast.makeText(this, "string"+response, Toast.LENGTH_SHORT).show();
        vehicle = vehicle;
    }
    */
    public void fillFields(Vehicle vehicle){
        modelo.setText(vehicle.getModel() + " " + vehicle.getYear() + " " + vehicle.getMarca());
        descripcion.setText(vehicle.getDescription());
    }


    public List<ListEspecificaciones> fill_with_data() {

        List<ListEspecificaciones> data = new ArrayList<>();

        data.add(new ListEspecificaciones( R.drawable.detalle_autoa, "Image 1"));
        data.add(new ListEspecificaciones( R.drawable.detalle_autob, "Image 2"));
        data.add(new ListEspecificaciones( R.drawable.detalle_autos, "Image 3"));
        data.add(new ListEspecificaciones( R.drawable.detalle_autob, "Image 1"));
        data.add(new ListEspecificaciones( R.drawable.detalle_autoa, "Image 2"));
        data.add(new ListEspecificaciones( R.drawable.detalle_autos, "Image 3"));

        return data;
    }
    public void FillField(Vehicle car){
        modelo.setText(car.getModel()+ " " + car.getYear() + " " + car.getMarca());
        descripcion.setText(car.getDescription());
    }

    private void init(Vehicle vehicle) {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

      //  mPager = (ViewPager) findViewById(R.id.pager);


       // mPager.setAdapter(new SlidingImage_Adapter(DetalleActivity.this,ImagesArray));



        //indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius



    }
    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,CarsXtype.class);
        startActivity(setIntent);
        finish();
    }

    @Override
    public void sendData(Vehicle obj) {
        init(obj);
    }

    @Override
    public void onClick(View v) {
            if(v.getId() == R.id.reservarr){
                Intent setIntent = new Intent(this,Reservacion.class);
                startActivity(setIntent);
                finish();
            }
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {


        List<ListEspecificaciones> horizontalList = Collections.emptyList();
        Context context;


        public HorizontalAdapter(List<ListEspecificaciones> horizontalList, Context context) {
            this.horizontalList = horizontalList;
            this.context = context;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView txtview;
            public MyViewHolder(View view) {
                super(view);
                imageView=(ImageView) view.findViewById(R.id.imageview);
                txtview=(TextView) view.findViewById(R.id.txtview);
            }
        }



        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_menu, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.imageView.setImageResource(horizontalList.get(position).getId_image());
            holder.txtview.setText(horizontalList.get(position).getNombre());

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    String list = horizontalList.get(position).getNombre().toString();
                    Toast.makeText(DetalleActivity.this, list, Toast.LENGTH_SHORT).show();
                }

            });

        }


        @Override
        public int getItemCount()
        {
            return horizontalList.size();
        }
    }




}

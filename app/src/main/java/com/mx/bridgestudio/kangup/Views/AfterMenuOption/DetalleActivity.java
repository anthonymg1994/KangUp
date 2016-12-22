package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.SlidingImage_Adapter;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendDetail;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.ListEspecificaciones;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;
import com.viewpagerindicator.CirclePageIndicator;

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
    Dialog dialogo;
    ArrayList<String> images = new ArrayList<String>();
    SlidingImage_Adapter s;
    ViewPager page;
    CirclePageIndicator indicator;
    RatingBar ratingBar;
    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;
    Control control = new Control();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_detalle);
     //   vehicle.setId(id_vehiculo);
      //  webs.DetailVehicle(this,this,vehicle);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        control.changeColorStatusBar(DetalleActivity.this);


        //dialog.setTitle();





        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_detalle, null, false);
        mDrawer.addView(contentView, 0);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        Drawable drawable = ratingBar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#ffcc00"), PorterDuff.Mode.SRC_ATOP);

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();

        car=(Vehicle)bundle.getSerializable("value");

        getSupportActionBar().setTitle(""+car.getModel()+ " " + car.getYear() +" " +car.getMarca());

        vermas = (Button)findViewById(R.id.vermas);
        vermas.setOnClickListener(this);
        reservar = (Button)findViewById(R.id.reservarr);
        reservar.setOnClickListener(this);
        modelo = (TextView) findViewById(R.id.modelo);
        descripcion = (TextView) findViewById(R.id.descripcion);
     //   horizontal_recycler_view= (RecyclerView) findViewById(R.id.horizontal_recycler_view);

      //  data = fill_with_data();

      //  horizontalAdapter=new HorizontalAdapter(data, getApplication());
       // LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DetalleActivity.this, LinearLayoutManager.HORIZONTAL, false);
        //horizontal_recycler_view.setLayoutManager(horizontalLayoutManager);
        //horizontal_recycler_view.setAdapter(horizontalAdapter);

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(DetalleActivity.this, CategoryActivity.class));
            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(DetalleActivity.this, NewsActivity.class));
            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(DetalleActivity.this, FavoriteActivity.class));
            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(DetalleActivity.this, HistoryActivity.class));
            }
        });


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


    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.fragment_page, null);


        dialogBuilder.setView(dialogView);


 page = (ViewPager) dialogView.findViewById(R.id.pagerId);
      indicator = (CirclePageIndicator)
              dialogView.findViewById(R.id.indicator);


init();
        dialogBuilder.setTitle("Galeria de imagenes");
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public List<ListEspecificaciones> fill_with_data() {

        List<ListEspecificaciones> data = new ArrayList<>();
        ArrayList<String> onlySpec = new ArrayList<String>();
        int flag = 0;

        String especificaciones = car.getSpecifications();
        //validar espacios
        String[] items = especificaciones.split(",");

        for (String item : items)
        {
            flag ++;
            onlySpec.add(item);
            System.out.println("item = " + item);
        }
        ListEspecificaciones list = new ListEspecificaciones();
        for (int i = 0; i < onlySpec.size() ; i ++){

            list.setId_image(R.drawable.detalle_autoa);
            list.setNombre(onlySpec.get(i));
            data.add(i,list);
        }
        /*
        data.add(new ListEspecificaciones( R.drawable.detalle_autoa, "Image 1"));
        data.add(new ListEspecificaciones( R.drawable.detalle_autob, "Image 2"));
        data.add(new ListEspecificaciones( R.drawable.detalle_autos, "Image 3"));
        data.add(new ListEspecificaciones( R.drawable.detalle_autob, "Image 1"));
        data.add(new ListEspecificaciones( R.drawable.detalle_autoa, "Image 2"));
        data.add(new ListEspecificaciones( R.drawable.detalle_autos, "Image 3"));
*/
        return data;
    }
    public void FillField(Vehicle car){
        modelo.setText(car.getModel()+ " " + car.getYear() + " " + car.getMarca());
        descripcion.setText(car.getDescription());
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
      //  init(obj);
    }

    @Override
    public void onClick(View v) {
            if(v.getId() == R.id.reservarr){
                Intent setIntent = new Intent(this,Reservacion.class);
                startActivity(setIntent);
                finish();
            }
        if(v.getId() == R.id.vermas){

            showChangeLangDialog();
        }
    }
    private void init() {
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);



        page.setAdapter(new SlidingImage_Adapter(DetalleActivity.this,ImagesArray));



        indicator.setViewPager(page);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                page.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

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

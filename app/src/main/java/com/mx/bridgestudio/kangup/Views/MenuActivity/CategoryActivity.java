package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.mx.bridgestudio.kangup.Adapters.AdapterCategory;
import com.mx.bridgestudio.kangup.Adapters.AndroidImageAdapter;
import com.mx.bridgestudio.kangup.Adapters.SlidingImage_Adapter;
import com.mx.bridgestudio.kangup.AsyncTask.MarcaModelo.AsyncBrands;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendPublicidad;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Brand;
import com.mx.bridgestudio.kangup.Models.Category;
import com.mx.bridgestudio.kangup.Models.Lists.ListPublicidad;
import com.mx.bridgestudio.kangup.Models.Publicidad;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.CatalogCar;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.DetalleActivity;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.Reservacion;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.RegisterActivity;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CategoryActivity extends DrawerActivity implements AdapterView.OnItemClickListener,OnDataSendPublicidad{

    private ListView list;
    private ArrayList<Category> tipos = new ArrayList<>();
    private AdapterCategory adaptador;
    private webServices webs = new webServices();
    private Brand brand = new Brand();
    public static int opcionSeleccionada;
    AsyncBrands asyncTask;
    Control control = new Control();
    protected DrawerLayout mDrawer;
    private List<ListPublicidad> data;
    private RecyclerView horizontal_recycler_view;
    private ArrayList<String> horizontalList;
    private static final Integer[] IMAGES= {R.drawable.auto,R.drawable.auto,R.drawable.auto,R.drawable.auto};
    private static String[] imagenes_publicidad;
    SlidingImage_Adapter s;
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    ViewPager page,pagePublicidad;
    CirclePageIndicator indicator;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    //ctoolbardown
    private ImageButton catalogo,noticias,favoritos,historial;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_category);
        control.changeColorStatusBar(CategoryActivity.this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);






        View contentView = inflater.inflate(R.layout.activity_category, null, false);

        mDrawer.addView(contentView, 0);




        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        pagePublicidad = (ViewPager) findViewById(R.id.viewPagePublicidad);


        AndroidImageAdapter adapterView = new AndroidImageAdapter(this);
        mViewPager.setAdapter(adapterView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarcateg);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"your icon was clicked",Toast.LENGTH_SHORT).show();
            }
        });

        webs.getAllPublicidad(this,this);
     //   getPublicidad();
        getSupportActionBar().setTitle("Categorias");





        list = (ListView)findViewById(R.id.listCategory);

        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adaptador = new AdapterCategory(this,tipos);
        list.setAdapter(adaptador);
        //list.setOnClickListener(this);
        list.setOnItemClickListener(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                 opcionSeleccionada = ((Category) arg0.getAdapter().getItem(position)).getId();
                brand.setId_categoria(opcionSeleccionada);
                Intent intent = new Intent(CategoryActivity.this, CatalogCar.class);
                //SendToActivity.sendData(arrayBrands);
                intent.putExtra("option",brand.getId_categoria());
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                webs.brandByCategory(CategoryActivity.this,CategoryActivity.this,brand);
            }
        });

        fillList();

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setColorFilter(ContextCompat.getColor(CategoryActivity.this,R.color.colorAccent));
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(CategoryActivity.this, CategoryActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    //} else {
                    //Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();
                    //}
            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(CategoryActivity.this, NewsActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
               // } else {
                    //Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  if (control.isOnline()) {
                if(LoginActivity.guestFlag == 1){
                    alertGuest();
                }
                else
                {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(CategoryActivity.this, FavoriteActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    //} else {
                    //Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();
                }
                //}

            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   if (control.isOnline()) {
                if(LoginActivity.guestFlag == 1){
                    alertGuest();
                }
                else{
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(CategoryActivity.this, HistoryActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

               // } else {
 //                   Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();
//
  //              }

            }
        });

    }

    public void fillList(){
        Category[] list = new Category[4];

        list[0] = new Category();
        list[0].setId(1);
        list[0].setName("Limusina");

        list[1] = new Category();
        list[1].setId(2);
        list[1].setName("Lujo");

        list[2] = new Category();
        list[2].setId(3);
        list[2].setName("Clasicos");

        list[3] = new Category();
        list[3].setId(4);
        list[3].setName("Suvs");

        for(int x=0; x<list.length;x++)
        {
            tipos.add(x,list[x]);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent setIntent = new Intent(this,CatalogCar.class);
        startActivity(setIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,LoginActivity.class);
        startActivity(setIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
/*
    @Override
    public void sendData(Brand[] obj) {
        {
            Toast.makeText(this, "paso"+obj.length, Toast.LENGTH_SHORT).show();
        }
    }
    */
    protected int getLayoutId() {
        return R.layout.activity_drawer;
    }

    public void fill_with_data(Publicidad[] list) {


        ListPublicidad listF = new ListPublicidad();
        for (int i = 0; i < list.length ; i ++){

            listF.setId(list[i].getId());
            listF.setNombre(list[i].getNombre());
            listF.setImage(R.drawable.auto);
            data.add(i,listF);
        }
    }

    @Override
    public void sendDataPublicidad(Publicidad[] obj) {
        final String URL = "http://kangup.com.mx/uploads/Publicidad/";
        imagenes_publicidad = new String[obj.length];
        for (int i = 0; i < obj.length; i++) {
            imagenes_publicidad[i] = new String();
                imagenes_publicidad[i] = (URL + obj[i].getNombre() + "." +obj[i].getFormato());

        }
        init();

    }




    private void init() {
        for(int i=0;i<imagenes_publicidad.length;i++)
            ImagesArray.add(imagenes_publicidad[i]);
            pagePublicidad.setAdapter(new SlidingImage_Adapter(CategoryActivity.this,ImagesArray));
             final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius


        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                pagePublicidad.setCurrentItem(currentPage++, true);
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
    }




    public void alertGuest() {
        new AlertDialog.Builder(CategoryActivity.this)
                .setTitle("Invitado")
                .setMessage("Gracias por visitar la aplicación de KangUp!! Para realizar la siguiente acción necesita estar registrado.")
                .setIcon(R.drawable.perfil_icon)
                .setPositiveButton("Registrar",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Intent setIntent = new Intent(CategoryActivity.this, RegisterActivity.class);
                                startActivity(setIntent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).show();
    }

}

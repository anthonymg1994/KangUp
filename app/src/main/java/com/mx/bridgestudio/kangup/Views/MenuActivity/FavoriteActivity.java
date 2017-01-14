package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdapterFavoriteList;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.DAO.DAOVehiculo;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendDetail;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendFavorites;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;

import java.util.ArrayList;

public class FavoriteActivity extends DrawerActivity implements OnDataSendFavorites,OnDataSendDetail ,AdapterView.OnItemClickListener{

    private ListView listFav;
    private ArrayList<ListCar> tipos = new ArrayList<>();
    private ArrayAdapter<ListCar> AdapterArray;
    private AdapterFavoriteList adaptador;
    protected DrawerLayout mDrawer;
    private webServices webs = new webServices();
    private SqliteController sql;
    private User user = new User();
    private Vehicle vehicle = new Vehicle();
    private String opcionSeleccionada ="";
    private DAOVehiculo Dvehicle = new DAOVehiculo();

    private Vehicle ve = new Vehicle();
    public static int id_vehiculo = 0;
    public static String nombre_vehiculo = "";
    ArrayList<ListCar> items= new ArrayList<>();
    Control control = new Control();

    public TextView emptyTest;
    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private DrawerActivity drw = new DrawerActivity();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_favorite);
        control.changeColorStatusBar(FavoriteActivity.this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_favorite, null, false);
        mDrawer.addView(contentView, 0);
        emptyTest =(TextView) findViewById(android.R.id.empty);
        //drw.setNameToolbar("Favoritos");

        listFav = (ListView)findViewById(R.id.listFav);
        adaptador = new AdapterFavoriteList(this,tipos);
        listFav.setAdapter(adaptador);

        getSupportActionBar().setTitle("Favoritos");

        sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
        sql.Connect();
        user = sql.user();
        sql.Close();
      //  if(control.isOnline()){
            webs.favsByUser(FavoriteActivity.this,FavoriteActivity.this,user);
        //}else{
//            Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

  //      }

        //listFav.setOnItemClickListener(this);
        listFav.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listFav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {

                int opcionSeleccionada = tipos.get(position).getId();
                id_vehiculo = opcionSeleccionada;
                nombre_vehiculo = tipos.get(position).getMarca() + " " + tipos.get(position).getModelo() + " " + tipos.get(position).getAnio();
                ve.setId(id_vehiculo);

                Toast.makeText(FavoriteActivity.this, "id_vehiculo = " + id_vehiculo
                        + " " + "nombre = "+ nombre_vehiculo , Toast.LENGTH_SHORT).show();
                webs.DetailVehicle(FavoriteActivity.this,FavoriteActivity.this,ve);
                //Intent intent = new Intent(CategoryActivity.this, CatalogCar.class);
                //SendToActivity.sendData(arrayBrands);
                //intent.putExtra("option",brand.getId_categoria());
                //startActivity(intent);

//                webs.brandByCategory(CategoryActivity.this,CategoryActivity.this,brand);
            }
        });

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent));
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(FavoriteActivity.this, CategoryActivity.class));
              //  } else {
                //    Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(FavoriteActivity.this, NewsActivity.class));
                //} else {
                  //  Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(FavoriteActivity.this, FavoriteActivity.class));
                //} else {
                 //   Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   if (control.isOnline()) {

                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(FavoriteActivity.this, HistoryActivity.class));
               // } else {
                 //   Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });

    }

    public void fillList(Vehicle[] vehicles){
        ListCar[] list = new ListCar[vehicles.length];
        for(int i = 0 ; i < vehicles.length ; i++){
            list[i] = new ListCar();
            list[i].setId(vehicles[i].getId());
            list[i].setMarca(vehicles[i].getMarca());
            list[i].setModelo(vehicles[i].getModel());
            list[i].setAnio(vehicles[i].getYear());
            // list[i].setFecha(listCar[i].getFecha());
            //list[i].setTotal(listCar[i].getTotal());
            //Cmbiar por imagen del servidor
            list[i].setImage(1);

            tipos.add(i,list[i]);
        }
        if(list.length<=0){
            setEmptyTest("No tiene favoritos : ( ");
        }
        adaptador.notifyDataSetChanged();

    }


    public void setEmptyTest(CharSequence text){
        emptyTest.setText(text);
    }
    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,LoginActivity.class);
        startActivity(setIntent);
        finish();
    }

    @Override
    public void sendDataFavorites(Vehicle[] obj) {
        fillList(obj);
    }

    @Override
    public void sendData(Vehicle obj) {
        ve = obj;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

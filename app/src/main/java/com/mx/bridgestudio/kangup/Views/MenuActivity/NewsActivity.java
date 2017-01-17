package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdapterNews;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendNews;
import com.mx.bridgestudio.kangup.Controllers.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.DividerItemDecoration;
import com.mx.bridgestudio.kangup.Models.Lists.ListNews;
import com.mx.bridgestudio.kangup.Models.News;
import com.mx.bridgestudio.kangup.Models.SampleDivider;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;

import java.util.ArrayList;

public class NewsActivity extends DrawerActivity implements OnDataSendNews {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    ArrayList<ListNews> items= new ArrayList<>();
    private ImageView imageNew;
    private webServices webs = new webServices();

    public static int id_noticia=0;
    public static String titulo = "";
    public static String desc = "";

    private News n;

    protected DrawerLayout mDrawer;
    Control control = new Control();

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;
    private DrawerActivity drw = new DrawerActivity();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_news);
        control.changeColorStatusBar(NewsActivity.this);


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_news, null, false);
        mDrawer.addView(contentView, 0);

       // drw.setNameToolbar("Noticias");
        getSupportActionBar().setTitle("Noticias");

       // if(control.isOnline()){
          webs.getAllNews(NewsActivity.this,this);
        //}else{
        //    Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();


        //}

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setColorFilter(ContextCompat.getColor(this,R.color.colorAccent));
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         //       if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(NewsActivity.this, CategoryActivity.class));
           //     } else {
             //       Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

               // }

            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(NewsActivity.this, NewsActivity.class));
              //  } else {
                //    Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   if (control.isOnline()) {
                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(NewsActivity.this, FavoriteActivity.class));
               // } else {
                 //   Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // if (control.isOnline()) {

                    finish(); // close this activity and return to preview activity (if there is any)
                    startActivity(new Intent(NewsActivity.this, HistoryActivity.class));
                //} else {
                  //  Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

                //}

            }
        });

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);

        recycler = (RecyclerView) findViewById(R.id.newsRecycler);
        recycler.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        final RecyclerView.ItemDecoration itemDecoration = new SampleDivider(this);
        recycler.addItemDecoration(itemDecoration);
        recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //Toast.makeText(getActi(), "position = " +items.get(position).getId(), Toast.LENGTH_SHORT).show();
                        int opcionSeleccionada = items.get(position).getId();
                        Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                        id_noticia = opcionSeleccionada;
                        //nombre_vehiculo = items.get(position).getMarca() + " " + items.get(position).getModelo() + " " + items.get(position).getAnio();
                        titulo = items.get(position).getTitle();
                        desc = items.get(position).getDescription();

                        Toast.makeText(NewsActivity.this, "titulo = " + titulo
                                + " " + "desc = "+ desc , Toast.LENGTH_SHORT).show();

                        //webs.DetailVehicle(this,getActivity(),vehicle);
                        //n = new News();
                        //n.setId(id_noticia);
                        //n.setTitulo(titulo);
                        //n.setDescripccion(desc);
                        //Bundle bundle = new Bundle();
                        //bundle.putSerializable("value", n);

                        //intent.putExtras(bundle);
                        startActivity(intent);

                        //  TabTop.this.startActivity(intent);
                        //finish();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        // Crear un nuevo adaptador
        adapter = new AdapterNews(NewsActivity.this,items);
        recycler.setAdapter(adapter);
    }

    public void fillList(News[] news){
        final String URL = "http://kangup.com.mx/uploads/Noticias/";
        final String URLDefault = "http://kangup.com.mx/uploads/Noticias/noticia_1.png";


        ListNews[] list = new ListNews[news.length];
        for(int i = 0 ; i < news.length ; i++){
            list[i] = new ListNews();
            list[i].setId(news[i].getId());
            list[i].setTitle(news[i].getTitulo());
            list[i].setDescription(news[i].getDescripccion());
            //list[i].setImage(news[i].getImagen());
            //Cmbiar por imagen del servidor
            if(news[i].getImagen() == null){
                list[i].setImage(URLDefault);
            }else{
                list[i].setImage(URL + news[i].getImagen());
            }
            items.add(i,list[i]);
        }
        adapter.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_drawer_drawer, menu);
        return true;
    }

    @Override
    public void sendDataNews(News[] obj) {
        Toast.makeText(this, "News"+obj.length, Toast.LENGTH_SHORT).show();
        fillList(obj);
    }
}

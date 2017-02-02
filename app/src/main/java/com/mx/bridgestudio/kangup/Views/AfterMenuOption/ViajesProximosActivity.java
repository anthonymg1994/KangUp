package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Adapters.AdapterReservacion;
import com.mx.bridgestudio.kangup.Adapters.AdapterRoutes;
import com.mx.bridgestudio.kangup.Controllers.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Models.*;
import com.mx.bridgestudio.kangup.Models.Lists.ListReservacion;
import com.mx.bridgestudio.kangup.Models.Lists.ListRoutes;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Models.Reservacion;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;

import java.util.ArrayList;

public class ViajesProximosActivity extends AppCompatActivity {

    private RecyclerView listViajes;
    private RecyclerView.LayoutManager lManagerViajes;
    public AdapterReservacion adapter;
    public ArrayList<ListReservacion> items = new ArrayList<>();

    private TextView emptyView;

    //toolbardown
    private ImageButton catalogo, favoritos, historial,noticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes_proximos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarViajes);
        setSupportActionBar(toolbar);

        emptyView = (TextView)findViewById(R.id.empty_view_viajes);

        //RecyclerView
        //Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);

        listViajes = (RecyclerView) findViewById(R.id.ViajesProxRecyclerView);

        //listRutas.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        listViajes.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManagerViajes = new LinearLayoutManager(this);
        listViajes.setLayoutManager(lManagerViajes);


        final RecyclerView.ItemDecoration itemDecorations = new SampleDivider(this);
        listViajes.addItemDecoration(itemDecorations);
        listViajes.addOnItemTouchListener(
                new RecyclerItemClickListener(this ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                    }


                })
        );

        adapter = new AdapterReservacion(items);
        listViajes.setAdapter(adapter);

        catalogo = (ImageButton) findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ViajesProximosActivity.this, CategoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        noticias = (ImageButton) findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ViajesProximosActivity.this, NewsActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        favoritos = (ImageButton) findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ViajesProximosActivity.this, FavoriteActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        historial = (ImageButton) findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(ViajesProximosActivity.this, HistoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


    }
}

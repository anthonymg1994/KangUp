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
import android.widget.ImageButton;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Models.News;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;

public class NewsDetailActivity extends DrawerActivity {

    private News news;
    protected DrawerLayout mDrawer;

    private TextView title,desc,fecha;
    Control control = new Control();

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private DrawerActivity drw =  new DrawerActivity();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        control.changeColorStatusBar(NewsDetailActivity.this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_news_detail, null, false);
        mDrawer.addView(contentView, 0);
        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  if (control.isOnline()) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(NewsDetailActivity.this, CategoryActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                //} else {
                //Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();
                //}
            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setColorFilter(ContextCompat.getColor(NewsDetailActivity.this,R.color.colorAccent));

        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   if (control.isOnline()) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(NewsDetailActivity.this, NewsActivity.class));
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
                    startActivity(new Intent(NewsDetailActivity.this, FavoriteActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

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
                    startActivity(new Intent(NewsDetailActivity.this, HistoryActivity.class));
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }
        });



        desc = (TextView)findViewById(R.id.bodyNews);
        fecha = (TextView)findViewById(R.id.date);
        desc.setText(NewsActivity.desc);
        fecha.setText(NewsActivity.fecha);
        getSupportActionBar().setTitle(NewsActivity.titulo);


    }
}

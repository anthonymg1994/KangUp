package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.News;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;

public class NewsDetailActivity extends DrawerActivity {

    private News news;
    protected DrawerLayout mDrawer;

    private TextView title,desc;

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;

    private DrawerActivity drw =  new DrawerActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_news_detail);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_news_detail, null, false);
        mDrawer.addView(contentView, 0);


        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(NewsDetailActivity.this, CategoryActivity.class));
            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(NewsDetailActivity.this, NewsActivity.class));
            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(NewsDetailActivity.this, FavoriteActivity.class));
            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(NewsDetailActivity.this, HistoryActivity.class));
            }
        });


        title = (TextView)findViewById(R.id.titleDetaiNews);
        desc = (TextView)findViewById(R.id.bodyNews);

        //Intent intent=this.getIntent();
        //Bundle bundle=intent.getExtras();

        //news = new News();

        //news=(News)bundle.getSerializable("value");

        title.setText(NewsActivity.titulo);
        desc.setText(NewsActivity.desc);
        //drw.setNameToolbar(NewsActivity.titulo);


    }
}

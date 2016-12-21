package com.mx.bridgestudio.kangup.Views.MenuActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.News;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;

public class NewsDetailActivity extends DrawerActivity {

    private News news;
    protected DrawerLayout mDrawer;

    private TextView title,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_news_detail);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.activity_news_detail, null, false);
        mDrawer.addView(contentView, 0);

        title = (TextView)findViewById(R.id.titleDetaiNews);
        desc = (TextView)findViewById(R.id.bodyNews);

        //Intent intent=this.getIntent();
        //Bundle bundle=intent.getExtras();

        //news = new News();

        //news=(News)bundle.getSerializable("value");

        title.setText(NewsActivity.titulo);
        desc.setText(NewsActivity.desc);


    }
}

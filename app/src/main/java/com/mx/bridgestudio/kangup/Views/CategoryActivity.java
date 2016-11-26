package com.mx.bridgestudio.kangup.Views;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.mx.bridgestudio.kangup.Adapters.AdapterCategory;
import com.mx.bridgestudio.kangup.Adapters.AdapterFavoriteList;
import com.mx.bridgestudio.kangup.Adapters.AndroidImageAdapter;
import com.mx.bridgestudio.kangup.Models.Category;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView list;
    private ArrayList<Category> tipos = new ArrayList<>();
    private AdapterCategory adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        AndroidImageAdapter adapterView = new AndroidImageAdapter(this);
        mViewPager.setAdapter(adapterView);

        list = (ListView)findViewById(R.id.listCategory);
        adaptador = new AdapterCategory(this,tipos);
        list.setAdapter(adaptador);
        fillList();

    }

    public void fillList(){
        Category list = new Category();
        list.setName("fff");

        for(int x=0; x<4;x++)
        {
            tipos.add(x,list);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,LoginActivity.class);
        startActivity(setIntent);
        finish();
    }
}

package com.mx.bridgestudio.kangup.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Adapters.AdaptadorType;
import com.mx.bridgestudio.kangup.Adapters.CardAdapter;
import com.mx.bridgestudio.kangup.Adapters.ViewPagerAdapter;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendCarXtype;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.SampleDivider;
import com.mx.bridgestudio.kangup.Models.Vehicle;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.tabs.TabTop;

import java.util.ArrayList;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by USUARIO on 24/10/2016.
 */

public class CarsXtype extends DrawerActivity implements
        AdapterView.OnItemClickListener,MaterialTabListener {

    MaterialTabHost tabHost;
    ViewPager viewPager;
    ViewPagerAdapterTab androidAdapter;

    protected DrawerLayout mDrawer;
    // private List items = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.content_types);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.typeofcar, null, false);
        mDrawer.addView(contentView, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(""+CardAdapter.marca);
        setSupportActionBar(toolbar);

        //tab host
        tabHost = (MaterialTabHost)findViewById(R.id.tabHost);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        //adapter view
        androidAdapter = new ViewPagerAdapterTab(getSupportFragmentManager(), CarsXtype.this);
        viewPager.setAdapter(androidAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int tabposition) {
                tabHost.setSelectedNavigationItem(tabposition);
                //androidAdapter.getItem(tabposition);
            }
        });

        //for tab position
        for (int i = 0; i < androidAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(androidAdapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,CatalogCar.class);
        startActivity(setIntent);
        finish();
    }


    @Override
    public void onTabSelected(MaterialTab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        //androidAdapter.getItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
    /*
    @Override
    public void sendData(Vehicle[] vehicle) {
        Toast.makeText(this, "string"+response, Toast.LENGTH_SHORT).show();
        for(int i = 0; i < vehicle.lenght(); i ++){
            items.add(i,vehicle[i]);
        }
         adapter.notifyDataSetChanged();
    }
    */

    // view pager adapter
    private class ViewPagerAdapterTab extends FragmentStatePagerAdapter {
        Context context;
        Fragment fragment = null;
        public ViewPagerAdapterTab(FragmentManager fragmentManager,Context c) {
            super(fragmentManager);
            this.context = c;
        }

        public Fragment getItem(int num) {
            switch (num){
                case 0: fragment = new TabTop();
                        break;
                case 1: fragment = new TabTop();
                        break;
                case 2: fragment = new TabTop();
                        break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int tabposition) {
            CharSequence tab;
            switch (tabposition)
            {
                case 0: tab = "Top";
                            break;
                case 1: tab = "Recomendados";
                            break;
                case 2: tab="Más votados";
                           break;
                default: tab ="";
            }
            return tab;
        }

    }




}

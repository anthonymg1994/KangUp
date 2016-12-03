package com.mx.bridgestudio.kangup.Controllers;

/**
 * Created by USUARIO on 25/10/2016.
 */

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.LoginActivity;
import com.mx.bridgestudio.kangup.Views.PaymentActivity;
import com.mx.bridgestudio.kangup.Views.ProfileActivity;
import com.mx.bridgestudio.kangup.Views.RecyclerItemClickListener;
import com.mx.bridgestudio.kangup.Views.TypesOfAutomobiles;


public class BaseActivity extends AppCompatActivity {
    /**
     * Instancia del drawer
     */
    private DrawerLayout drawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    /**
     * Titulo inicial del drawer
     */
    private String drawerTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar(); // Setear Toolbar como action bar

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Closing drawer on item click
                drawerLayout.closeDrawers();
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(),"Inbox Selected",Toast.LENGTH_SHORT).show();

                        return true;

                    // For rest of the options we just show a toast on click

                }
                return true;

            }
        });*/



        if (navigationView != null) {
            setupDrawerContent(navigationView);
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.setDrawerListener(drawerToggle);
            //getSupportActionBar().setHomeButtonEnabled(true);
            drawerToggle.syncState();

        }

        drawerTitle = getResources().getString(R.string.home_item);
        if (savedInstanceState == null) {
            selectItem(drawerTitle);
        }
        // mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //list = (ListView)findViewById(R.id.lis)

    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_share);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
         //   displayView(position);
            Toast msg = Toast.makeText(getBaseContext(),
                    "pósition"+position, Toast.LENGTH_SHORT);
            msg.show();
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                Intent setIntent = new Intent(BaseActivity.this,ProfileActivity.class);
                                startActivity(setIntent);
                                finish();
                                break;
                        }
                        // Marcar item presionado
                        menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        String title = menuItem.getTitle().toString();
                        selectItem(title);
                        return true;
                    }
                }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectItem(String title) {
        // Enviar título como arguemento del fragmento

//        drawerLayout.closeDrawers(); // Cerrar drawer

        setTitle(title); // Setear título actual

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }



}

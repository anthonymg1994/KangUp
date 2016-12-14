package com.mx.bridgestudio.kangup.Views.LeftSide;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.ProfileActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;

import org.w3c.dom.Text;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawer;
    private TextView name,email;
    private SqliteController sql;
    private User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(getLayoutId());
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //name = (TextView) findViewById(R.id.name);
        //email = (TextView) findViewById(R.id.email);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.bringToFront();
        drawer.requestLayout();

      //  getInformation();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_drawer_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_home){
            Intent setIntent = new Intent(this, CategoryActivity.class);
            startActivity(setIntent);
            finish();
        }else if (id == R.id.nav_profile) {
            //Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
            Intent setIntent = new Intent(this,ProfileActivity.class);
            startActivity(setIntent);
            finish();
            // Handle the camera action
        } else if (id == R.id.nav_pay) {

        } else if (id == R.id.nav_history) {
            Intent setIntent = new Intent(this, HistoryActivity.class);
            startActivity(setIntent);
            finish();
        } else if (id == R.id.nav_favorite) {
            Intent setIntent = new Intent(this, FavoriteActivity.class);
            startActivity(setIntent);
            finish();
        } else if(id == R.id.nav_news){

        } else if (id == R.id.nav_privacy) {

        } else if (id == R.id.nav_logout) {
            alertLogOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected int getLayoutId() {
        return R.layout.activity_drawer;
    }

    public void alertLogOut() {
        new AlertDialog.Builder(DrawerActivity.this)
                .setTitle("Cerrar sesion")
                .setMessage("¿Desea cerrar sesión?")
                .setIcon(R.drawable.ic_menu_manage)
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Intent setIntent = new Intent(DrawerActivity.this,LoginActivity.class);
                                startActivity(setIntent);
                                finish();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void getInformation(){
        sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
        sql.Connect();
        user = sql.user();
        sql.Close();

    //    name.setText(user.getFirstName() +" "+ user.getLastName());
     //   email.setText(user.getEmail());

    }
}

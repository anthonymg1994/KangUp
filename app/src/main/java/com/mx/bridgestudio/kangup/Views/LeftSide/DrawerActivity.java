package com.mx.bridgestudio.kangup.Views.LeftSide;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.SessionManager;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.AfterMenuOption.ViajesProximosActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.PaymentActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.ProfileActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;
import com.squareup.picasso.Picasso;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.RegisterActivity;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawer;
    private TextView name,email;
    private ImageView profile_photo;
    private SqliteController sql;
    private User user = new User();
    private Control control = new Control();
    public static int flag=0;
    public static String title="";
    SessionManager session;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(getLayoutId());
        super.onCreate(savedInstanceState);


        session = new SessionManager(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        session = new SessionManager(this);


        //ivCloseDrawer = (ImageView) lV.findViewById(R.id.imageView);

       // ivCloseDrawer.setOnClickListener(new View.OnClickListener()


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.bringToFront();

        View header = navigationView.inflateHeaderView(R.layout.nav_header_drawer);

        name = (TextView) header.findViewById(R.id.namedrawer);
       // email = (TextView) header.findViewById(R.id.emaildrawer);
        profile_photo = (ImageView) header.findViewById(R.id.imageProfilee);


        drawer.requestLayout();

        getInformation();
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
            //if(control.isOnline()){
                Intent setIntent = new Intent(this, CategoryActivity.class);
                startActivity(setIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();

          //  }else{
              //  Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

        //    }

        }else if (id == R.id.nav_profile) {
            //Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
      //      if(control.isOnline()){
            if(LoginActivity.guestFlag==1)
            {
                alertGuest();
            }
            else{
                Intent setIntent = new Intent(this,ProfileActivity.class);
                startActivity(setIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
    //        }else{
  //              Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

//            }

            // Handle the camera action
        } else if (id == R.id.nav_pay) {
            //if(control.isOnline()){
            if(LoginActivity.guestFlag==1)
            {
                alertGuest();
            }
            else{
                Intent setIntent = new Intent(this, PaymentActivity.class);
                startActivity(setIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
            //}else{
              //  Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

            //}


        } else if (id == R.id.nav_history) {
          //  if(control.isOnline()){
            if(LoginActivity.guestFlag==1)
            {
                alertGuest();
            }
            else {
                Intent setIntent = new Intent(this, HistoryActivity.class);
                startActivity(setIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        //    }else{
      //          Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

    //        }

        } else if (id == R.id.nav_favorite) {
           // (()) if(control.isOnline()){
            if(LoginActivity.guestFlag==1)
            {
                alertGuest();
            }
            else{
                Intent setIntent = new Intent(this, FavoriteActivity.class);
                startActivity(setIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }

            //}else{
  //            /  Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();




        } else if(id == R.id.nav_news){
            //if(control.isOnline()){
                Intent setIntent = new Intent(this, NewsActivity.class);
                startActivity(setIntent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
           // }else{
//                Toast.makeText(getApplicationContext(),"Verifica tu conexion",Toast.LENGTH_SHORT).show();

       //     }


        } else if (id == R.id.nav_privacy) {

        } else if (id == R.id.nav_logout) {
            if(LoginActivity.guestFlag==1)
            {
                alertLogOutGuest();
            }
            else{
                alertLogOut();
            }
        }
        else if(id == R.id.nav_viajes){
            if(LoginActivity.guestFlag==1)
            {
                alertGuest();
            }
            else{
                Intent setIntent = new Intent(this, ViajesProximosActivity.class);
                startActivity(setIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
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
                .setIcon(R.drawable.ic_close_light)
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                session.logoutUser();
                                Intent setIntent = new Intent(DrawerActivity.this,LoginActivity.class);
                                startActivity(setIntent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

    public void alertLogOutGuest() {
        new AlertDialog.Builder(DrawerActivity.this)
                .setTitle("Invitado")
                .setMessage("¿Desea salir del modo invitado?")
                .setIcon(R.drawable.ic_close_light)
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                session.logoutUser();
                                Intent setIntent = new Intent(DrawerActivity.this,LoginActivity.class);
                                startActivity(setIntent);

                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

        if(LoginActivity.guestFlag==1)
        {
            name.setText("Invitado");

        }
        else{
            sql = new SqliteController(getApplicationContext(), "kangup",null, 1);
            sql.Connect();
            user = sql.user();
            sql.Close();
            Picasso.with(DrawerActivity.this).load(user.getPhoto()).into(profile_photo);
            name.setText(user.getFirstName() +" "+ user.getAp_paterno() + " " + user.getAp_materno());
        }

    }

    public void setNameToolbar(String name){
        toolbar.setTitle(name);
    }


    public void alertGuest() {
        new AlertDialog.Builder(DrawerActivity.this)
                .setTitle("Invitado")
                .setMessage("Gracias por visitar la aplicación de KangUp!! Para realizar la siguiente acción necesita estar registrado.")
                .setIcon(R.drawable.perfil_icon)
                .setPositiveButton("Registrar",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Intent setIntent = new Intent(DrawerActivity.this, RegisterActivity.class);
                                startActivity(setIntent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).show();
    }

}

package com.mx.bridgestudio.kangup.Views.AfterMenuOption.Fin_De_Viaje;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by USUARIO on 25/01/2017.
 */

public class terminaViaje extends DrawerActivity {
    protected DrawerLayout mDrawer;
    Control control = new Control();
    RatingBar ratingBar_socio, ratingBar_viaje;
    TextView nombre_socio, fecha,hora_inicio,hora_termino,total;
    Button enviar;
    float rating_socio=0;
    float rating_viaje=0;
    private ImageView profile_photo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_detalle);
        //   vehicle.setId(id_vehiculo);
        //  webs.DetailVehicle(this,this,vehicle);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        control.changeColorStatusBar(terminaViaje.this);




        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.black_back, null, false);
        mDrawer.addView(contentView, 0);

        Dialog_Puntuacion();


    }

    public void init(View v){


        ratingBar_socio = (RatingBar) v.findViewById(R.id.stars_chofer);
        ratingBar_viaje = (RatingBar) v.findViewById(R.id.stars_viaje);
        nombre_socio = (TextView)v.findViewById(R.id.conductor_nombre);
        fecha = (TextView)v.findViewById(R.id.fecha_);
        hora_inicio = (TextView)v.findViewById(R.id.hora_inicio);
        hora_termino = (TextView)v.findViewById(R.id.hora_termino);
        total = (TextView)v.findViewById(R.id.total);
        profile_photo = (ImageView)v.findViewById(R.id.imageProfilee);



        ratingBar_socio.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                float rating_socio = ratingBar_socio.getRating();
            }
        });

        ratingBar_viaje.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                float rating_viaje = ratingBar_viaje.getRating();

            }
        });


        fecha.setHintTextColor(this.getResources().getColor(R.color.textoHint));
        hora_inicio.setHintTextColor(this.getResources().getColor(R.color.textoHint));
        hora_termino.setHintTextColor(this.getResources().getColor(R.color.textoHint));


        Drawable drawable = ratingBar_socio.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#ffcc00"), PorterDuff.Mode.SRC_ATOP);

        Drawable drawables = ratingBar_viaje.getProgressDrawable();
        drawables.setColorFilter(Color.parseColor("#ffcc00"), PorterDuff.Mode.SRC_ATOP);

    }

    public void fill_fields(){
        final String URL = "http://kangup.com.mx/uploads/Foto_perfil_socio/";

        nombre_socio.setText("Juanito simon");
        fecha.setText("Lunes ,24 de diciembre");
        hora_inicio.setText("14:00");
        hora_termino.setText("17:00");
        total.setText("$ "+ "1000.00");
        Picasso.with(this).load(URL+"socio_1.jpg").into(profile_photo);

    }
    public void Dialog_Puntuacion() {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(terminaViaje.this,R.style.MyDialogTheme);
        View root = ((LayoutInflater) terminaViaje.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_termino_viaje, null);
        dialogBuilder.setView(root);

        init(root);
        fill_fields();
        dialogBuilder.setTitle("");
        dialogBuilder.setMessage("Termina tu viaje");
        dialogBuilder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                //com.mx.bridgestudio.kangup.Models.Reservacion res = new com.mx.bridgestudio.kangup.Models.Reservacion();


                if(rating_socio == 0 || rating_viaje == 0){
                    Toast.makeText(getApplicationContext(),"Nuestra aplicacion necesita de tu opinion, califica tu viaje",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Tu puntuacion de socio:" + rating_socio + "Tu puntacion de viaje : "+ rating_viaje,Toast.LENGTH_SHORT).show();
                    //Send puntuacion con servicio web a servidor
                    Toast.makeText(getApplicationContext(),"Gracias por tu preferencia",Toast.LENGTH_SHORT).show();
                }


            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }


}

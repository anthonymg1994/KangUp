package com.mx.bridgestudio.kangup.Adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.Payment;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;

import java.util.ArrayList;

/**
 * Created by Isaac on 09/11/2016.
 */

public class AdapterFavoriteList extends ArrayAdapter<ListCar> implements View.OnClickListener {

    Activity context;
    private ArrayList<ListCar> list;
    private ListCar[] objects;
    private View listView;

    public AdapterFavoriteList(Activity context, ArrayList<ListCar> list) {
        super(context, R.layout.favorite_list, list);
        // TODO Auto-generated constructor stub
        this.context = (Activity) context;
        this.list = list;
    }

    @Override
    public View getView(final int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        View item = null;
        ImageView imgImg = null;
        ImageButton img = null;


        if (item == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.favorite_list, null);
            imgImg = (ImageView)item.findViewById(R.id.imageCar);
            img = (ImageButton) item.findViewById(R.id.starButton);
            img.setOnClickListener(this);
            img.setTag(arg0);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertConfirmacion();
                    Toast.makeText(context, "Eliminado"+list.get(arg0).getId(), Toast.LENGTH_LONG).show();
                    int xy =(Integer) v.getTag();

                }
            });
            TextView Nnom = (TextView) item.findViewById(R.id.titleCar);
            Nnom.setText("" + list.get(arg0).getModelo()+ " " + list.get(arg0).getAnio());
            imgImg.setImageResource(R.drawable.auto);


        }
        return item;

    }

    @Override
    public void onClick(View v) {

    }
    public void alertConfirmacion() {
        new AlertDialog.Builder(context)
                .setTitle("Confirmacion")
                .setMessage("¿ Deseas eliminarlo de favoritos ?")
                .setIcon(R.drawable.ic_menu_manage)
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {

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
}

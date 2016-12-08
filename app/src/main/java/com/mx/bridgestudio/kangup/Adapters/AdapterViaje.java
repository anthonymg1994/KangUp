package com.mx.bridgestudio.kangup.Adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.Lists.ListViaje;
import com.mx.bridgestudio.kangup.Models.Payment;
import com.mx.bridgestudio.kangup.Models.RoadTrip;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 07/12/2016.
 */

public class AdapterViaje extends RecyclerView.Adapter<AdapterViaje.AnimeViewHolder>  {
    private List<ListViaje> items;

    public AdapterViaje(List<ListViaje> items) {
        this.items = items;
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    @Override
    public AdapterViaje.AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.history_list, viewGroup, false);
        return new AdapterViaje.AnimeViewHolder(v);
    }

    public Resources getResources() {
        Resources resources = null;
        return resources;
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView marca;
        public TextView modelo;
        public TextView anio;
        public TextView fecha;
        public TextView total;

        public AnimeViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.image_type);
            marca = (TextView) v.findViewById(R.id.ModeloCar);
            fecha = (TextView) v.findViewById(R.id.fecha);
            total = (TextView) v.findViewById(R.id.total);
           // fav = (ImageButton) v.findViewById(R.id.starButton);
            // fav.setOnClickListener(this);
        }
    }


    @Override
    public void onBindViewHolder(AdapterViaje.AnimeViewHolder viewHolder, int i) {
        // viewHolder.imagen.setImageResource(items.get(i).getImage());

        viewHolder.imagen.setImageResource(R.drawable.auto);
        viewHolder.marca.setText(items.get(i).getMarca()+" "+items.get(i).getModelo()+" "+items.get(i).getYear()+" ");
        viewHolder.fecha.setText(items.get(i).getFecha());
        viewHolder.total.setText(items.get(i).getTotal());
    }

}


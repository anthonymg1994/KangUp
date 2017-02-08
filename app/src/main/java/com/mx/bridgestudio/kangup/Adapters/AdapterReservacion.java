package com.mx.bridgestudio.kangup.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Lists.ListReservacion;
import com.mx.bridgestudio.kangup.Models.Lists.ListViaje;
import com.mx.bridgestudio.kangup.R;

import java.util.List;

/**
 * Created by Isaac on 01/02/2017.
 */

public class AdapterReservacion extends RecyclerView.Adapter<AdapterReservacion.AnimeViewHolder> {

    private List<ListReservacion> items;

    public AdapterReservacion(List<ListReservacion> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    @Override
    public AdapterReservacion.AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viajes_proximos_list, viewGroup, false);
        return new AdapterReservacion.AnimeViewHolder(v);
    }

    public Resources getResources() {
        Resources resources = null;
        return resources;
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView auto;
        public TextView fecha;
        public TextView total;

        public AnimeViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imageView4);
            auto = (TextView) v.findViewById(R.id.autoName);
            fecha = (TextView) v.findViewById(R.id.horarios);
            total = (TextView) v.findViewById(R.id.total);
            // fav = (ImageButton) v.findViewById(R.id.starButton);
            // fav.setOnClickListener(this);
        }
    }


    @Override
    public void onBindViewHolder(AdapterReservacion.AnimeViewHolder viewHolder, int i) {
        // viewHolder.imagen.setImageResource(items.get(i).getImage());
        viewHolder.auto.setText(items.get(i).getAuto()+" "+items.get(i).getModelo()+" "+items.get(i).getYear()+" ");
        viewHolder.fecha.setText(items.get(i).getDate()+ "  "+ items.get(i).getHourI()+ " - "+ items.get(i).getHourF());
        viewHolder.total.setText("");
    }

    public ListReservacion getItem(int position){
        return items.get(position);
    }
}

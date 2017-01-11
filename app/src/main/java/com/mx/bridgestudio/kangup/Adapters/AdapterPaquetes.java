package com.mx.bridgestudio.kangup.Adapters;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Lists.ListPaquetes;
import com.mx.bridgestudio.kangup.Models.Lists.ListPaymentForm;
import com.mx.bridgestudio.kangup.Models.Lists.ListRoutes;
import com.mx.bridgestudio.kangup.R;

import java.util.List;

/**
 * Created by Isaac on 05/01/2017.
 */

public class AdapterPaquetes extends RecyclerView.Adapter<AdapterPaquetes.AnimeViewHolder> {

    private List<ListPaquetes> items;

    public AdapterPaquetes(List<ListPaquetes> items) {
        this.items = items;
    }

    @Override

    public int getItemCount() {
        return items.size();
    }

    @Override
    public AdapterPaquetes.AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.paquetes_list, viewGroup, false);
        return new AdapterPaquetes.AnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterPaquetes.AnimeViewHolder holder, int i) {
        holder.nombre.setText(items.get(i).getNombre());
        holder.descripcion.setText(items.get(i).getDescripcion());
        holder.precio.setText("$ "+items.get(i).getPrecio());
    }

    public Resources getResources() {
        Resources resources = null;
        return resources;
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView descripcion;
        public TextView precio;
        public AnimeViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombrePaquete);
            descripcion = (TextView) v.findViewById(R.id.descripcionPaquete);
            precio = (TextView) v.findViewById(R.id.precioPaquete);
        }
    }

}

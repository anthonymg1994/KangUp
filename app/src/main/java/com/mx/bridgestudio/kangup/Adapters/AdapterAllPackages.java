package com.mx.bridgestudio.kangup.Adapters;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Lists.ListPaquetes;
import com.mx.bridgestudio.kangup.R;

import java.util.List;

/**
 * Created by Isaac on 12/01/2017.
 */

public class AdapterAllPackages extends RecyclerView.Adapter<AdapterPaquetes.AnimeViewHolder>{

    private List<ListPaquetes> items;

    public AdapterAllPackages(List<ListPaquetes> items) {
        this.items = items;
    }

    @Override

    public int getItemCount() {
        return items.size();
    }

    @Override
    public AdapterPaquetes.AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.paquetes_recycler, viewGroup, false);
        return new AdapterPaquetes.AnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterPaquetes.AnimeViewHolder holder, int i) {
        holder.nombre.setText(items.get(i).getNombre());
        holder.precio.setText("$ "+items.get(i).getPrecio());
    }

    public Resources getResources() {
        Resources resources = null;
        return resources;
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public CheckBox checked;
        public TextView nombre;
        public TextView precio;
        public AnimeViewHolder(View v) {
            super(v);
            checked = (CheckBox)v.findViewById(R.id.packChecked);
            nombre = (TextView) v.findViewById(R.id.nombrePaqueteRecycler);
            precio = (TextView) v.findViewById(R.id.nombreArt);
        }
    }
}

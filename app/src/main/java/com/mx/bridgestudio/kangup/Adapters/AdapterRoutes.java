package com.mx.bridgestudio.kangup.Adapters;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Lists.ListRoutes;
import com.mx.bridgestudio.kangup.R;

import java.util.List;

/**
 * Created by Isaac on 04/01/2017.
 */

public class AdapterRoutes extends RecyclerView.Adapter<AdapterRoutes.AnimeViewHolder> {

    private List<ListRoutes> items;

    public AdapterRoutes(List<ListRoutes> items) {
        this.items = items;
    }

    @Override

    public int getItemCount() {
        return items.size();
    }

    @Override
    public AdapterRoutes.AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.routes_list, viewGroup, false);
        return new AnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AnimeViewHolder holder, int i) {
        holder.origen.setText(items.get(i).getOrigen());
        holder.destino.setText(items.get(i).getDestiny());
    }

    public Resources getResources() {
        Resources resources = null;
        return resources;
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView origen;
        public TextView destino;
        public AnimeViewHolder(View v) {
            super(v);
            origen = (TextView) v.findViewById(R.id.originTitle);
            destino = (TextView) v.findViewById(R.id.destinyTitle);
            // fav.setOnClickListener(this);
        }
    }

}

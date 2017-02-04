package com.mx.bridgestudio.kangup.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Lists.ListArticles;
import com.mx.bridgestudio.kangup.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Isaac on 12/01/2017.
 */

public class AdapterArticle extends RecyclerView.Adapter<AdapterArticle.AnimeViewHolder> {

    private List<ListArticles> items;
    Context context;

    public AdapterArticle(List<ListArticles> items,Context context) {
        this.items = items;
        this.context = context;
    }

    @Override

    public int getItemCount() {
        return items.size();
    }

    @Override
    public AdapterArticle.AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.articulo_recycler, viewGroup, false);
        return new AdapterArticle.AnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterArticle.AnimeViewHolder holder, int i) {
        holder.nombre.setText(items.get(i).getNombre());
        holder.descripcion.setText(items.get(i).getDescripcion());
        Picasso.with(context).load(items.get(i).getFoto()).into(holder.image);

    }

    public Resources getResources() {
        Resources resources = null;
        return resources;
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView descripcion;

        public ImageView image;
        public AnimeViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombreArt);
            descripcion = (TextView) v.findViewById(R.id.descripcionArticulo);
            image = (ImageView)v.findViewById(R.id.imageView4);
        }
    }

}

package com.mx.bridgestudio.kangup.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Lists.ListNews;
import com.mx.bridgestudio.kangup.Models.News;
import com.mx.bridgestudio.kangup.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Isaac on 20/12/2016.
 */

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.AnimeViewHolder> {

    private static Context mContext;
    private List<ListNews> items;

    public AdapterNews(Context mContext ,List<ListNews> items) {
        this.items = items;
        this.mContext = mContext;
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    @Override
    public AdapterNews.AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_recycler_cell, viewGroup, false);
        return new AnimeViewHolder(v);
    }

    public Resources getResources() {
        Resources resources = null;
        return resources;
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView descripcion;

        public AnimeViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imageNews);
            nombre = (TextView) v.findViewById(R.id.newsTitle);
            descripcion = (TextView) v.findViewById(R.id.newsDescription);

            // fav.setOnClickListener(this);
        }
    }
    @Override
    public void onBindViewHolder(AdapterNews.AnimeViewHolder viewHolder, int i) {
        //viewHolder.imagen.setImageResource(items.get(i).getImage());

        viewHolder.imagen.setImageResource(R.drawable.perfil2);
        viewHolder.nombre.setText(items.get(i).getTitle());
        viewHolder.descripcion.setText(String.valueOf(items.get(i).getDescription()));
        Picasso.with(mContext).load(items.get(i).getImage()).into(viewHolder.imagen);

    }

}

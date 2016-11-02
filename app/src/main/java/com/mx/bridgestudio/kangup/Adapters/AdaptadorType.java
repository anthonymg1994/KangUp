package com.mx.bridgestudio.kangup.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.ListCar;
import com.mx.bridgestudio.kangup.R;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Anthony on 02/11/2016.
 */
public class AdaptadorType extends RecyclerView.Adapter<AdaptadorType.AnimeViewHolder>  {
    private List<Class> items;

    public AdaptadorType(List<ListCar> items) {
        this.items = items;
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.celltipo, viewGroup, false);
        return new AnimeViewHolder(v);
    }
    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView descripcion;
        public AnimeViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.image_type);
            nombre = (TextView) v.findViewById(R.id.name);
            descripcion = (TextView) v.findViewById(R.id.description);
        }
    }
    @Override
    public void onBindViewHolder(AnimeViewHolder viewHolder, int i) {
       // viewHolder.imagen.setImageResource(items.get(i).getImage());
        viewHolder.imagen.setImageResource(R.drawable.ic_menu_camera);
        viewHolder.nombre.setText(items.get(i).getName());
        viewHolder.descripcion.setText(String.valueOf(items.get(i).getDescription()));
    }

}

package com.mx.bridgestudio.kangup.Adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.User;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.LoginActivity;
import com.mx.bridgestudio.kangup.Views.PaginasInicio.RegisterActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Anthony on 02/11/2016.
 */
public class AdaptadorType extends RecyclerView.Adapter<AdaptadorType.AnimeViewHolder> implements View.OnClickListener{
    private List<ListCar> items;
    private ArrayList<ListCar> arraylist;

    Activity context;
    webServices web = new webServices();
    public AdaptadorType(Activity context,List<ListCar> items) {
        this.items = items;
        this.context = context;
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

    public Resources getResources() {
        Resources resources = null;
        return resources;
    }

    @Override
    public void onClick(View view) {

    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView descripcion;
        public ImageButton fav;
        public AnimeViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.image_type);
            nombre = (TextView) v.findViewById(R.id.namedrawer);
            descripcion = (TextView) v.findViewById(R.id.description);
            fav = (ImageButton) v.findViewById(R.id.starButton);
            fav.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }
    @Override
    public void onBindViewHolder(AnimeViewHolder viewHolder, final int i) {
       // viewHolder.imagen.setImageResource(items.get(i).getImage());


        Picasso.with(context).load(items.get(i).getImage()).into(viewHolder.imagen);

        viewHolder.nombre.setText(items.get(i).getModelo());
        viewHolder.descripcion.setText(String.valueOf(items.get(i).getModelo()+" "+items.get(i).getAnio()));

        viewHolder.fav.setFocusable(false);
        viewHolder.fav.setFocusableInTouchMode(false);
        //img.setOnClickListener(this);
        viewHolder.fav.setTag(i);

        SqliteController sql = new SqliteController(context, "kangup", null, 1);
        sql.Connect();
        final User user = sql.user();
        sql.Close();

        viewHolder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LoginActivity.guestFlag==1){
                    alertGuest();
                }
                else {
                    alertConfirmacion(items.get(i).getId(),user.getId());
                    Toast.makeText(context, "Eliminado veh "+items.get(i).getId(), Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "Eliminado user "+user.getId(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void alertConfirmacion(final int veh, final int user) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmacion")
                .setMessage("¿ Deseas agregarlo a favoritos ?")
                .setIcon(R.drawable.ic_menu_manage)
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                web.addFav(context,veh,user);
                                Toast.makeText(context, "Favorito "+ veh + " " + user, Toast.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void alertGuest() {
        new AlertDialog.Builder(context)
                .setTitle("Invitado")
                .setMessage("Gracias por visitar la aplicación de KangUp!! Para realizar la siguiente acción necesita estar registrado.")
                .setIcon(R.drawable.perfil_icon)
                .setPositiveButton("Registrar",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Intent setIntent = new Intent(context, RegisterActivity.class);
                                context.startActivity(setIntent);
                                context.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arraylist);
        } else {
            for (ListCar wp : arraylist) {
                if (wp.getModelo().toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}

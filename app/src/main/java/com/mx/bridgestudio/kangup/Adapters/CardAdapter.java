package com.mx.bridgestudio.kangup.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mx.bridgestudio.kangup.Models.ListCar;
import com.mx.bridgestudio.kangup.R;

import java.util.List;

/**
 * Created by Anthony on 09/11/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private Context mContext;
    private List<ListCar> albumList;
    private View v;
    public ClipData.Item currentItem;
    public CardView cardView;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public ListCar list;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            cardView = (CardView) view.findViewById(R.id.card_view_catalogo);




        }
    }
    public CardAdapter(Context mContext, List<ListCar> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }
    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_places_cars, parent, false);
        return new CardAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final CardAdapter.MyViewHolder holder, final int position) {
        final ListCar album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText(1+"adsd");
        // loading album cover using Glide library
        //Revisar como funciona esta libreria para integrarle las imagens correspondientes a cada cardvies
        Glide.with(mContext).load(R.drawable.auto).into(holder.thumbnail);
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Position"+album.getName(), Toast.LENGTH_SHORT).show();
                showPopupMenu(holder.overflow);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Position"+album.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Position"+album.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_cars, popup.getMenu());
        popup.setOnMenuItemClickListener(new CardAdapter.MyMenuItemClickListener());
        popup.show();
    }
    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Agregar a favorito", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    //Revisar que poner en este campo
                    Toast.makeText(mContext, "Recomendar", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
    @Override
    public int getItemCount() {
        return albumList.size();
    }

}

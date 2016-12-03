package com.mx.bridgestudio.kangup.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.Models.Payment;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;

/**
 * Created by Isaac on 09/11/2016.
 */

public class AdapterFavoriteList extends ArrayAdapter<ListCar> {

    Activity context;
    private ArrayList<Payment> lista;
    private ListCar[] objects;
    private View listView;

    public AdapterFavoriteList(Activity context, ArrayList<ListCar> list) {
        super(context, R.layout.favorite_list, list);
        // TODO Auto-generated constructor stub
        this.context = (Activity) context;
        this.lista = lista;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        View item = null;
        ImageView imgImg = null;
        ImageButton img = null;

        if (item == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.favorite_list, null);
            imgImg = (ImageView)item.findViewById(R.id.imageCar);
            img = (ImageButton) item.findViewById(R.id.starButton);
            TextView Nnom = (TextView) item.findViewById(R.id.titleCar);

        }
        return item;

    }
}

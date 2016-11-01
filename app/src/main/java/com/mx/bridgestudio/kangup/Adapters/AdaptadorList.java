package com.mx.bridgestudio.kangup.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Models.ListCar;

import java.util.ArrayList;

/**
 * Created by USUARIO on 25/10/2016.
 */

public class AdaptadorList extends ArrayAdapter<ListCar> {
    Activity context;
    private ArrayList<ListCar> lista;
    private ListCar[] objects;
    private View listView;

    public AdaptadorList(Activity context, int e, ArrayList<ListCar> list) {
        super(context, R.layout.celltype, list);
        // TODO Auto-generated constructor stub
        this.context = (Activity) context;
        this.lista = lista;
    }
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        View item = null;
        ImageView imgImg = null;
        if (item == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.celltype, null);
            TextView Nnom = (TextView) item.findViewById(R.id.name);
            TextView des = (TextView) item.findViewById(R.id.description);

            }
        return item;

    }


}

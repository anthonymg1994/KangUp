package com.mx.bridgestudio.kangup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Category;
import com.mx.bridgestudio.kangup.Models.ListCar;
import com.mx.bridgestudio.kangup.Models.Payment;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;

/**
 * Created by Isaac on 25/11/2016.
 */

public class AdapterCategory extends ArrayAdapter<Category> {

    Activity context;
    private ArrayList<Category> list;
    private Category[] objects;
    private View listView;

    public AdapterCategory(Activity context, ArrayList<Category> list) {
        super(context, R.layout.category_list, list);
        // TODO Auto-generated constructor stub
        this.context = (Activity) context;
        this.list = list;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        View item = null;
        ImageView imgImg = null;
        //TextView txt= null;

        if (item == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.category_list, null);
            //txt = (TextView)item.findViewById(R.id.txtcat);
            if(arg0==0){
                imgImg = (ImageView) item.findViewById(R.id.imageCateg);
                imgImg.setImageResource(R.drawable.clasicos);
            }
            if(arg0==1) {
                imgImg = (ImageView) item.findViewById(R.id.imageCateg);
                imgImg.setImageResource(R.drawable.limusinas);
            }
            if(arg0==2) {
                imgImg = (ImageView) item.findViewById(R.id.imageCateg);
                imgImg.setImageResource(R.drawable.suvs);
            }
            if(arg0==3){
                imgImg = (ImageView) item.findViewById(R.id.imageCateg);
                imgImg.setImageResource(R.drawable.lujo);
            }
        }
        return item;

    }

}

package com.mx.bridgestudio.kangup.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Category;
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
        ImageButton image;
        TextView tipo,autocompleteTxt;
        //TextView txt= null;
        int flag = 0;
        if (item == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.category_list, null);
            //txt = (TextView)item.findViewById(R.id.txtcat);

            imgImg = (ImageView) item.findViewById(R.id.imageCateg);
            image = (ImageButton) item.findViewById(R.id.imageButtonsIG);
            tipo = (TextView) item.findViewById(R.id.txttipo);
            autocompleteTxt = (TextView)  item.findViewById(R.id.textView5);



            if(arg0==0){
                imgImg.setImageResource(R.drawable.limusinacat);
                flag = 1;
                autocompleteTxt.setText("VER TODAS LAS");
                tipo.setText("LIMUSIONAS");
            }
            if(arg0==1) {

                imgImg.setImageResource(R.drawable.lujocat);
                flag =2;
                autocompleteTxt.setText("VER TODOS LOS");
                tipo.setText("AUTOS DE LUJO");
            }
            if(arg0==2) {
                imgImg.setImageResource(R.drawable.clasicocat);
                autocompleteTxt.setText("VER TODAS LOS");
              flag = 3;
                tipo.setText("AUTOS CLASICOS");
            }
            if(arg0==3){
                imgImg.setImageResource(R.drawable.suvscat);
                autocompleteTxt.setText("VER TODAS LAS");
                flag = 4;
                tipo.setText("SUBS O CAMIONETAS");
            }
            image.setFocusable(false);

          
        }
        return item;

    }

}

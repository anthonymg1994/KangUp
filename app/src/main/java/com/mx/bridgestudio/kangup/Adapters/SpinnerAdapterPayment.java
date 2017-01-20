package com.mx.bridgestudio.kangup.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mx.bridgestudio.kangup.Models.Lists.ListPaymentForm;
import com.mx.bridgestudio.kangup.Models.User;

import java.util.ArrayList;

/**
 * Created by Isaac on 20/01/2017.
 */

public class SpinnerAdapterPayment  extends ArrayAdapter<ListPaymentForm> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private ListPaymentForm[] values;

    public SpinnerAdapterPayment(Context context, int textViewResourceId,
                       ListPaymentForm[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
        return values.length;
    }

    public ListPaymentForm getItem(int position){
        return values[position];
    }

    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.WHITE);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values[position].getNum_cuenta());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getNum_cuenta());

        return label;
    }
}

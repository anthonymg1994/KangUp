package com.mx.bridgestudio.kangup.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by HP on 4/4/2016.
 */

public class AndroidFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        // tab page content
        textView.setText("");
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
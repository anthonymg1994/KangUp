package com.mx.bridgestudio.kangup.Views.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mx.bridgestudio.kangup.R;

/**
 * Created by Isaac on 06/12/2016.
 */

public class TabTop extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_top,container,false);
        return v;
    }

}

package com.mx.bridgestudio.kangup.Views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.mx.bridgestudio.kangup.R;

/**
 * Created by USUARIO on 24/10/2016.
 */

public class TypesOfAutomobiles extends Activity {

    private ListView lista;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types);
        lista = (ListView)findViewById(R.id.listView);


    }

}

package com.mx.bridgestudio.kangup.Views;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.mx.bridgestudio.kangup.Adapters.CardAdapter;
import com.mx.bridgestudio.kangup.Controllers.BaseActivity;
import com.mx.bridgestudio.kangup.Models.ListCar;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;

/**
 * Created by Anthony on 09/11/2016.
 */

public class RecyclerView_Activity extends BaseActivity {
    private static RecyclerView recyclerView;

    //String and Integer array for Recycler View Items
    public static final String[] TITLES= {"Hood","Full Sleeve Shirt","Shirt","Jean Jacket","Jacket"};
    public static final Integer[] IMAGES= {R.drawable.auto,R.drawable.auto,R.drawable.auto,R.drawable.auto,R.drawable.auto,};


    private static String navigateFrom;//String to get Intent Value

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        initViews();
        populatRecyclerView();
    }

    // Initialize the view
    private void initViews() {
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Set Back Icon on Activity
        navigateFrom = getIntent().getStringExtra("navigateFrom");//Get Intent Value in String
        recyclerView = (RecyclerView)
                findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        //Set RecyclerView type according to intent value
       // if (navigateFrom.equals("horizontal")) {
        //    getSupportActionBar().setTitle("Horizontal Recycler View");
         //   recyclerView
          //7          .setLayoutManager(new LinearLayoutManager(RecyclerView_Activity.this, LinearLayoutManager.HORIZONTAL, false));
        //} else {
         //   getSupportActionBar().setTitle("Staggered GridLayout Manager");
          //  recyclerView
            //        .setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));// Here 2 is no. of columns to be displayed
       // }
    }


    // populate the list view by adding data to arraylist
    private void populatRecyclerView() {
        ArrayList<ListCar> arrayList = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            arrayList.add(new ListCar(i,TITLES[i],"descripccion"));
        }
        CardAdapter adapter = new CardAdapter(RecyclerView_Activity.this, arrayList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
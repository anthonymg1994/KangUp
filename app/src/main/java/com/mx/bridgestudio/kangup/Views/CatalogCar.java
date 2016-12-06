package com.mx.bridgestudio.kangup.Views;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.mx.bridgestudio.kangup.Adapters.CardAdapter;
import com.mx.bridgestudio.kangup.Controllers.BaseActivity;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendToActivity;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Brand;
import com.mx.bridgestudio.kangup.Models.Lists.ListBrand;
import com.mx.bridgestudio.kangup.Models.Lists.ListCar;
import com.mx.bridgestudio.kangup.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by Anthony on 07/11/2016.
 */

public class CatalogCar extends BaseActivity implements View.OnClickListener,OnDataSendToActivity {

    MaterialTabHost tabHost;
    ViewPager viewPager;
   // ViewPagerAdapter androidAdapter;
    //UI References
    private DatePicker dpResult;
    final Calendar c = Calendar.getInstance();
    private int year =  c.get(Calendar.YEAR);
    private int month = c.get(Calendar.MONTH);
    private int day = c.get(Calendar.DAY_OF_MONTH);
    boolean isOkayClicked = true;
    private RecyclerView recyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    private CardAdapter adapter;
    private List<ListBrand> albumList;
    private webServices webs = new webServices();
    private FloatingActionButton editCalendar;
    private Brand brand = new Brand();
    private Brand[] brands;
    private ListBrand[]  list;
    private int flag = 0;
    private Brand[] obj1;
    public static int flagDate = 0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogcar);

        Intent mIntent = getIntent();
        brand.setId_categoria(CategoryActivity.opcionSeleccionada);


        webs.brandByCategory(CatalogCar.this,CatalogCar.this,brand);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Marcas");

        //tabs
      //  tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
      //  viewPager = (ViewPager) this.findViewById(R.id.viewPager);
       // webs.brandByCategory(CatalogCar.this,brand);
        //adapter view
        /*
        androidAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(androidAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int tabposition) {
                tabHost.setSelectedNavigationItem(tabposition);
            }
        });

        //for tab position
        for (int i = 0; i < androidAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(androidAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
*/
        // 1. get a reference to recyclerView
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        // 2. set layoutManger
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        albumList = new ArrayList<>();

        //adaptador de listcar
        adapter = new CardAdapter(this, albumList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        // 2 columnas
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        try {
            // Imagen top de vista catalogo
            Glide.with(this).load(R.drawable.top_catalogo).into((ImageView)findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


        initCollapsingToolbar();

        editCalendar = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        editCalendar.setOnClickListener(this);

        if(flagDate == 0){
        //calendario de disponibildad
        showDatePicker();

        }

    }

    /**
     * Datos de prueba para ejecucion
     */
    private void prepareListBrands(Brand[] brand) {
        int [] images = new int[]{
                R.drawable.auto,
                R.drawable.auto,
                R.drawable.auto,
        };
        ListBrand[] list = new ListBrand[brand.length];
        for(int i = 0 ; i < brand.length ; i++){
            list[i] = new ListBrand();
            list[i].setId(brand[i].getId());
            list[i].setName(brand[i].getNombre());
            list[i].setId_categoria(brand[i].getId_categoria());
            list[i].setImage(images[i]);
            albumList.add(list[i]);
        }

        adapter.notifyDataSetChanged();


    }

    @Override
    public void sendData(Brand[] obj) {
        Toast.makeText(this, "marcas"+obj.length, Toast.LENGTH_SHORT).show();
        prepareListBrands(obj);
        //obj1 = obj;
    }


/*
    @Override
    public void sendData(Brand[] brand) {
        Toast.makeText(this, "string"+brand, Toast.LENGTH_SHORT).show();
        for(int j = 0 ; j < brand.lenght() ; j ++){
                list[j] = new ListBrand();
                list[j].setNombre = brand[j].getName();

        }
            albumList.add(list);
            adapter.notifyDataSetChanged();
    }
*/

/*
    @Override
    public void sendData(String response) {
        Toast.makeText(this, "string"+response, Toast.LENGTH_SHORT).show();
    }
*/
    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column
            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Marcas");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.floatingActionButton2) {
            showDatePicker();
        }

    }
/*
    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }
*/
    private void showDatePicker() {
        flag = 1;
        // TODO Auto-generated method stub
        DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            // when dialog box is closed, below method will be called.
            public void onDateSet(DatePicker view, int selectedYear,
                                  int selectedMonth, int selectedDay) {
                if (isOkayClicked) {
                      year =  selectedYear;
                      month = selectedMonth;
                      day = selectedDay;
                }
                isOkayClicked = false;
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                CatalogCar.this, datePickerListener, year,
                month, day);
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialog.cancel();
                            isOkayClicked = false;
                        }
                    }
                });
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            isOkayClicked = true;

                        }
                    }
                });
        datePickerDialog.setCancelable(false);
        datePickerDialog.show();
    }
    @Override
    public void onBackPressed()
    {
        Intent setIntent = new Intent(this,CategoryActivity.class);
        startActivity(setIntent);
        finish();
    }

    /*
    // view pager adapter
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int num) {

               return new AndroidFragment();
            }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int tabposition) {
            CharSequence tab = null;
            switch(tabposition){
                case 0: tab= "Top";
                        break;
                case 1: tab= "Recomendados";
                        break;
                case 2: tab= "Favoritos";
                        break;
                default: return null;
            }
            return tab;
        }
    }
*/
}

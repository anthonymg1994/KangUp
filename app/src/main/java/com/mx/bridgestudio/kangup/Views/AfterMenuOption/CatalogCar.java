package com.mx.bridgestudio.kangup.Views.AfterMenuOption;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mx.bridgestudio.kangup.Adapters.CardAdapter;
import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.Interfaces.OnDataSendToActivity;
import com.mx.bridgestudio.kangup.Controllers.ServiciosWeb.webServices;
import com.mx.bridgestudio.kangup.Models.Brand;
import com.mx.bridgestudio.kangup.Models.Lists.ListBrand;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.LeftSide.DrawerActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.CategoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.HistoryActivity;
import com.mx.bridgestudio.kangup.Views.MenuActivity.NewsActivity;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialtabs.MaterialTabHost;

/**
 * Created by Anthony on 07/11/2016.
 */

public class CatalogCar extends DrawerActivity implements View.OnClickListener,OnDataSendToActivity {


    //implements OnDataSendToActivity


    MaterialTabHost tabHost;
    ViewPager viewPager;
   // ViewPagerAdapter androidAdapter;
    //UI References
    private DatePicker dpResult;
    boolean isOkayClicked;
    private int mYear, mMonth, mDay;
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
    protected DrawerLayout mDrawer;
    Control control = new Control();

    private DrawerActivity drw = new DrawerActivity();

    //toolbardown
    private ImageButton catalogo,noticias,favoritos,historial;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.catalogcar);
        control.changeColorStatusBar(CatalogCar.this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        View contentView = inflater.inflate(R.layout.catalogcar, null, false);
        mDrawer.addView(contentView, 0);

        Intent mIntent = getIntent();
        brand.setId_categoria(CategoryActivity.opcionSeleccionada);

        webs.brandByCategory(CatalogCar.this,CatalogCar.this,brand);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Marcas");
        getSupportActionBar().setTitle("Marcas");

        //drw.setNameToolbar("Marcas");
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

        catalogo = (ImageButton)findViewById(R.id.catalogoToolbar);
        catalogo.setColorFilter(ContextCompat.getColor(CatalogCar.this,R.color.colorAccent));

        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(CatalogCar.this, CategoryActivity.class));
            }
        });
        noticias = (ImageButton)findViewById(R.id.noticiasToolbar);
        noticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(CatalogCar.this, NewsActivity.class));
            }
        });
        favoritos  = (ImageButton)findViewById(R.id.favoritosToolbar);
        favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(CatalogCar.this, FavoriteActivity.class));
            }
        });
        historial = (ImageButton)findViewById(R.id.historialToolbar);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // close this activity and return to preview activity (if there is any)
                startActivity(new Intent(CatalogCar.this, HistoryActivity.class));
            }
        });


        try {
            // Imagen top de vista catalogo
            Glide.with(this).load(R.drawable.top_catalogo).into((ImageView)findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


        initCollapsingToolbar();



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
    //    Toast.makeText(this, "marcas"+obj.length, Toast.LENGTH_SHORT).show();
        prepareListBrands(obj);
        //obj1 = obj;
    }
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_drawer_drawer, menu);
        return true;
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

package com.mx.bridgestudio.kangup.Models;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.mx.bridgestudio.kangup.Adapters.AdapterFavoriteList;
import com.mx.bridgestudio.kangup.Controllers.SqlLite.SqliteController;
import com.mx.bridgestudio.kangup.R;
import com.mx.bridgestudio.kangup.Views.MenuActivity.FavoriteActivity;

/**
 * Created by USUARIO on 13/12/2016.
 */

public class screenAsync extends Activity{

    public  static screenAsync sInstance = null;
    public static Context context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashasync);
        sInstance = this;

        screenAsync.context = getApplicationContext();
    }
    // Getter to access Singleton instance
    public static screenAsync getInstance() {
        return sInstance;
    }
    public static Context getAppContext() {
        return screenAsync.context;
    }

}

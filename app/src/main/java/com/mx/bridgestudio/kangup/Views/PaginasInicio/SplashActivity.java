package com.mx.bridgestudio.kangup.Views.PaginasInicio;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;

import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.Controllers.SessionManager;
import com.mx.bridgestudio.kangup.R;

public class SplashActivity extends Activity {
    private Control control = new Control();
    private SessionManager session;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        control.changeColorStatusBar(SplashActivity.this);
        session = new SessionManager(this);
      //  getSupportActionBar().hide();
        handler.sendEmptyMessageDelayed(1000,1000);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            session.checkLogin();
        }
    };
}

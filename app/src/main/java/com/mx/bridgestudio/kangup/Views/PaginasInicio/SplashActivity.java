package com.mx.bridgestudio.kangup.Views.PaginasInicio;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mx.bridgestudio.kangup.Controllers.Control;
import com.mx.bridgestudio.kangup.R;

public class SplashActivity extends Activity {
    private Control control = new Control();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        control.changeColorStatusBar(SplashActivity.this);

      //  getSupportActionBar().hide();

        handler.sendEmptyMessageDelayed(3000,3000);

    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    };
}

package com.mx.bridgestudio.kangup.Views.PaginasInicio;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mx.bridgestudio.kangup.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

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

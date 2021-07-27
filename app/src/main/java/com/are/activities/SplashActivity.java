package com.are.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.are.MyApp;
import com.are.R;
import com.are.model.MainUser;
import com.google.gson.Gson;

public class SplashActivity extends AppCompatActivity {

    public AppCompatButton btn_next;
    public SplashActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        instance = this;
        initView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MyApp.mySharedPref.getIsLoggedIn()) {
                    try {
                        Gson gson = new Gson();
                        MyApp.user = gson.fromJson(MyApp.mySharedPref.getUserModel(), MainUser.class);
                    } catch (Exception e) {

                    }
                    startActivity(new Intent(instance, DashboardActivity.class));
                    finish();
                } else {
                    Intent intent = new Intent(instance, LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }


            }
        }, 2000);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initView() {
        btn_next = findViewById(R.id.btn_next);
    }

}
package com.are.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.are.R;

public class ForgotPass1Activity extends AppCompatActivity {
    public ForgotPass1Activity instance;
    public ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass1);
        instance = this;
        initView();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        img_back = findViewById(R.id.img_back);
    }

    public void onClickEmail(View view) {
        Intent intent = new Intent(instance, ForgotPassActivity.class);
        startActivity(intent);

    }

    public void onClickOtp(View view) {
        Intent intent = new Intent(instance, EnterMobileActivity.class);
        startActivity(intent);
    }
}
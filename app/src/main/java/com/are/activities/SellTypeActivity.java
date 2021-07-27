package com.are.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.are.R;

public class SellTypeActivity extends AppCompatActivity {
    public SellTypeActivity instance;
    public AppCompatButton btn_publish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_type);
        instance = this;
        initView();
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, UnderReviewActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btn_publish = findViewById(R.id.btn_publish);
    }
}
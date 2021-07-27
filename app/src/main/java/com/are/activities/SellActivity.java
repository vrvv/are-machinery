package com.are.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.are.R;

public class SellActivity extends AppCompatActivity {
    public SellActivity instance;
    public AppCompatButton btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        instance = this;
        initView();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, SellTypeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btn_next = findViewById(R.id.btn_next);
    }
}
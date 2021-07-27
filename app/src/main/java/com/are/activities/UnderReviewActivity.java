package com.are.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.are.R;

public class UnderReviewActivity extends AppCompatActivity {
    public UnderReviewActivity instance;
    public AppCompatButton btn_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_review);
        instance = this;
        initView();
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btn_done = findViewById(R.id.btn_done);
    }
}
package com.are.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.are.R;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class UnderVerificationActivity extends AppCompatActivity {
    public UnderVerificationActivity instance;
    public AppCompatButton btn_close;
    public TextView tvContactUs;
    public BottomSheetDialog mBottomSheetContactus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_verification);
        instance = this;
        initView();
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, DashboardActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        bottomContactUs();

        tvContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetContactus.show();
            }
        });
    }

    private void bottomContactUs() {
        View view = getLayoutInflater().inflate(R.layout.bottom_enquiry_success, null);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setAlignItems(AlignItems.FLEX_START);

        mBottomSheetContactus = new BottomSheetDialog(instance, R.style.DialogStyle);
        mBottomSheetContactus.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetContactus.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        Button btn_close = view.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetContactus.dismiss();
            }
        });
    }

    private void initView() {
        btn_close = findViewById(R.id.btn_close);
        tvContactUs = findViewById(R.id.tvContactUs);
    }
}
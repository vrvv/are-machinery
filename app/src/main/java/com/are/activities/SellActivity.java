package com.are.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.are.R;
import com.are.utils.ToastUtils;

public class SellActivity extends AppCompatActivity {
    public SellActivity instance;
    public AppCompatButton btn_next;
    public ImageView img_back;
    public RelativeLayout rel_sparepart, rel_equipment;
    public int itemType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        instance = this;
        initView();
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemType == 0) {
                    ToastUtils.show(instance, "Please select any one");
                } else {
                    if (itemType == 1) {
                        Intent intent = new Intent(instance, AddEquipmentItemActivity.class);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(instance, AddSpareItemActivity.class);
                        startActivity(intent);

                    }
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void initView() {
        rel_equipment = findViewById(R.id.rel_equipment);
        rel_sparepart = findViewById(R.id.rel_sparepart);
        img_back = findViewById(R.id.img_back);
        btn_next = findViewById(R.id.btn_next);
    }

    public void onClickSpares(View view) {
        itemType = 2;
        rel_sparepart.setBackground(getResources().getDrawable(R.drawable.rounded_light_blue_ring));
        rel_equipment.setBackground(getResources().getDrawable(R.drawable.rounded_gray_bg));
    }

    public void onClickEquipment(View view) {
        itemType = 1;
        rel_sparepart.setBackground(getResources().getDrawable(R.drawable.rounded_gray_bg));
        rel_equipment.setBackground(getResources().getDrawable(R.drawable.rounded_light_blue_ring));
    }
}
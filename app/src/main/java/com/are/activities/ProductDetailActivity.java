package com.are.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.are.R;
import com.are.adapter.ImageAdapterExample;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {
    public ProductDetailActivity instance;
    SliderView sliderView;
    ImageAdapterExample adapter;
    ArrayList<String> dashboardImageArrayList = new ArrayList<>();
    public AppCompatButton btn_enquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        instance = this;
        btn_enquiry = findViewById(R.id.btn_enquiry);
        sliderView = findViewById(R.id.imageSlider);
        adapter = new ImageAdapterExample(ProductDetailActivity.this, dashboardImageArrayList);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(R.color.black);
        sliderView.setIndicatorUnselectedColor(R.color.white);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        btn_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
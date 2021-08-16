package com.are.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.are.MyApp;
import com.are.R;
import com.are.adapter.HistoryAdapter;
import com.are.adapter.MyItemEnquiryAdapter;
import com.are.model.MyEnquiries;
import com.are.model.MyItem;
import com.are.model.MyItemEnquiries;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.utils.LogUtils;
import com.are.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MyItemDetailActivity extends AppCompatActivity {
    public MyItemDetailActivity instance;
    public RecyclerView rvMyItemEnquiry;
    public MyItemEnquiryAdapter myItemEnquiryAdapter;
    public List<MyItemEnquiries> myItemEnquiriesArrayList = new ArrayList<>();
    public ImageView img_back;
    public MyItem myItem;
    public TextView tv_date,tvName,tv_price,tv_company_name;
    public ShapeableImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_item_detail);
        instance = this;
        myItem = (MyItem) getIntent().getSerializableExtra("itemId");
        img_back = findViewById(R.id.img_back);
        tv_date = findViewById(R.id.tv_date);
        tvName = findViewById(R.id.tvName);
        tv_price = findViewById(R.id.tv_price);
        tv_company_name = findViewById(R.id.tv_company_name);
        image = findViewById(R.id.image);

        tvName.setText(myItem.getName());
        tv_company_name.setText(MyApp.user.getCompanyName());
        tv_date.setText(myItem.getName());
        Glide.with(img_back)
                .load("http://aremachinery.com/items" + myItem.getItemImage())
                .placeholder(R.drawable.img_machine)
                .transition(withCrossFade())
                .into(image);
        rvMyItemEnquiry = findViewById(R.id.rvMyItemEnquiry);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        hitMyItemEnquireListApi();
    }

    private void hitMyItemEnquireListApi() {
        Call<ResponseModel<List<MyItemEnquiries>>> callStates = RestServiceFactory.createServiceUser().getMyItemEnquiries(myItem.getEquipmentId());

        callStates.enqueue(new RestCallBack<ResponseModel<List<MyItemEnquiries>>>(instance) {
            @Override
            public void onFailure(Call<ResponseModel<List<MyItemEnquiries>>> call, String message) {
                ToastUtils.show(instance, message);
                LogUtils.api("onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<List<MyItemEnquiries>>> call, Response<ResponseModel<List<MyItemEnquiries>>> restResponse, ResponseModel<List<MyItemEnquiries>> response) {
                if (response.isSuccess) {
                    myItemEnquiriesArrayList.clear();
                    myItemEnquiriesArrayList.addAll(response.data);
                    if (myItemEnquiriesArrayList.size() > 0) {
                        myItemEnquiryAdapter =
                                new MyItemEnquiryAdapter(instance, myItemEnquiriesArrayList);
                        rvMyItemEnquiry.setHasFixedSize(true);
                        rvMyItemEnquiry.setLayoutManager(new LinearLayoutManager(instance));
                        rvMyItemEnquiry.setAdapter(myItemEnquiryAdapter);
                        rvMyItemEnquiry.setNestedScrollingEnabled(false);

                    }
                } else {
                    ToastUtils.show(instance, response.message);
                }
            }
        });
    }
}
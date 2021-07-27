package com.are.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.are.MyApp;
import com.are.R;
import com.are.adapter.RecomMachineryAdapter;
import com.are.drawer.FragmentDrawer;
import com.are.model.Items;
import com.are.model.rest_request.GetItemRequest;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.utils.LogUtils;
import com.are.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    public DashboardActivity instance;
    public RecyclerView rvRecomMachinery;
    public TextView tv_sell_here;
    public AppCompatButton btn_post_request;
    public GetItemRequest getItemRequest;
    public List<Items> itemsArrayList = new ArrayList<>();
    private FragmentDrawer drawerFragment;
    public ImageView img_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        instance = this;
        getItemRequest = new GetItemRequest();
        drawerFragment = (FragmentDrawer) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        img_menu = findViewById(R.id.img_menu);
        setupDrawer();
        tv_sell_here = findViewById(R.id.tv_sell_here);
        btn_post_request = findViewById(R.id.btn_post_request);
        rvRecomMachinery = findViewById(R.id.rvRecomMachinery);
        getItemList();
        tv_sell_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, SellActivity.class);
                startActivity(intent);
            }
        });
        btn_post_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, EnquireActivity.class);
                startActivity(intent);
            }
        });

    }
    public void setupDrawer() {
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), img_menu);
    }
    private void getItemList() {
        getItemRequest.setBrand("");
        getItemRequest.setFromCount(0);
        getItemRequest.setToCount(10);
        getItemRequest.setItemType("");
        getItemRequest.setLocation("Hydrabad");
        getItemRequest.setLocation(MyApp.mySharedPref.getUserId());
        Call<ResponseModel<List<Items>>> callStates = RestServiceFactory.createServiceUser().getItem(getItemRequest);

        callStates.enqueue(new RestCallBack<ResponseModel<List<Items>>>(instance) {
            @Override
            public void onFailure(Call<ResponseModel<List<Items>>> call, String message) {
                ToastUtils.show(instance, message);
                LogUtils.api("onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<List<Items>>> call, Response<ResponseModel<List<Items>>> restResponse, ResponseModel<List<Items>> response) {
                if (response.isSuccess) {
                    itemsArrayList.addAll(response.data);
                    if(itemsArrayList.size()>0){
                        setupRecyclerview();

                    }
                } else {
                    ToastUtils.show(instance, response.message);
                }
            }
        });

    }

    private void setupRecyclerview() {
        RecomMachineryAdapter itemListDataAdapter =
                new RecomMachineryAdapter(instance, itemsArrayList);
        rvRecomMachinery.setHasFixedSize(true);
        rvRecomMachinery.setLayoutManager(new GridLayoutManager(instance,
                2));
        rvRecomMachinery.setAdapter(itemListDataAdapter);

        rvRecomMachinery.setNestedScrollingEnabled(false);
    }
}
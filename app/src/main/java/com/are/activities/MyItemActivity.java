package com.are.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.are.MyApp;
import com.are.R;
import com.are.adapter.MyItemAdapter;
import com.are.model.MyItem;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.utils.LogUtils;
import com.are.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MyItemActivity extends AppCompatActivity {
    public MyItemActivity instance;
    public RecyclerView rvMyItem;
    public MyItemAdapter myItemAdapter;
    public List<MyItem> myItemArrayList = new ArrayList<>();
    public ImageView img_back;
    public LinearLayout linEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_item);
        instance = this;
        linEmpty = findViewById(R.id.linEmpty);
        img_back = findViewById(R.id.img_back);
        rvMyItem = findViewById(R.id.rvMyItem);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rvMyItem.setLayoutManager(new LinearLayoutManager(instance));
        myItemAdapter = new MyItemAdapter(instance, myItemArrayList);
        myItemAdapter.setOnItemClickListener(new MyItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                hitCloseItemApi(myItemArrayList.get(position).getEquipmentId());
            }
        });
        rvMyItem.setAdapter(myItemAdapter);

        hitMyItemApi();
    }

    private void hitCloseItemApi(int equipmentId) {
        Call<ResponseModel<String>> callStates = RestServiceFactory.createServiceUser().closeItem(equipmentId, MyApp.mySharedPref.getUserId());

        callStates.enqueue(new RestCallBack<ResponseModel<String>>(instance) {
            @Override
            public void onFailure(Call<ResponseModel<String>> call, String message) {
                ToastUtils.show(instance, message);
                LogUtils.api("onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> restResponse, ResponseModel<String> response) {
                if (response.isSuccess) {
                    hitMyItemApi();
                } else {
                    ToastUtils.show(instance, response.message);
                }
            }
        });
    }

    private void hitMyItemApi() {
        Call<ResponseModel<List<MyItem>>> callStates = RestServiceFactory.createServiceUser().getMyItem(MyApp.mySharedPref.getUserId());

        callStates.enqueue(new RestCallBack<ResponseModel<List<MyItem>>>(instance) {
            @Override
            public void onFailure(Call<ResponseModel<List<MyItem>>> call, String message) {
                ToastUtils.show(instance, message);
                linEmpty.setVisibility(View.VISIBLE);
                rvMyItem.setVisibility(View.GONE);
                LogUtils.api("onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<List<MyItem>>> call, Response<ResponseModel<List<MyItem>>> restResponse, ResponseModel<List<MyItem>> response) {
                if (response.isSuccess) {
                    myItemArrayList.clear();
                    myItemArrayList.addAll(response.data);
                    if (myItemArrayList.size() > 0) {
                        linEmpty.setVisibility(View.GONE);
                        rvMyItem.setVisibility(View.VISIBLE);
                        myItemAdapter.notifyDataSetChanged();

                    } else {
                        linEmpty.setVisibility(View.VISIBLE);
                        rvMyItem.setVisibility(View.GONE);
                    }
                } else {
                    linEmpty.setVisibility(View.VISIBLE);
                    rvMyItem.setVisibility(View.GONE);
                    ToastUtils.show(instance, response.message);
                }
            }
        });
    }
}
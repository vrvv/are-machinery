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
import com.are.adapter.HistoryAdapter;
import com.are.model.MyEnquiries;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.utils.LogUtils;
import com.are.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    public HistoryActivity instance;
    public RecyclerView rvHistory;
    public HistoryAdapter historyAdapter;
    public List<MyEnquiries> myEnquiriesArrayList = new ArrayList<>();
    public ImageView img_back;
    public LinearLayout linEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        instance = this;
        img_back = findViewById(R.id.img_back);
        linEmpty = findViewById(R.id.linEmpty);
        rvHistory = findViewById(R.id.rvHistory);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        hitEnquireListApi();
    }

    private void hitEnquireListApi() {
        Call<ResponseModel<List<MyEnquiries>>> callStates = RestServiceFactory.createServiceUser().getMyEnquiriesCompany(MyApp.mySharedPref.getUserId());

        callStates.enqueue(new RestCallBack<ResponseModel<List<MyEnquiries>>>(instance) {
            @Override
            public void onFailure(Call<ResponseModel<List<MyEnquiries>>> call, String message) {
                ToastUtils.show(instance, message);
                linEmpty.setVisibility(View.VISIBLE);
                rvHistory.setVisibility(View.GONE);
                LogUtils.api("onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<List<MyEnquiries>>> call, Response<ResponseModel<List<MyEnquiries>>> restResponse, ResponseModel<List<MyEnquiries>> response) {
                if (response.isSuccess) {
                    myEnquiriesArrayList.clear();
                    myEnquiriesArrayList.addAll(response.data);
                    if (myEnquiriesArrayList.size() > 0) {
                        linEmpty.setVisibility(View.GONE);
                        rvHistory.setVisibility(View.VISIBLE);
                        historyAdapter =
                                new HistoryAdapter(instance, myEnquiriesArrayList);
                        rvHistory.setHasFixedSize(true);
                        rvHistory.setLayoutManager(new LinearLayoutManager(instance));
                        rvHistory.setAdapter(historyAdapter);
                        rvHistory.setNestedScrollingEnabled(false);

                    }else{
                        linEmpty.setVisibility(View.VISIBLE);
                        rvHistory.setVisibility(View.GONE);

                    }
                } else {
                    linEmpty.setVisibility(View.VISIBLE);
                    rvHistory.setVisibility(View.GONE);
                    ToastUtils.show(instance, response.message);
                }
            }
        });
    }
}
package com.are.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.are.R;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.utils.LogUtils;
import com.are.utils.ToastUtils;

import retrofit2.Call;
import retrofit2.Response;

public class ContantActivity extends AppCompatActivity {
    public ContantActivity instance;
    public TextView tv_content,tv_title;
    public String type = "";
    public ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contant);

        instance = this;
        type = getIntent().getStringExtra("type");
        tv_content = findViewById(R.id.tv_content);
        tv_title = findViewById(R.id.tv_title);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (type.equals("517"))
            tv_title.setText("Terms & Conditions");
        if (type.equals("516"))
            tv_title.setText("Privacy Policy");

         getContent(type);
    }

    private void getContent(String type) {
        Call<ResponseModel<String>> callStates = RestServiceFactory.createServiceUser().getContent(Integer.parseInt(type));

        callStates.enqueue(new RestCallBack<ResponseModel<String>>(instance) {
            @Override
            public void onFailure(Call<ResponseModel<String>> call, String message) {
                ToastUtils.show(instance, message);
                LogUtils.api("onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> restResponse, ResponseModel<String> response) {
                if (response.isSuccess) {
                    tv_content.setText(response.data);
                } else {
                    ToastUtils.show(instance, response.message);
                }
            }
        });
    }


}
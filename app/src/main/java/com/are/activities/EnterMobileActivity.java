package com.are.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.are.R;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.ui.NoChangingBackgroundTextInputLayout;
import com.are.utils.DialogUtils;
import com.are.utils.LogUtils;
import com.are.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Response;

public class EnterMobileActivity extends AppCompatActivity {
    public EnterMobileActivity context;
    public AppCompatButton btn_send;
    public TextInputEditText et_mobile;
    public NoChangingBackgroundTextInputLayout ip_mobile;
    public Dialog loder;
    private String otpText = "";
    public ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile);
        context = this;
        initView();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void validation() {

        String phoneText = et_mobile.getText().toString().trim();
        if (phoneText.length() == 0) {
            ip_mobile.setError("please enter phone number");
            return;
        }
        if (phoneText.length() != 10) {
            ip_mobile.setError("phone number is invalid");
            return;
        }
        if (phoneText.length() < 10) {
            ip_mobile.setError("please enter valid 10 digit mobile number");
            return;
        }
        hitApiForOTP(phoneText);
    }
    private void initView() {
        img_back = findViewById(R.id.img_back);
        et_mobile = findViewById(R.id.et_mobile);
        ip_mobile = findViewById(R.id.ip_mobile);
        btn_send = findViewById(R.id.btn_send);
    }
    public void hitApiForOTP(String phoneText) {
        loder = DialogUtils.showLoader(this);
        otpText = null;
        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().getOtp(phoneText);
        call.enqueue(new RestCallBack<ResponseModel<String>>(context) {
            @Override
            public void onFailure(Call<ResponseModel<String>> call, String message) {
                loder.dismiss();
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> restResponse, ResponseModel<String> response) {
                loder.dismiss();
                if (response.isSuccess) {
                    otpText = response.data;
                    startActivity(new Intent(context, ForgotVerifyOtpActivity.class)
                            .putExtra("mobileNo", phoneText)
                            .putExtra("otp", otpText));


                } else {
                    ToastUtils.show(context, response.message);
                }
            }
        });
    }
}
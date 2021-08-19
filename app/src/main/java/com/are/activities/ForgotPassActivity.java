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

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class ForgotPassActivity extends AppCompatActivity {
    public ForgotPassActivity context;
    public AppCompatButton btn_send;
    public TextInputEditText et_email, et_mobile;
    public NoChangingBackgroundTextInputLayout ip_email, ip_mobile;
    public Dialog loder;
    private String otpText = "";
    public ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
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
        String emailText = et_email.getText().toString().trim();
        if (emailText.isEmpty()) {
            ip_email.setError("please enter email");
            return;
        }
        if (!LogUtils.isValidEmail(emailText)) {
            ip_email.setError("please enter valid email");
            return;
        }
        String phoneText = et_mobile.getText().toString().trim();
        if (phoneText.length() == 0) {
            ip_mobile.setError("please enter phone number");
            ip_email.setError(null);
            return;
        }
        if (phoneText.length() != 10) {
            ip_mobile.setError("phone number is invalid");
            ip_email.setError(null);
            return;
        }
        if (phoneText.length() < 10) {
            ip_mobile.setError("please enter valid 10 digit mobile number");
            ip_email.setError(null);
            return;
        }
        hitForgotPasswordApi(emailText, phoneText);
    }

    private void hitForgotPasswordApi(String emailText, String phoneText) {
        loder = DialogUtils.showLoader(this);
        Map<String, String> data = new HashMap<>();
        data.put("mobile", phoneText);
        data.put("email", emailText);
        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().forgotPassEmail(data);
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
                    ToastUtils.show(context, response.message);
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();


                } else {
                    ToastUtils.show(context, response.message);
                }
            }
        });
    }

    private void initView() {
        img_back = findViewById(R.id.img_back);
        et_email = findViewById(R.id.et_email);
        et_mobile = findViewById(R.id.et_mobile);
        ip_email = findViewById(R.id.ip_email);
        ip_mobile = findViewById(R.id.ip_mobile);
        btn_send = findViewById(R.id.btn_send);
    }
}
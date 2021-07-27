package com.are.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.are.MyApp;
import com.are.R;
import com.are.model.MainUser;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.ui.NoChangingBackgroundTextInputLayout;
import com.are.utils.DialogUtils;
import com.are.utils.LogHelper;
import com.are.utils.NetworkUtil;
import com.are.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public LoginActivity context;
    public AppCompatButton btn_login;
    public AppCompatTextView tvSignup;
    public TextInputEditText et_email, et_password;
    public NoChangingBackgroundTextInputLayout ip_email, ip_password;
    public Dialog loder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        initView();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validation() {
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        if (email.isEmpty()) {
            ip_email.setError("please enter email/mobile");
            return;
        }
        if (password.isEmpty()) {
            ip_password.setError("please enter password");
            ip_email.setError(null);
            return;
        }
        ip_email.setError(null);
        ip_password.setError(null);
        hitLoginApi(email, password);
    }

    private void hitLoginApi(String email, String password) {
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No internet");
            return;
        }
        loder = DialogUtils.showLoader(this);
        Map<String, String> data = new HashMap<>();
        data.put("username", email);
        data.put("password", password);
        Call<ResponseModel<MainUser>> call = RestServiceFactory.createServiceUser().validateUser(data);
        call.enqueue(new RestCallBack<ResponseModel<MainUser>>(context) {
            @Override
            public void onFailure(Call<ResponseModel<MainUser>> call, String message) {
                loder.dismiss();
                ToastUtils.show(context, message);
                LogHelper.d("Failure", "onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<MainUser>> call, Response<ResponseModel<MainUser>> restResponse, ResponseModel<MainUser> response) {
                loder.dismiss();
                if (response.isSuccess) {
                    MyApp.user = response.data;
                    Gson gson = new Gson();
                    String json = gson.toJson(response.data);
                    MyApp.mySharedPref.setUserModel(json);
                    MyApp.mySharedPref.setIsLoggedIn(true);
                    MyApp.mySharedPref.setUserId(String.valueOf(MyApp.user.getUserId()));
                    ToastUtils.show(context, response.message);
                    Intent i = new Intent(context, DashboardActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    ToastUtils.show(context, response.message);
                }
            }
        });
    }

    private void initView() {
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tvSignup = findViewById(R.id.tvSignup);
        ip_email = findViewById(R.id.ip_email);
        ip_password = findViewById(R.id.ip_password);
    }
}
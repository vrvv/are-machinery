package com.are.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.are.MyApp;
import com.are.R;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.ui.NoChangingBackgroundTextInputLayout;
import com.are.utils.DialogUtils;
import com.are.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Response;

public class ChangePassActivity extends AppCompatActivity {
    public String mobileNo = "";
    public ChangePassActivity context;
    public AppCompatButton btn_done;
    public TextInputEditText et_password, et_re_password;
    public NoChangingBackgroundTextInputLayout ip_password, ip_re_password;
    public Dialog loder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        context = this;
        try {
            mobileNo = getIntent().getStringExtra("mobileNo");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void validation() {
        String pass = et_password.getText().toString();
        String re_pass = et_re_password.getText().toString();
        if (pass.isEmpty()) {
            ip_password.setError("Please Enter Password");
            return;
        }
        if (re_pass.isEmpty()) {
            ip_re_password.setError("Please re-enter Password");
            ip_password.setError(null);
            return;
        }
        if (!pass.equals(re_pass)) {
            ip_re_password.setError("Password was not match");
            ip_password.setError(null);
            return;
        }
        hitForgotMobileApi(pass);


    }

    private void hitForgotMobileApi(String pass) {
        loder = DialogUtils.showLoader(this);
        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().setPass(MyApp.mySharedPref.getUserId(), pass);
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
        ip_password = findViewById(R.id.ip_password);
        ip_re_password = findViewById(R.id.ip_re_password);
        et_password = findViewById(R.id.et_password);
        et_re_password = findViewById(R.id.et_re_password);
        btn_done = findViewById(R.id.btn_done);
    }

}
package com.are.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.are.MyApp;
import com.are.R;
import com.are.model.rest_request.AddDeviceRequest;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.ui.NoChangingBackgroundTextInputLayout;
import com.are.utils.DialogUtils;
import com.are.utils.NetworkUtil;
import com.are.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Response;

public class SetupPasswordActivity extends AppCompatActivity {
    public SetupPasswordActivity context;
    public AppCompatButton btn_done;
    public TextInputEditText et_password, et_re_password;
    public NoChangingBackgroundTextInputLayout ip_password, ip_re_password;
    public Dialog loder;
    public AddDeviceRequest addDeviceRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_password);
        context = this;
        addDeviceRequest = new AddDeviceRequest();
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
            ip_password.setError("please enter password");
            return;
        }
        if (re_pass.isEmpty()) {
            ip_re_password.setError("please re-enter password");
            ip_password.setError(null);
            return;
        }
        if (!pass.equals(re_pass)) {
            ip_re_password.setError("password was not match");
            ip_password.setError(null);
            return;
        }
        MyApp.registerRequest.setPassword(pass);
        hitRegisterApi();

    }

    private void hitRegisterApi() {
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No internet");
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(MyApp.registerRequest);
        Log.d("myTag", json);
        loder = DialogUtils.showLoader(this);

        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().register(MyApp.registerRequest);
        call.enqueue(new RestCallBack<ResponseModel<String>>(context) {
            @Override
            public void onFailure(Call<ResponseModel<String>> call, String message) {
                loder.dismiss();
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> restResponse, ResponseModel<String> response) {
                if (response.isSuccess) {
                    MyApp.mySharedPref.setUserId(response.data);
                    hitAddDeviceApi();
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

    private void hitAddDeviceApi() {
        addDeviceRequest.setUserId(MyApp.mySharedPref.getUserId());
        addDeviceRequest.setModel(android.os.Build.MODEL);
        addDeviceRequest.setPlatform(System.getProperty("os.version"));
        addDeviceRequest.setKey(Settings.Secure.ANDROID_ID);
        addDeviceRequest.setManufacturer(android.os.Build.MANUFACTURER);
        addDeviceRequest.setSerial(Build.SERIAL);
        addDeviceRequest.setDeviceType("1");
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No internet");
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(addDeviceRequest);
        Log.d("myTag", json);

        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().addDevice(addDeviceRequest);
        call.enqueue(new RestCallBack<ResponseModel<String>>(context) {
            @Override
            public void onFailure(Call<ResponseModel<String>> call, String message) {
                loder.dismiss();
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> restResponse, ResponseModel<String> response) {
                if (response.isSuccess) {
                    Intent i = new Intent(context, DashboardActivity.class);
                    startActivity(i);
                    finishAffinity();

                } else {
                    ToastUtils.show(context, response.message);
                }
            }
        });
    }

}
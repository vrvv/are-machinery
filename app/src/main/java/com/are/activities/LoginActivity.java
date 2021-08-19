package com.are.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;

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
    public AppCompatTextView tvSignup,tv_forgot;
    public TextInputEditText et_email, et_password;
    public NoChangingBackgroundTextInputLayout ip_email, ip_password;
    public Dialog loder;
    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    int PERMISSION_ALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        initView();
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ForgotPass1Activity.class);
                startActivity(intent);

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

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void validation() {
        final String regexStr = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        if (email.isEmpty()) {
            ip_email.setError("Please Enter Email/Mobile");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.matches(regexStr)){
            ip_email.setError("Enter Valid Email or Mobile");
            return;
        }

        if (password.isEmpty()) {
            ip_password.setError("Please Enter Password");
            ip_email.setError(null);
            return;
        }
        ip_email.setError(null);
        ip_password.setError(null);
        hitLoginApi(email, password);
    }

    private void hitLoginApi(String email, String password) {
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No Internet");
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
                    hitUserStatus(response.data);
                } else {
                    ToastUtils.show(context, response.message);
                }
            }
        });
    }

    private void hitUserStatus(MainUser mainUser) {
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No Internet");
            return;
        }
        Call<ResponseModel<Boolean>> call = RestServiceFactory.createServiceUser().getUserStatus(mainUser.getUserId());
        call.enqueue(new RestCallBack<ResponseModel<Boolean>>(context) {
            @Override
            public void onFailure(Call<ResponseModel<Boolean>> call, String message) {
                loder.dismiss();
                ToastUtils.show(context, message);
                LogHelper.d("Failure", "onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<Boolean>> call, Response<ResponseModel<Boolean>> restResponse, ResponseModel<Boolean> response) {
                loder.dismiss();
                if (response.isSuccess) {
                    if (response.data) {
                        hitCompanyStatus(mainUser);

                    } else {
                        ToastUtils.show(context, "Your Account has been removed");

                    }
                } else {
                    ToastUtils.show(context, response.message);
                }
            }
        });
    }

    private void hitCompanyStatus(MainUser mainUser) {
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No Internet");
            return;
        }
        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().getCompanyStatus(mainUser.getCompanyId());
        call.enqueue(new RestCallBack<ResponseModel<String>>(context) {
            @Override
            public void onFailure(Call<ResponseModel<String>> call, String message) {
                loder.dismiss();
                ToastUtils.show(context, message);
                LogHelper.d("Failure", "onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> restResponse, ResponseModel<String> response) {
                loder.dismiss();
                if (response.isSuccess) {
                    if (response.data.equals("500")) {
                        Intent i = new Intent(context, VerifyActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(context, DashboardActivity.class);
                        startActivity(i);
                        finish();
                    }
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
        tv_forgot = findViewById(R.id.tv_forgot);
        ip_email = findViewById(R.id.ip_email);
        ip_password = findViewById(R.id.ip_password);
    }
}
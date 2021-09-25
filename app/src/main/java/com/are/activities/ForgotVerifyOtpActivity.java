package com.are.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.are.MyApp;
import com.are.R;
import com.are.model.MainUser;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.ui.OtpView;
import com.are.utils.DialogUtils;
import com.are.utils.NetworkUtil;
import com.are.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class ForgotVerifyOtpActivity extends AppCompatActivity {
    public ForgotVerifyOtpActivity context;
    public AppCompatButton btn_verify;
    public AppCompatTextView tv_mobile, tvTimer, tvResend;
    public OtpView otpView;
    public String mobileNo = "", otpText = "";
    public CountDownTimer countDownTimer;
    public Dialog loder;
    public ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_verify_otp);
        context = this;
        initView();

        try {
            mobileNo = getIntent().getStringExtra("mobileNo");
            otpText = getIntent().getStringExtra("otp");
            String mobile = mobileNo.substring(6, mobileNo.length());
            tv_mobile.setText(Html.fromHtml("Enter the code that's been sent to your<br>phone ending <b> <font color=\"#111B2B\">" + mobile + "</b>"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvResend.setEnabled(false);
        tvResend.setAlpha(0.5f);
        btn_verify.setAlpha(0.5F);
        btn_verify.setEnabled(false);

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitApiForResendOTP(mobileNo);
            }
        });
        otpView.setOtpComplite(new OtpView.OtpComplite() {
            @Override
            public void onOTPEnter() {
                btn_verify.setEnabled(true);
                btn_verify.setAlpha(1.0F);
            }
        });
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpInput = otpView.getOTP().toString().trim();
                if (otpInput.isEmpty()) {
                    ToastUtils.show(context, "Please Enter OTP");
                    return;
                }
                if (!otpInput.equals(otpText)) {
                    ToastUtils.show(context, "Please Enter Correct OTP");
                    return;
                }
                if (!NetworkUtil.getInstance(context).isConnected()) {
                    ToastUtils.show(context, "No internet");
                    return;
                }
                hitForgotMobileApi();
            }
        });

        otpTimer();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void hitForgotMobileApi() {
        loder = DialogUtils.showLoader(this);
        Call<ResponseModel<MainUser>> call = RestServiceFactory.createServiceUser().forgotPassMobile(mobileNo);
        call.enqueue(new RestCallBack<ResponseModel<MainUser>>(context) {
            @Override
            public void onFailure(Call<ResponseModel<MainUser>> call, String message) {
                loder.dismiss();
                ToastUtils.show(context, message);
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

                    Intent i = new Intent(context, ChangePassActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    ToastUtils.show(context, response.message);
                }
            }
        });
    }

    public void otpTimer() {
        countDownTimer = new CountDownTimer(120000, 500) {

            public void onTick(long millisUntilFinished) {

                String hours = String.format("%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)));

                String senc = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                tvTimer.setText(hours + ":" + senc);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tvResend.setEnabled(true);
                tvResend.setAlpha(1.0f);
            }

        }.start();
    }

    private void initView() {
        tvResend = findViewById(R.id.tvResend);
        tvTimer = findViewById(R.id.tvTimer);
        tv_mobile = findViewById(R.id.tv_mobile);
        otpView = findViewById(R.id.otp_view);
        btn_verify = findViewById(R.id.btn_verify);
        img_back = findViewById(R.id.img_back);
    }

    public void hitApiForResendOTP(String phoneText) {
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
                if (response.isSuccess) {
                    loder.dismiss();
                    otpText = response.data;
                } else {
                    ToastUtils.show(context, response.message);
                }
            }
        });
    }


}
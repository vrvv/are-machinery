package com.are.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.are.MyApp;
import com.are.R;
import com.are.model.rest_request.EditDetailRequest;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.ui.NoChangingBackgroundTextInputLayout;
import com.are.utils.DialogUtils;
import com.are.utils.LogUtils;
import com.are.utils.NetworkUtil;
import com.are.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Response;

public class EditDetailActivity extends AppCompatActivity {
    public EditDetailActivity instance;
    public AppCompatButton btn_save;
    public TextInputEditText et_name,  et_email;
    public NoChangingBackgroundTextInputLayout ip_name,  ip_email;
    public EditDetailRequest editDetailRequest;
    public Dialog loder;
    public ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail);
        instance = this;
        editDetailRequest = new EditDetailRequest();
        initView();
        et_email.setText(MyApp.user.getEmail());
        et_name.setText(MyApp.user.getName());
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }
    private void validation() {
        if (et_name.getText().toString().isEmpty()) {
            ip_name.setError("please enter name");
            return;
        }

        String emailText = et_email.getText().toString().trim();
        if (emailText.isEmpty()) {
            ip_email.setError("please enter email");
            ip_name.setError(null);
            return;
        }
        if (!LogUtils.isValidEmail(emailText)) {
            ip_email.setError("please enter valid email");
             ip_name.setError(null);
            return;
        }

        editDetailRequest.setUserId(MyApp.user.getUserId());
        editDetailRequest.setName(et_name.getText().toString());
        editDetailRequest.setEmail(emailText);
        hitEditDetailApi(editDetailRequest);
    }

    private void hitEditDetailApi(EditDetailRequest editDetailRequest) {
        if (!NetworkUtil.getInstance(instance).isConnected()) {
            ToastUtils.show(instance, "No internet");
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(editDetailRequest);
        Log.d("myTag", json);
        loder = DialogUtils.showLoader(this);

        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().editDetail(editDetailRequest);
        call.enqueue(new RestCallBack<ResponseModel<String>>(instance) {
            @Override
            public void onFailure(Call<ResponseModel<String>> call, String message) {
                loder.dismiss();
                ToastUtils.show(instance, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> restResponse, ResponseModel<String> response) {
                loder.dismiss();
                if (response.isSuccess) {
                    onBackPressed();
                    ToastUtils.show(instance, response.message);
                } else {
                    ToastUtils.show(instance, response.message);
                }
            }
        });
    }

    private void initView() {
        img_back = findViewById(R.id.img_back);
        btn_save = findViewById(R.id.btn_save);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        ip_name = findViewById(R.id.ip_name);
        ip_email = findViewById(R.id.ip_email);
      }
}
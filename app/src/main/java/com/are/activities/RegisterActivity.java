package com.are.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.are.MyApp;
import com.are.R;
import com.are.model.Company;
import com.are.model.rest_request.RegisterRequest;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.ui.NoChangingBackgroundTextInputLayout;
import com.are.utils.DialogUtils;
import com.are.utils.LogHelper;
import com.are.utils.LogUtils;
import com.are.utils.NetworkUtil;
import com.are.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    public RegisterActivity context;
    public AppCompatButton btn_signup;
    public AppCompatTextView tvSignup;
    public AppCompatAutoCompleteTextView act_company_name;
    public TextInputEditText et_name, et_email, et_mobile, et_gst_no;
    public NoChangingBackgroundTextInputLayout ip_name, ip_company_name, ip_email, ip_mobile, ip_gst_no;
    List<String> listComanyName = new ArrayList<>();
    List<Company> listComany = new ArrayList<>();
    public Dialog loder;
    private String otpText = "";
    public boolean isMobileValid = false;
    public boolean isEmailValid = false;
    public ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        initView();
        MyApp.registerRequest = new RegisterRequest();
        act_company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (act_company_name.getText().toString().length() >= 3) {
                    String searchVal = act_company_name.getText().toString().substring(0, 3);
                    runCompanyApi(searchVal);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();

            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });
        act_company_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyApp.registerRequest.setCompanyId(listComany.get(position).getCompanyId());
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
        if (et_name.getText().toString().isEmpty()) {
            ip_name.setError("please enter name");
            return;
        }
      /*  if (act_company_name.getText().toString().isEmpty()) {
            ip_company_name.setError("please type company name");
            ip_name.setError(null);
            return;
        }*/
        String emailText = et_email.getText().toString().trim();
        if (emailText.isEmpty()) {
            ip_email.setError("please enter email");
            ip_name.setError(null);
            ip_company_name.setError(null);
            return;
        }
        if (!LogUtils.isValidEmail(emailText)) {
            ip_email.setError("please enter valid email");
            ip_name.setError(null);
            ip_company_name.setError(null);
            return;
        }
        hitValidateEmail(emailText);
    }

    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_mobile = findViewById(R.id.et_mobile);
        et_gst_no = findViewById(R.id.et_gst_no);
        ip_name = findViewById(R.id.ip_name);
        ip_company_name = findViewById(R.id.ip_company_name);
        ip_email = findViewById(R.id.ip_email);
        ip_mobile = findViewById(R.id.ip_mobile);
        ip_gst_no = findViewById(R.id.ip_gst_no);
        act_company_name = findViewById(R.id.act_company_name);
        btn_signup = findViewById(R.id.btn_signup);
        tvSignup = findViewById(R.id.tvSignup);
    }

    private void runCompanyApi(String val) {
        if (!NetworkUtil.getInstance(context).isConnected()) {
            ToastUtils.show(context, "No internet");
            return;
        }
        Call<ResponseModel<List<Company>>> callCities = RestServiceFactory.createServiceUser().searchCompany(val);
        callCities.enqueue(new RestCallBack<ResponseModel<List<Company>>>(context) {
            @Override
            public void onFailure(Call<ResponseModel<List<Company>>> call, String message) {
                ToastUtils.show(context, message);
                LogHelper.d("Tag", "onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<List<Company>>> call, Response<ResponseModel<List<Company>>> restResponse, ResponseModel<List<Company>> response) {
                if (response.isSuccess) {
                    listComanyName.clear();
                    listComany.clear();
                    listComany.addAll(response.data);
                    for (int i = 0; i < response.data.size(); i++) {
                        listComanyName.add(response.data.get(i).getName());
                    }
                    ArrayAdapter adapter = new ArrayAdapter(context, R.layout.custom_spinner_text, R.id.tvSpinner, listComanyName);
                    act_company_name.setAdapter(adapter);
                    act_company_name.setThreshold(3);
                } else {
                    ToastUtils.show(context, response.message);
                }
            }
        });

    }

    private boolean hitValidateMobile(String phoneText) {
        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().validateMobile(phoneText);
        call.enqueue(new RestCallBack<ResponseModel<String>>(context) {
            @Override
            public void onFailure(Call<ResponseModel<String>> call, String message) {
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> restResponse, ResponseModel<String> response) {
                if (response.isSuccess) {
                    isMobileValid = true;
                } else {
                    isMobileValid = false;
                }
                if (!isMobileValid) {
                    ip_mobile.setError("Already mobile number exists");
                    ip_name.setError(null);
                    ip_company_name.setError(null);
                    ip_email.setError(null);
                    return;
                }
                MyApp.registerRequest.setName(et_name.getText().toString());
                MyApp.registerRequest.setCompanyId(0);
                MyApp.registerRequest.setCompanyName(act_company_name.getText().toString());
                MyApp.registerRequest.setEmail(et_email.getText().toString());
                MyApp.registerRequest.setMobile(phoneText);
                hitApiForOTP(phoneText);
            }
        });
        return isMobileValid;
    }

    public boolean hitValidateEmail(String email) {
        Map<String, String> data = new HashMap<>();
        data.put("id", email);
        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().validateEmail(data);
        call.enqueue(new RestCallBack<ResponseModel<String>>(context) {
            @Override
            public void onFailure(Call<ResponseModel<String>> call, String message) {
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> restResponse, ResponseModel<String> response) {
                if (response.isSuccess) {
                    isEmailValid = true;
                } else {
                    isEmailValid = false;
                }
                if (!isEmailValid) {
                    ip_email.setError("Already email exists");
                    ip_name.setError(null);
                    ip_company_name.setError(null);
                    return;
                }
                String phoneText = et_mobile.getText().toString().trim();
                if (phoneText.length() == 0) {
                    ip_mobile.setError("please enter phone number");
                    ip_name.setError(null);
                    ip_company_name.setError(null);
                    ip_email.setError(null);
                    return;
                }
                if (phoneText.length() != 10) {
                    ip_mobile.setError("phone number is invalid");
                    ip_name.setError(null);
                    ip_company_name.setError(null);
                    ip_email.setError(null);
                    return;
                }
                if (phoneText.length() < 10) {
                    ip_mobile.setError("please enter valid 10 digit mobile number");
                    ip_name.setError(null);
                    ip_company_name.setError(null);
                    ip_email.setError(null);
                    return;
                }
                hitValidateMobile(phoneText);
            }
        });
        return isEmailValid;
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
                if (response.isSuccess) {
                    loder.dismiss();
                    otpText = response.data;
                    startActivity(new Intent(context, OTPActivity.class)
                            .putExtra("mobileNo", phoneText)
                            .putExtra("otp", otpText));


                } else {
                    ToastUtils.show(context, response.message);
                }
            }
        });
    }

}
package com.are.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;

import com.are.MyApp;
import com.are.R;
import com.are.model.rest_request.AddEmployeeRequest;
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

public class AddEmplyoyeeActivity extends AppCompatActivity {
    public AddEmplyoyeeActivity instance;
    public AppCompatButton btn_save;
    public TextInputEditText et_name, et_mobile, et_email, et_city, et_password, et_role_no;
    public NoChangingBackgroundTextInputLayout ip_name, ip_mobile, ip_email, ip_password, ip_role, ip_city;
    public String[] roleArray = new String[]{"Manager", "Employee"};
    public int role_id = 0;
    public AddEmployeeRequest addEmployeeRequest;
    public Dialog loder;
    public ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emplyoyee);
        instance = this;
        addEmployeeRequest = new AddEmployeeRequest();
        initView();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        et_role_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    final PopupMenu popupMenu = new PopupMenu(instance, v);
                    for (int i = 0; i < roleArray.length; i++) {
                        popupMenu.getMenu().add(i, Menu.FIRST, i, roleArray[i]);

                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            et_role_no.setText(item.getTitle());
                            if (item.getTitle().equals("Manager")) {
                                role_id = 506;
                            } else {
                                role_id = 507;

                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
                return true; // return is important...
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
            ip_name.setError("Please Enter Name");
            return;
        }

        String phoneText = et_mobile.getText().toString().trim();
        if (phoneText.length() == 0) {
            ip_mobile.setError("Please Enter Phone Number");
            ip_name.setError(null);
            return;
        }
        if (phoneText.length() != 10) {
            ip_mobile.setError("Phone Number is Invalid");
            ip_name.setError(null);
            return;
        }
        if (phoneText.length() < 10) {
            ip_mobile.setError("Please Enter Valid 10 Digit Mobile Number");
            ip_name.setError(null);
            return;
        }
        String emailText = et_email.getText().toString().trim();
        if (emailText.isEmpty()) {
            ip_email.setError("Please Enter Email");
            ip_name.setError(null);
            ip_mobile.setError(null);
            return;
        }
        if (!LogUtils.isValidEmail(emailText)) {
            ip_email.setError("Please Enter Valid Email");
            ip_mobile.setError(null);
            ip_name.setError(null);
            return;
        }
        if (et_password.getText().toString().isEmpty()) {
            ip_password.setError("Please Enter Password");
            ip_mobile.setError(null);
            ip_name.setError(null);
            ip_email.setError(null);
            return;
        }
        if (et_city.getText().toString().isEmpty()) {
            ip_city.setError("Please Enter City");
            ip_role.setError(null);
            ip_password.setError(null);
            ip_mobile.setError(null);
            ip_name.setError(null);
            ip_email.setError(null);
            return;
        }
        if (et_role_no.getText().toString().isEmpty()) {
            ip_role.setError("Please Select Role");
            ip_password.setError(null);
            ip_mobile.setError(null);
            ip_name.setError(null);
            ip_email.setError(null);
            return;
        }
        addEmployeeRequest.setCompanyId(MyApp.user.getCompanyId());
        addEmployeeRequest.setName(et_name.getText().toString());
        addEmployeeRequest.setPassword(et_password.getText().toString());
        addEmployeeRequest.setEmail(emailText);
        addEmployeeRequest.setMobile(phoneText);
        addEmployeeRequest.setRoleId(role_id);
        addEmployeeRequest.setCity(et_city.getText().toString());
        hitAddEmployeeApi(addEmployeeRequest);
    }

    private void hitAddEmployeeApi(AddEmployeeRequest addEmployeeRequest) {
        if (!NetworkUtil.getInstance(instance).isConnected()) {
            ToastUtils.show(instance, "No internet");
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(addEmployeeRequest);
        Log.d("myTag", json);
        loder = DialogUtils.showLoader(this);

        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().addEmployee(addEmployeeRequest);
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
        et_city = findViewById(R.id.et_city);
        et_name = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_mobile);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_role_no = findViewById(R.id.et_role_no);
        ip_name = findViewById(R.id.ip_name);
        ip_mobile = findViewById(R.id.ip_mobile);
        ip_email = findViewById(R.id.ip_email);
        ip_password = findViewById(R.id.ip_password);
        ip_city = findViewById(R.id.ip_city);
        ip_role = findViewById(R.id.ip_role);
    }
}
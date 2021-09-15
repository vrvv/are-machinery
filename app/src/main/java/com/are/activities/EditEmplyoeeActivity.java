package com.are.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.PopupMenu;

import com.are.MyApp;
import com.are.R;
import com.are.model.Employees;
import com.are.model.rest_request.AddEmployeeRequest;
import com.are.model.rest_request.EditEmployeeRequest;
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

public class EditEmplyoeeActivity extends AppCompatActivity {
    public EditEmplyoeeActivity instance;
    public AppCompatButton btn_save;
    public TextInputEditText et_name, et_mobile, et_email, et_role_no;
    public NoChangingBackgroundTextInputLayout ip_name, ip_mobile, ip_email, ip_role;
    public AppCompatCheckBox chk_active;
    public String[] roleArray = new String[]{"Manager", "Employee"};
    public int role_id = 0;
    public EditEmployeeRequest editEmployeeRequest;
    public Dialog loder;
    public ImageView img_back;
    public boolean isEdit = false;
    public Employees employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emplyoee);
        instance = this;


        initView();
        editEmployeeRequest = new EditEmployeeRequest();
        employees = (Employees) getIntent().getSerializableExtra("employees");
        et_name.setText(employees.getName());
        et_mobile.setText(employees.getMobile());
        et_email.setText(employees.getEmail());
        et_role_no.setText(employees.getRole());


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        chk_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editEmployeeRequest.setActive(isChecked);
                } else {
                    editEmployeeRequest.setActive(isChecked);

                }
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
            ip_name.setError("please enter name");
            return;
        }

        String phoneText = et_mobile.getText().toString().trim();
        if (phoneText.length() == 0) {
            ip_mobile.setError("please enter phone number");
            ip_name.setError(null);
            return;
        }
        if (phoneText.length() != 10) {
            ip_mobile.setError("phone number is invalid");
            ip_name.setError(null);
            return;
        }
        if (phoneText.length() < 10) {
            ip_mobile.setError("please enter valid 10 digit mobile number");
            ip_name.setError(null);
            return;
        }
        String emailText = et_email.getText().toString().trim();
        if (emailText.isEmpty()) {
            ip_email.setError("please enter email");
            ip_name.setError(null);
            ip_mobile.setError(null);
            return;
        }
        if (!LogUtils.isValidEmail(emailText)) {
            ip_email.setError("please enter valid email");
            ip_mobile.setError(null);
            ip_name.setError(null);
            return;
        }


        if (et_role_no.getText().toString().isEmpty()) {
            ip_role.setError("please select role");
            ip_mobile.setError(null);
            ip_name.setError(null);
            ip_email.setError(null);
            return;
        }
        editEmployeeRequest.setUserId(employees.getUserId());
        editEmployeeRequest.setName(et_name.getText().toString());
        editEmployeeRequest.setEmail(emailText);
        editEmployeeRequest.setMobile(phoneText);
        editEmployeeRequest.setRoleId(role_id);
        hitAddEmployeeApi(editEmployeeRequest);
    }

    private void hitAddEmployeeApi(EditEmployeeRequest addEmployeeRequest) {
        if (!NetworkUtil.getInstance(instance).isConnected()) {
            ToastUtils.show(instance, "No internet");
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(addEmployeeRequest);
        Log.d("myTag", json);

        loder = DialogUtils.showLoader(this);
        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().editEmployee(addEmployeeRequest);


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
        chk_active = findViewById(R.id.chk_active);
        btn_save = findViewById(R.id.btn_save);
        et_name = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_mobile);
        et_email = findViewById(R.id.et_email);
        et_role_no = findViewById(R.id.et_role_no);
        ip_name = findViewById(R.id.ip_name);
        ip_mobile = findViewById(R.id.ip_mobile);
        ip_email = findViewById(R.id.ip_email);
        ip_role = findViewById(R.id.ip_role);
    }
}
package com.are.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.are.MyApp;
import com.are.R;
import com.are.adapter.EmployeeAdapter;
import com.are.model.Employees;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.utils.LogUtils;
import com.are.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class EmployeesListActivity extends AppCompatActivity {
    public EmployeesListActivity instance;
    public RecyclerView rvEmployee;
    public EmployeeAdapter employeeAdapter;
    public List<Employees> employeesArrayList = new ArrayList<>();
    public ImageView img_back;
    public AppCompatButton btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_list);
        instance = this;
        btn_add = findViewById(R.id.btn_add);
        img_back = findViewById(R.id.img_back);
        rvEmployee = findViewById(R.id.rvEmployee);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(instance, AddEmplyoyeeActivity.class));

            }
        });
        hitEmployeeApi();
    }

    private void hitEmployeeApi() {
        Call<ResponseModel<List<Employees>>> callStates = RestServiceFactory.createServiceUser().getMyEmployee(MyApp.user.getCompanyId());

        callStates.enqueue(new RestCallBack<ResponseModel<List<Employees>>>(instance) {
            @Override
            public void onFailure(Call<ResponseModel<List<Employees>>> call, String message) {
                ToastUtils.show(instance, message);
                LogUtils.api("onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<List<Employees>>> call, Response<ResponseModel<List<Employees>>> restResponse, ResponseModel<List<Employees>> response) {
                if (response.isSuccess) {
                    employeesArrayList.clear();
                    employeesArrayList.addAll(response.data);
                    if (employeesArrayList.size() > 0) {
                        employeeAdapter =
                                new EmployeeAdapter(instance, employeesArrayList);
                        rvEmployee.setHasFixedSize(true);
                        rvEmployee.setLayoutManager(new LinearLayoutManager(instance));
                        rvEmployee.setAdapter(employeeAdapter);
                        rvEmployee.setNestedScrollingEnabled(false);

                    } else {

                    }
                } else {
                    ToastUtils.show(instance, response.message);
                }
            }
        });

    }
}
package com.are.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.are.MyApp;
import com.are.R;
import com.are.adapter.RecomMachineryAdapter;
import com.are.drawer.FragmentDrawer;
import com.are.model.Items;
import com.are.model.rest_request.GetItemRequest;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.utils.LogUtils;
import com.are.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    public DashboardActivity instance;
    public RecyclerView rvRecomMachinery;
    public TextView tv_sell_here;
    public AppCompatButton btn_post_request;
    public GetItemRequest getItemRequest;
    public List<Items> itemsArrayList = new ArrayList<>();
    private FragmentDrawer drawerFragment;
    public ImageView img_menu;
    public LinearLayout lin_everything, lin_spares, lin_equipment,lin_logout;
    public RecomMachineryAdapter recomMachineryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        instance = this;
        getItemRequest = new GetItemRequest();
        drawerFragment = (FragmentDrawer) getFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        img_menu = findViewById(R.id.img_menu);
        setupDrawer();
        lin_everything = findViewById(R.id.lin_everything);
        lin_spares = findViewById(R.id.lin_spares);
        lin_logout = findViewById(R.id.lin_logout);
        lin_equipment = findViewById(R.id.lin_equipment);
        tv_sell_here = findViewById(R.id.tv_sell_here);
        btn_post_request = findViewById(R.id.btn_post_request);
        rvRecomMachinery = findViewById(R.id.rvRecomMachinery);
        getItemList("1");
        tv_sell_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, SellActivity.class);
                startActivity(intent);
            }
        });
        btn_post_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instance, EnquireActivity.class);
                startActivity(intent);
            }
        });
        lin_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutPopUp();
            }
        });

    }

    public void setupDrawer() {
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), img_menu);
    }

    public void showLogoutPopUp() {

        final Dialog dialog = new Dialog(instance);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dailog_logout);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView btnNo = (TextView) dialog.findViewById(R.id.btnNo);
        TextView btnYes = (TextView) dialog.findViewById(R.id.btnYes);

        if (dialog.getWindow() != null) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = dialog.getWindow();
            lp.copyFrom(window.getAttributes());
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            lp.width = (int) (metrics.widthPixels * 0.90);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp.mySharedPref.setUserId("");
                MyApp.mySharedPref.setLocationid("");
                MyApp.mySharedPref.setIsLoggedIn(false);
                MyApp.mySharedPref.clearApp();
                startActivity(new Intent(instance, LoginActivity.class));
                finish();
                dialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void getItemList(String type) {
        getItemRequest.setBrand("");
        getItemRequest.setFromCount(0);
        getItemRequest.setToCount(6);
        getItemRequest.setItemType(type);
        getItemRequest.setLocation("Hyderabad");
        getItemRequest.setUserid(Integer.parseInt(MyApp.mySharedPref.getUserId()));
        Call<ResponseModel<List<Items>>> callStates = RestServiceFactory.createServiceUser().getItem(getItemRequest);

        callStates.enqueue(new RestCallBack<ResponseModel<List<Items>>>(instance) {
            @Override
            public void onFailure(Call<ResponseModel<List<Items>>> call, String message) {
                ToastUtils.show(instance, message);
                LogUtils.api("onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<List<Items>>> call, Response<ResponseModel<List<Items>>> restResponse, ResponseModel<List<Items>> response) {
                if (response.isSuccess) {
                    itemsArrayList.clear();
                    itemsArrayList.addAll(response.data);
                    if (itemsArrayList.size() > 0) {
                        setupRecyclerview();

                    }
                } else {
                    ToastUtils.show(instance, response.message);
                }
            }
        });

    }

    private void setupRecyclerview() {
        recomMachineryAdapter =
                new RecomMachineryAdapter(instance, itemsArrayList);
        rvRecomMachinery.setHasFixedSize(true);
        rvRecomMachinery.setLayoutManager(new GridLayoutManager(instance,
                2));
        rvRecomMachinery.setAdapter(recomMachineryAdapter);
        rvRecomMachinery.setNestedScrollingEnabled(false);
        recomMachineryAdapter.notifyDataSetChanged();
    }

    public void onClickEverything(View view) {
        lin_everything.setBackground(getResources().getDrawable(R.drawable.rounded_light_gray));
        lin_equipment.setBackground(getResources().getDrawable(R.drawable.rounded_light_blue_ring));
        lin_spares.setBackground(getResources().getDrawable(R.drawable.rounded_light_blue_ring));
        Intent intent = new Intent(instance, MachineryActivity.class);
        intent.putExtra("type", "0");
        startActivity(intent);
    }

    public void onClickEquipment(View view) {
        lin_everything.setBackground(getResources().getDrawable(R.drawable.rounded_light_blue_ring));
        lin_equipment.setBackground(getResources().getDrawable(R.drawable.rounded_light_gray));
        lin_spares.setBackground(getResources().getDrawable(R.drawable.rounded_light_blue_ring));
        Intent intent = new Intent(instance, MachineryActivity.class);
        intent.putExtra("type", "1");
        startActivity(intent);
        //  getItemList("1");
    }

    public void onClickSpares(View view) {
        lin_everything.setBackground(getResources().getDrawable(R.drawable.rounded_light_blue_ring));
        lin_equipment.setBackground(getResources().getDrawable(R.drawable.rounded_light_blue_ring));
        lin_spares.setBackground(getResources().getDrawable(R.drawable.rounded_light_gray));
        Intent intent = new Intent(instance, MachineryActivity.class);
        intent.putExtra("type", "2");
        startActivity(intent);
        //  getItemList("2");
    }
}
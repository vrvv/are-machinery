package com.are.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.are.MyApp;
import com.are.R;
import com.are.adapter.ItemListAdapter;
import com.are.adapter.RecomMachineryAdapter;
import com.are.fragment.ProductFragment;
import com.are.model.Items;
import com.are.model.rest_request.GetItemRequest;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.utils.LogUtils;
import com.are.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MachineryActivity extends AppCompatActivity {
    public MachineryActivity instance;
    private static final String DOG_BREEDS[] = {"Products", "Suppliers"};
    public String type = "";
    public GetItemRequest getItemRequest;
    public List<Items> itemsArrayList = new ArrayList<>();
    public ItemListAdapter recomMachineryAdapter;
    public RecyclerView rvItemList;
    public ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machinery);
        instance = this;
        getItemRequest = new GetItemRequest();
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        type = getIntent().getStringExtra("type");
        rvItemList = findViewById(R.id.rvItemList);
        getItemList(type);
       /* viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
  */
    }

    private void getItemList(String type) {
        getItemRequest.setBrand("");
        getItemRequest.setFromCount(0);
        getItemRequest.setToCount(10);
        getItemRequest.setItemType(type);
        getItemRequest.setLocation("Hyderabad");
        getItemRequest.setUserid(Integer.parseInt(MyApp.mySharedPref.getUserId()));
        Call<ResponseModel<List<Items>>> callStates = RestServiceFactory.createServiceUser().getItem(getItemRequest);
        Gson gson = new Gson();
        String json = gson.toJson(getItemRequest);
        Log.d("myTag", json);

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
        }); }

    private void setupRecyclerview() {
        recomMachineryAdapter =
                new ItemListAdapter(instance, itemsArrayList);
        rvItemList.setHasFixedSize(true);
        rvItemList.setLayoutManager(new LinearLayoutManager(instance));
        rvItemList.setAdapter(recomMachineryAdapter);
        rvItemList.setNestedScrollingEnabled(false);
        recomMachineryAdapter.notifyDataSetChanged();
    }
    public class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return new ProductFragment();
        }

        @Override
        public int getCount() {
            return DOG_BREEDS.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return DOG_BREEDS[position];
        }
    }
}
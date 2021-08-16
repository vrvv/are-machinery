package com.are.activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.are.MyApp;
import com.are.R;
import com.are.adapter.ImageAdapterExample;
import com.are.model.ItemDetails;
import com.are.model.Items;
import com.are.model.rest_request.AddEnquiryRequest;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.utils.DialogUtils;
import com.are.utils.LogHelper;
import com.are.utils.NetworkUtil;
import com.are.utils.ToastUtils;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    public ProductDetailActivity instance;
    SliderView sliderView;
    ImageAdapterExample adapter;
    ArrayList<String> dashboardImageArrayList = new ArrayList<>();
    public AppCompatButton btn_enquiry;
    public Items items ;
    public TextView tvname, tvUnitPrice, tvCompanyName, tvLocation, tvModel, tvBrand, tvYear, tvFunction;
    public BottomSheetDialog mBottomSheetEnquire, mBottomSheetEnquireSuccess;
    public AddEnquiryRequest addEnquiryRequest;
    public Dialog loder;
    public ItemDetails itemDetails;
    public ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        instance = this;
        addEnquiryRequest = new AddEnquiryRequest();
        itemDetails = new ItemDetails();
        items = (Items) getIntent().getSerializableExtra("itemId");
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_enquiry = findViewById(R.id.btn_enquiry);
        sliderView = findViewById(R.id.imageSlider);
        tvUnitPrice = findViewById(R.id.tvUnitPrice);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvname = findViewById(R.id.tvname);
        tvLocation = findViewById(R.id.tvLocation);
        tvModel = findViewById(R.id.tvModel);
        tvBrand = findViewById(R.id.tvBrand);
        tvYear = findViewById(R.id.tvYear);
        tvFunction = findViewById(R.id.tvFunction);
        getDetailApi();
        bottomSelectBusinessType();
        bottomEnquireSuccess();
        btn_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetEnquire.show();

          /*      Intent intent = new Intent(instance, HistoryActivity.class);
                startActivity(intent);
         */
            }
        });
    }

    private void bottomEnquireSuccess() {
        View view = getLayoutInflater().inflate(R.layout.bottom_enquiry_success, null);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setAlignItems(AlignItems.FLEX_START);

        mBottomSheetEnquireSuccess = new BottomSheetDialog(instance, R.style.DialogStyle);
        mBottomSheetEnquireSuccess.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetEnquireSuccess.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        ImageView img_close = view.findViewById(R.id.img_close);
        TextView tvname = view.findViewById(R.id.tvname);
        TextView tvnumber = view.findViewById(R.id.tvnumber);
        TextView tv_call = view.findViewById(R.id.tv_call);
        tvname.setText(itemDetails.getName());
        tvnumber.setText(itemDetails.getContact());
        tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + itemDetails.getContact()));
                startActivity(intent);

            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetEnquireSuccess.dismiss();
            }
        });
        Button btn_close = view.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetEnquireSuccess.dismiss();
            }
        });
    }

    public void bottomSelectBusinessType() {

        View view = getLayoutInflater().inflate(R.layout.bottom_enquire, null);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setAlignItems(AlignItems.FLEX_START);

        mBottomSheetEnquire = new BottomSheetDialog(instance, R.style.DialogStyle);
        mBottomSheetEnquire.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetEnquire.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        AppCompatCheckBox chkPrice = view.findViewById(R.id.chkPrice);
        AppCompatCheckBox chkQuery = view.findViewById(R.id.chkQuery);
        LinearLayout lin_price = view.findViewById(R.id.lin_price);
        LinearLayout lin_query = view.findViewById(R.id.lin_query);
        ImageView img_close = view.findViewById(R.id.img_close);
        Button btn_submit = view.findViewById(R.id.btn_submit);
        TextInputEditText et_query = view.findViewById(R.id.et_query);
        TextInputEditText et_price = view.findViewById(R.id.et_price);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetEnquire.dismiss();
            }
        });

        chkQuery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lin_query.setVisibility(View.VISIBLE);
                    chkPrice.setChecked(false);
                    lin_price.setVisibility(View.GONE);
                }
            }
        });
        chkPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lin_price.setVisibility(View.VISIBLE);
                    chkQuery.setChecked(false);
                    lin_query.setVisibility(View.GONE);
                }
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chkQuery.isChecked() && !chkPrice.isChecked()) {
                    ToastUtils.show(instance, "Please check atleast one.");
                } else {
                    if (chkQuery.isChecked()) {
                        if (et_query.getText().toString().isEmpty()) {
                            ToastUtils.show(instance, "Please enter your additional requests about this product.");
                        } else {
                            addEnquiryRequest.setRemarks(et_query.getText().toString());
                            addEnquiryRequest.setItemId(items.getEquipmentId());
                            addEnquiryRequest.setUserId(MyApp.mySharedPref.getUserId());
                            addEnquiryRequest.setPrice("0");
                            hitEnquiryApi(addEnquiryRequest);


                        }
                    } else {
                        if (et_price.getText().toString().isEmpty()) {
                            ToastUtils.show(instance, "Please enter your price.");
                        } else {
                            addEnquiryRequest.setRemarks("");
                            addEnquiryRequest.setItemId(items.getEquipmentId());
                            addEnquiryRequest.setUserId(MyApp.mySharedPref.getUserId());
                            addEnquiryRequest.setPrice(et_price.getText().toString());
                            hitEnquiryApi(addEnquiryRequest);

                        }
                    }

                }
            }
        });

    }

    private void hitEnquiryApi(AddEnquiryRequest addEnquiryRequest) {
        if (!NetworkUtil.getInstance(instance).isConnected()) {
            ToastUtils.show(instance, "No internet");
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(MyApp.registerRequest);
        Log.d("myTag", json);
        loder = DialogUtils.showLoader(this);

        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().addEnquiry(addEnquiryRequest);
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
                    mBottomSheetEnquire.dismiss();
                    mBottomSheetEnquireSuccess.show();
                    ToastUtils.show(instance, response.message);
                } else {
                    ToastUtils.show(instance, response.message);
                }
            }
        });
    }


    private void getDetailApi() {
        if (!NetworkUtil.getInstance(instance).isConnected()) {
            ToastUtils.show(instance, "No internet");
            return;
        }
        Call<ResponseModel<ItemDetails>> callCities = RestServiceFactory.createServiceUser().getItemDetail(items.getEquipmentId(), MyApp.mySharedPref.getUserId());
        callCities.enqueue(new RestCallBack<ResponseModel<ItemDetails>>(instance) {
            @Override
            public void onFailure(Call<ResponseModel<ItemDetails>> call, String message) {
                ToastUtils.show(instance, message);
                LogHelper.d("Tag", "onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<ItemDetails>> call, Response<ResponseModel<ItemDetails>> restResponse, ResponseModel<ItemDetails> response) {
                if (response.isSuccess) {
                    itemDetails = response.data;
                    for (int i = 0; i < response.data.getImages().size(); i++) {
                        dashboardImageArrayList.add(response.data.getImages().get(i).getUrl());

                    }
                    adapter = new ImageAdapterExample(ProductDetailActivity.this, dashboardImageArrayList);
                    sliderView.setSliderAdapter(adapter);
                    sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                    sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                    sliderView.setIndicatorSelectedColor(R.color.black);
                    sliderView.setIndicatorUnselectedColor(R.color.white);
                    sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                    sliderView.startAutoCycle();

                    tvname.setText(response.data.getName());
                    tvUnitPrice.setText(response.data.getUnitPrice() + "");
                    tvCompanyName.setText(response.data.getCompanyName());
                    tvLocation.setText(response.data.getLocation());
                    tvModel.setText(response.data.getModel());
                    tvBrand.setText(response.data.getBrand());
                    tvYear.setText(response.data.getYear());
                    tvFunction.setText(response.data.getFunctional() + " " + response.data.getFunctionalType());
                } else {
                    ToastUtils.show(instance, response.message);
                }
            }
        });
    }
}
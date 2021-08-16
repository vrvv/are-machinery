package com.are.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;

import com.are.MyApp;
import com.are.R;
import com.are.model.Equipments;
import com.are.model.rest_request.AddItemRequest;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.ui.NoChangingBackgroundTextInputLayout;
import com.are.utils.DialogUtils;
import com.are.utils.LogHelper;
import com.are.utils.NetworkUtil;
import com.are.utils.ToastUtils;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Response;

import static android.os.Environment.DIRECTORY_DCIM;

public class AddEquipmentItemActivity extends AppCompatActivity {
    public AddEquipmentItemActivity instance;
    public AppCompatButton btn_publish;
    public List<Equipments> equipmentsList = new ArrayList<>();
    public TextInputEditText et_type, et_name, et_brand_name, et_model, et_year, et_functional, et_quantity, et_unit_price, et_location, et_description;
    public NoChangingBackgroundTextInputLayout ip_type, ip_brand_name, ip_model, ip_year,ip_location, ip_quantity, ip_unit_price,
            ip_description;
    public AddItemRequest addItemRequest;
    public int equipmentType = 0;
    public RelativeLayout rel_image1, rel_image2, rel_image3, rel_image4,
            rel_image5, rel_image6, rel_image7, rel_image8, rel_video;
    public ShapeableImageView img_one, img_two, img_three, img_four,
            img_five, img_six, img_seven, img_eight;
    public int PICK_IMAGE = 1;
    public Uri selectedUri;
    public ArrayList<String> returnValue = new ArrayList<>();
    public ArrayList<File> seletedFileArray = new ArrayList<>();
    public ArrayList<File> seletedVideoFileArray = new ArrayList<>();
    private static final int SELECT_VIDEO = 3;
    public LinearLayout linName;
    public Spinner spinner_functional;
    public TextView tvVideoUpload;
    public String functional_type = "KM";
    public Dialog loder;
    public ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_type);
        instance = this;
        addItemRequest = new AddItemRequest();
        initView();
        hitEquipmentListApi();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        et_type.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    final PopupMenu popupMenu = new PopupMenu(instance, v);
                    for (int i = 0; i < equipmentsList.size(); i++) {
                        popupMenu.getMenu().add(i, Menu.FIRST, i, equipmentsList.get(i).getName());

                    }
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            et_type.setText(item.getTitle());
                            if (item.getTitle().equals("Other")) {
                                linName.setVisibility(View.VISIBLE);
                            } else {
                                linName.setVisibility(View.GONE);

                            }
                            equipmentType = item.getItemId();
                            return false;
                        }
                    });
                    popupMenu.show();
                }
                return true; // return is important...
            }
        });
        spinner_functional.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    functional_type = "KM";
                } else if (position == 1) {
                    functional_type = "HR";

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rel_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
            }
        });
        rel_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_IMAGE = 101;
                CropImage.startPickImageActivity(instance);

            }
        });
        rel_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_IMAGE = 102;
                CropImage.startPickImageActivity(instance);

            }
        });
        rel_image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_IMAGE = 103;
                CropImage.startPickImageActivity(instance);

            }
        });
        rel_image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_IMAGE = 104;
                CropImage.startPickImageActivity(instance);

            }
        });
        rel_image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_IMAGE = 105;
                CropImage.startPickImageActivity(instance);

            }
        });
        rel_image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_IMAGE = 106;
                CropImage.startPickImageActivity(instance);

            }
        });
        rel_image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_IMAGE = 107;
                CropImage.startPickImageActivity(instance);

            }
        });
        rel_image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_IMAGE = 108;
                CropImage.startPickImageActivity(instance);

            }
        });
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
               /* Intent intent = new Intent(instance, UnderReviewActivity.class);
                startActivity(intent);
           */
            }
        });
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setShowCropOverlay(false)
                .setFixAspectRatio(true)
                .setMultiTouchEnabled(true)
                .start(instance);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
                Uri imageUri = CropImage.getPickImageResultUri(instance, data);
                Log.i("profImagePath", "==>" + imageUri.getPath());
                if (CropImage.isReadExternalStoragePermissionsRequired(instance, imageUri)) {
                    selectedUri = imageUri;

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                } else {
                    startCropImageActivity(imageUri);
                }
            }
            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                tvVideoUpload.setVisibility(View.VISIBLE);
                generateVideoName();
                goforVideoIt();
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    selectedUri = result.getUri();
                    if (PICK_IMAGE == 101) {
                        img_one.setImageURI(selectedUri);
                        generateImageName();
                    } else if (PICK_IMAGE == 102) {
                        img_two.setImageURI(selectedUri);
                        generateImageName();
                    } else if (PICK_IMAGE == 103) {
                        img_three.setImageURI(selectedUri);
                        generateImageName();
                    } else if (PICK_IMAGE == 104) {
                        img_four.setImageURI(selectedUri);
                        generateImageName();
                    } else if (PICK_IMAGE == 105) {
                        img_five.setImageURI(selectedUri);
                        generateImageName();
                    } else if (PICK_IMAGE == 106) {
                        img_six.setImageURI(selectedUri);
                        generateImageName();
                    } else if (PICK_IMAGE == 107) {
                        img_seven.setImageURI(selectedUri);
                        generateImageName();
                    } else if (PICK_IMAGE == 108) {
                        img_eight.setImageURI(selectedUri);
                        generateImageName();
                    }


                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.i("profImagePath", "==> error");
                }
            }
        }
    }

    private void generateImageName() {
        String uniqueId = UUID.randomUUID().toString();
        String fileName = uniqueId + ".jpg";
        seletedFileArray.add(new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM) + "/" + fileName));
    }

    private void generateVideoName() {
        String uniqueId = UUID.randomUUID().toString();
        String fileName = uniqueId + ".mp4";
        seletedVideoFileArray.add(new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM) + "/" + fileName));
    }

    public void goforIt() {

        FTPClient con = null;
        try {
            con = new FTPClient();
            con.connect("182.50.132.51");

            if (con.login("areftp", "9vT7e!n3")) {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);
                for (int i = 0; i < seletedFileArray.size(); i++) {
                    String data = seletedFileArray.get(i).getAbsolutePath();

                    FileInputStream in = new FileInputStream(new File(data));
                    boolean result = con.storeFile("/images/" + seletedFileArray.get(i).getName(), in);

                    in.close();
                    if (result) Log.v("upload result", "succeeded");
                    con.logout();
                    con.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goforVideoIt() {

        FTPClient con = null;
        try {
            con = new FTPClient();
            con.connect("182.50.132.51");

            if (con.login("areftp", "9vT7e!n3")) {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);
                for (int i = 0; i < seletedVideoFileArray.size(); i++) {
                    String data = seletedVideoFileArray.get(i).getAbsolutePath();

                    FileInputStream in = new FileInputStream(new File(data));
                    boolean result = con.storeFile("/videos/" + seletedVideoFileArray.get(i).getName(), in);

                    in.close();
                    if (result) Log.v("upload result", "succeeded");
                    con.logout();
                    con.disconnect();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validation() {
        if (et_type.getText().toString().isEmpty()) {
            ip_type.setError("Please select Type");
            return;
        }
        if (et_brand_name.getText().toString().isEmpty()) {
            ip_brand_name.setError("Please enter Brand name");
            ip_type.setError(null);
            return;
        }
        if (et_model.getText().toString().isEmpty()) {
            ip_model.setError("Please enter Model name");
            ip_brand_name.setError(null);
            ip_type.setError(null);
            return;
        }
        if (et_year.getText().toString().isEmpty()) {
            ip_year.setError("Please enter Year");
            ip_model.setError(null);
            ip_brand_name.setError(null);
            ip_type.setError(null);
            return;
        }
        if (et_functional.getText().toString().isEmpty()) {
            ToastUtils.show(instance, "Please enter Functional");
            ip_year.setError(null);
            ip_model.setError(null);
            ip_brand_name.setError(null);
            ip_type.setError(null);
            return;
        }
        if (et_quantity.getText().toString().isEmpty()) {
            ip_quantity.setError("Please enter Quantity");
            ip_year.setError(null);
            ip_model.setError(null);
            ip_brand_name.setError(null);
            ip_type.setError(null);
            return;
        }
        if (et_unit_price.getText().toString().isEmpty()) {
            ip_unit_price.setError("Please enter Unit price");
            ip_quantity.setError(null);
            ip_year.setError(null);
            ip_model.setError(null);
            ip_brand_name.setError(null);
            ip_type.setError(null);
            return;
        }
        if (et_location.getText().toString().isEmpty()) {
            ip_location.setError("Please enter Location");
            ip_unit_price.setError(null);
            ip_quantity.setError(null);
            ip_model.setError(null);
            ip_brand_name.setError(null);
            return;
        }
        if (et_description.getText().toString().isEmpty()) {
            ip_description.setError("Please enter description");
            ip_unit_price.setError(null);
            ip_quantity.setError(null);
            ip_year.setError(null);
            ip_model.setError(null);
            ip_brand_name.setError(null);
            ip_type.setError(null);
            return;
        }
        goforIt();
        addItemRequest.setEquipmentType(equipmentType);
        addItemRequest.setName(et_name.getText().toString());
        addItemRequest.setBrand(et_brand_name.getText().toString());
        addItemRequest.setModel(et_model.getText().toString());
        addItemRequest.setYear(et_year.getText().toString());
        addItemRequest.setFunctional(et_functional.getText().toString());
        addItemRequest.setFunctionalType(functional_type);
        addItemRequest.setQuantity(et_quantity.getText().toString());
        addItemRequest.setUnitPrice(et_unit_price.getText().toString());
        addItemRequest.setLocation(et_location.getText().toString());
        addItemRequest.setDescription(et_description.getText().toString());
        addItemRequest.setItemType("1");
        addItemRequest.setCreatedBy(MyApp.mySharedPref.getUserId());
        for (int i = 0; i < seletedFileArray.size(); i++) {
            AddItemRequest.Images aI = new AddItemRequest.Images();
            aI.setUrl("/images/" + seletedFileArray.get(i).getName());
            aI.setPositionType(i + 1);
            addItemRequest.images.add(aI);
        }
        for (int i = 0; i < seletedVideoFileArray.size(); i++) {
            AddItemRequest.Video aI = new AddItemRequest.Video();
            aI.setUrl("/videos/" + seletedVideoFileArray.get(i).getName());
            addItemRequest.video.add(aI);
        }
        hitEnquiryApi(addItemRequest);

    }

    private void hitEnquiryApi(AddItemRequest addItemRequest) {
        if (!NetworkUtil.getInstance(instance).isConnected()) {
            ToastUtils.show(instance, "No internet");
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(addItemRequest);
        Log.d("myTag", json);
        loder = DialogUtils.showLoader(this);

        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().addItem(addItemRequest);
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
                    Intent intent = new Intent(instance, UnderReviewActivity.class);
                    startActivity(intent);
                    finish();
                    ToastUtils.show(instance, response.message);
                } else {
                    ToastUtils.show(instance, response.message);
                }
            }
        });
    }

    private void initView() {
        tvVideoUpload = findViewById(R.id.tvVideoUpload);
        spinner_functional = findViewById(R.id.spinner_functional);
        ip_location = findViewById(R.id.ip_location);
        et_location = findViewById(R.id.et_location);
        linName = findViewById(R.id.linName);
        rel_video = findViewById(R.id.rel_video);
        img_one = findViewById(R.id.img_one);
        img_two = findViewById(R.id.img_two);
        img_three = findViewById(R.id.img_three);
        img_four = findViewById(R.id.img_four);
        img_five = findViewById(R.id.img_five);
        img_six = findViewById(R.id.img_six);
        img_seven = findViewById(R.id.img_seven);
        img_eight = findViewById(R.id.img_eight);
        rel_image1 = findViewById(R.id.rel_image1);
        rel_image2 = findViewById(R.id.rel_image2);
        rel_image3 = findViewById(R.id.rel_image3);
        rel_image4 = findViewById(R.id.rel_image4);
        rel_image5 = findViewById(R.id.rel_image5);
        rel_image6 = findViewById(R.id.rel_image6);
        rel_image7 = findViewById(R.id.rel_image7);
        rel_image8 = findViewById(R.id.rel_image8);
        btn_publish = findViewById(R.id.btn_publish);
        et_type = findViewById(R.id.et_type);
        et_name = findViewById(R.id.et_name);
        et_brand_name = findViewById(R.id.et_brand_name);
        et_model = findViewById(R.id.et_model);
        et_year = findViewById(R.id.et_year);
        et_functional = findViewById(R.id.et_functional);
        et_quantity = findViewById(R.id.et_quantity);
        et_unit_price = findViewById(R.id.et_unit_price);
        et_location = findViewById(R.id.et_location);
        et_description = findViewById(R.id.et_description);
        ip_type = findViewById(R.id.ip_type);
        ip_brand_name = findViewById(R.id.ip_brand_name);
        ip_model = findViewById(R.id.ip_model);
        ip_year = findViewById(R.id.ip_year);
        ip_quantity = findViewById(R.id.ip_quantity);
        ip_unit_price = findViewById(R.id.ip_unit_price);
        ip_description = findViewById(R.id.ip_description);
        img_back = findViewById(R.id.img_back);
    }

    private void hitEquipmentListApi() {
        if (!NetworkUtil.getInstance(instance).isConnected()) {
            ToastUtils.show(instance, "No internet");
            return;
        }
        Call<ResponseModel<List<Equipments>>> callCities = RestServiceFactory.createServiceUser().getEquipmentList();
        callCities.enqueue(new RestCallBack<ResponseModel<List<Equipments>>>(instance) {
            @Override
            public void onFailure(Call<ResponseModel<List<Equipments>>> call, String message) {
                ToastUtils.show(instance, message);
                LogHelper.d("Tag", "onFailure FormField  : ");
            }

            @Override
            public void onResponse(Call<ResponseModel<List<Equipments>>> call, Response<ResponseModel<List<Equipments>>> restResponse, ResponseModel<List<Equipments>> response) {
                if (response.isSuccess) {
                    equipmentsList.clear();
                    equipmentsList.addAll(response.data);
                } else {
                    ToastUtils.show(instance, response.message);
                }
            }
        });
    }


}
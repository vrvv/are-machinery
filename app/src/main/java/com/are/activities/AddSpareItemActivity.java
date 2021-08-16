package com.are.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.are.MyApp;
import com.are.R;
import com.are.model.Equipments;
import com.are.model.rest_request.AddSpareItemRequest;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.ui.NoChangingBackgroundTextInputLayout;
import com.are.utils.DialogUtils;
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

public class AddSpareItemActivity extends AppCompatActivity {
    public AddSpareItemActivity instance;
    public AppCompatButton btn_publish;
    public List<Equipments> equipmentsList = new ArrayList<>();
    public TextInputEditText et_name, et_part_number, et_brand_name, et_model, et_quantity, et_unit_price, et_location, et_description;
    public NoChangingBackgroundTextInputLayout ip_name,ip_brand_name, ip_model, ip_quantity, ip_unit_price,ip_part_number,
            ip_description,ip_location;
    public AddSpareItemRequest addItemRequest;
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
    public TextView tvVideoUpload;
    public Dialog loder;
    public ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spare_item);
        instance = this;
        addItemRequest = new AddSpareItemRequest();
        initView();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
        if (et_name.getText().toString().isEmpty()) {
            ip_name.setError("Please enter Name");
            return;
        }
        if (et_brand_name.getText().toString().isEmpty()) {
            ip_brand_name.setError("Please enter Brand name");
            ip_name.setError(null);
            return;
        }
        if (et_model.getText().toString().isEmpty()) {
            ip_model.setError("Please enter Model name");
            ip_name.setError(null);
            ip_brand_name.setError(null);
            return;
        }


        if (et_quantity.getText().toString().isEmpty()) {
            ip_quantity.setError("Please enter Quantity");
            ip_model.setError(null);
            ip_name.setError(null);
            ip_brand_name.setError(null);
            return;
        }
        if (et_unit_price.getText().toString().isEmpty()) {
            ip_unit_price.setError("Please enter Unit price");
            ip_quantity.setError(null);
            ip_model.setError(null);
            ip_name.setError(null);
            ip_brand_name.setError(null);
            return;
        }
        if (et_part_number.getText().toString().isEmpty()) {
            ip_part_number.setError("Please enter Part Number");
            ip_unit_price.setError(null);
            ip_quantity.setError(null);
            ip_model.setError(null);
            ip_name.setError(null);
            ip_brand_name.setError(null);
            return;
        }
        if (et_location.getText().toString().isEmpty()) {
            ip_location.setError("Please enter Location");
            ip_part_number.setError(null);
            ip_unit_price.setError(null);
            ip_quantity.setError(null);
            ip_model.setError(null);
            ip_name.setError(null);
            ip_brand_name.setError(null);
            return;
        }
        if (et_description.getText().toString().isEmpty()) {
            ip_description.setError("Please enter description");
            ip_part_number.setError(null);
            ip_unit_price.setError(null);
            ip_quantity.setError(null);
            ip_model.setError(null);
            ip_name.setError(null);
            ip_brand_name.setError(null);
            return;
        }

        goforIt();
        addItemRequest.setEquipmentType(0);
        addItemRequest.setName(et_name.getText().toString());
        addItemRequest.setBrand(et_brand_name.getText().toString());
        addItemRequest.setModel(et_model.getText().toString());
        addItemRequest.setQuantity(et_quantity.getText().toString());
        addItemRequest.setUnitPrice(et_unit_price.getText().toString());
        addItemRequest.setLocation(et_location.getText().toString());
        addItemRequest.setDescription(et_description.getText().toString());
        addItemRequest.setItemType("2");
        addItemRequest.setPartNumber(et_part_number.getText().toString());
        addItemRequest.setCreatedBy(MyApp.mySharedPref.getUserId());
        for (int i = 0; i < seletedFileArray.size(); i++) {
            AddSpareItemRequest.Images aI = new AddSpareItemRequest.Images();
            aI.setUrl("/images/" + seletedFileArray.get(i).getName());
            aI.setPositionType(i + 1);
            addItemRequest.images.add(aI);
        }
        for (int i = 0; i < seletedVideoFileArray.size(); i++) {
            AddSpareItemRequest.Video aI = new AddSpareItemRequest.Video();
            aI.setUrl("/videos/" + seletedVideoFileArray.get(i).getName());
            addItemRequest.video.add(aI);
        }
        hitEnquiryApi(addItemRequest);

    }

    private void hitEnquiryApi(AddSpareItemRequest addItemRequest) {
        if (!NetworkUtil.getInstance(instance).isConnected()) {
            ToastUtils.show(instance, "No internet");
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(addItemRequest);
        Log.d("myTag", json);
        loder = DialogUtils.showLoader(this);

        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().addSpareItem(addItemRequest);
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
        ip_location = findViewById(R.id.ip_location);
        ip_part_number = findViewById(R.id.ip_part_number);
        ip_name = findViewById(R.id.ip_name);
        img_back = findViewById(R.id.img_back);
        tvVideoUpload = findViewById(R.id.tvVideoUpload);
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
        et_part_number = findViewById(R.id.et_part_number);
        et_name = findViewById(R.id.et_name);
        et_brand_name = findViewById(R.id.et_brand_name);
        et_model = findViewById(R.id.et_model);
        et_quantity = findViewById(R.id.et_quantity);
        et_unit_price = findViewById(R.id.et_unit_price);
        et_location = findViewById(R.id.et_location);
        et_description = findViewById(R.id.et_description);
        ip_brand_name = findViewById(R.id.ip_brand_name);
        ip_model = findViewById(R.id.ip_model);
        ip_quantity = findViewById(R.id.ip_quantity);
        ip_unit_price = findViewById(R.id.ip_unit_price);
        ip_description = findViewById(R.id.ip_description);
    }


}
package com.are.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.are.MyApp;
import com.are.R;
import com.are.model.Company;
import com.are.model.rest_request.AddCompanyRequest;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.ui.NoChangingBackgroundTextInputLayout;
import com.are.utils.DialogUtils;
import com.are.utils.LogHelper;
import com.are.utils.NetworkUtil;
import com.are.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;
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

public class VerifyActivity extends AppCompatActivity {

    public VerifyActivity context;
    public TextInputEditText et_legel_name, et_pan_number, et_gst_no, et_cin_no;
    public AppCompatAutoCompleteTextView act_company_name;
    List<String> listComanyName = new ArrayList<>();
    List<Company> listComany = new ArrayList<>();
    public NoChangingBackgroundTextInputLayout ip_legalname, ip_pan_number, ip_company_name, ip_gst_no, ip_cin_no;
    public Button btn_next;
    public TextView tvSkip;
    public ImageView img_pan, img_gst, img_cin;
    public String panImagePath = "", gstImagePath = "", cinImagePath = "";
    public RelativeLayout rel_pan_upload, rel_gst, rel_in;
    public int PICK_IMAGE = 1;
    public Uri selectedUri;
    public int companyId = 0;
    public AddCompanyRequest addCompanyRequest;
    public File panFileArray, gstFileArray, cinFileArray;
    public Dialog loder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        context = this;
        addCompanyRequest = new AddCompanyRequest();
        initView();
        act_company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (act_company_name.getText().toString().length() >= 3) {
                    String searchVal = act_company_name.getText().toString().substring(0, 2);
                    runCompanyApi(searchVal);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        act_company_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                companyId = listComany.get(position).getCompanyId();
            }
        });
        rel_pan_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_IMAGE = 101;
                CropImage.startPickImageActivity(context);

            }
        });
        rel_gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_IMAGE = 102;
                CropImage.startPickImageActivity(context);

            }
        });
        rel_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PICK_IMAGE = 103;
                CropImage.startPickImageActivity(context);

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
                Uri imageUri = CropImage.getPickImageResultUri(context, data);
                Log.i("profImagePath", "==>" + imageUri.getPath());
                if (CropImage.isReadExternalStoragePermissionsRequired(context, imageUri)) {
                    selectedUri = imageUri;

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                } else {
                    startCropImageActivity(imageUri);
                }
            }

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    selectedUri = result.getUri();
                    if (PICK_IMAGE == 101) {
                        panImagePath = selectedUri.getPath().toString();
                        img_pan.setImageURI(selectedUri);
                        goforItPan(panImagePath);
                    } else if (PICK_IMAGE == 102) {
                        gstImagePath = selectedUri.getPath().toString();
                        img_gst.setImageURI(selectedUri);
                        goforItGst(panImagePath);
                    } else if (PICK_IMAGE == 103) {
                        cinImagePath = selectedUri.getPath().toString();
                        img_cin.setImageURI(selectedUri);
                        goforItCin(cinImagePath);
                    }


                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.i("profImagePath", "==> error");
                }
            }
        }
    }

    private void goforItGst(String panImagePath) {
        generateGstmageName();
        FTPClient con = null;

        try {
            con = new FTPClient();
            con.connect("182.50.132.51");

            if (con.login("areftp", "9vT7e!n3")) {
                Log.e("upload", "Connect");
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);
                String data = panImagePath;
                FileInputStream in = new FileInputStream(new File(data));
                boolean result = con.storeFile("/docs" + gstFileArray.getName(), in);
                if (result)
                    Log.e("upload", "succeeded");
                else
                    Log.e("upload", "FAIL");
                in.close();
                con.logout();
                con.disconnect();
            } else {
                Log.e("upload", "NOT Connect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goforItCin(String panImagePath) {
        generateCinImageName();
        FTPClient con = null;

        try {
            con = new FTPClient();
            con.connect("182.50.132.51");

            if (con.login("areftp", "9vT7e!n3")) {
                Log.e("upload", "Connect");
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);
                String data = panImagePath;
                FileInputStream in = new FileInputStream(new File(data));
                boolean result = con.storeFile("/docs" + cinFileArray.getName(), in);
                if (result)
                    Log.e("upload", "succeeded");
                else
                    Log.e("upload", "FAIL");
                in.close();
                con.logout();
                con.disconnect();
            } else {
                Log.e("upload", "NOT Connect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateGstmageName() {
        String uniqueId = UUID.randomUUID().toString();
        String fileName = uniqueId + ".jpg";
        gstFileArray = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM) + "/" + fileName);
        Log.i("uniqueId", "==>" + uniqueId);
        Log.i("File path", "==>" + panFileArray.getAbsolutePath());
    }

    private void generateCinImageName() {
        String uniqueId = UUID.randomUUID().toString();
        String fileName = uniqueId + ".jpg";
        cinFileArray = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM) + "/" + fileName);
        Log.i("uniqueId", "==>" + uniqueId);
        Log.i("File path", "==>" + panFileArray.getAbsolutePath());
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setShowCropOverlay(false)
                .setFixAspectRatio(true)
                .setMultiTouchEnabled(true)
                .start(context);
    }

    private void initView() {
        img_pan = findViewById(R.id.img_pan);
        img_gst = findViewById(R.id.img_gst);
        img_cin = findViewById(R.id.img_cin);
        ip_cin_no = findViewById(R.id.ip_cin_no);
        et_cin_no = findViewById(R.id.et_cin_no);
        ip_gst_no = findViewById(R.id.ip_gst_no);
        ip_company_name = findViewById(R.id.ip_company_name);
        ip_legalname = findViewById(R.id.ip_legalname);
        ip_pan_number = findViewById(R.id.ip_pan_number);
        rel_pan_upload = findViewById(R.id.rel_pan_upload);
        rel_gst = findViewById(R.id.rel_gst);
        rel_in = findViewById(R.id.rel_in);
        et_legel_name = findViewById(R.id.et_legel_name);
        et_pan_number = findViewById(R.id.et_pan_number);
        et_gst_no = findViewById(R.id.et_gst_no);
        btn_next = findViewById(R.id.btn_next);
        tvSkip = findViewById(R.id.tvSkip);
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
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DashboardActivity.class);
                startActivity(i);
                finishAffinity();
            }
        });
    }

    private void validation() {
        if (et_legel_name.getText().toString().isEmpty()) {
            ip_legalname.setError("please enter legal name");
            return;
        }
        if (et_pan_number.getText().toString().isEmpty()) {
            ip_pan_number.setError("please type company legal name");
            ip_legalname.setError(null);
            return;
        }
        if (panImagePath.isEmpty()) {
            ToastUtils.show(context, "Plese upload pancard photo.");
            return;
        }
        if (panFileArray != null) {
            addCompanyRequest.setPanDoc(panFileArray.getName().toString());
        }
        if (act_company_name.getText().toString().isEmpty()) {
            ip_company_name.setError("please type company name");
            ip_legalname.setError(null);
            ip_pan_number.setError(null);
            return;
        }
        if (et_gst_no.getText().toString().isEmpty()) {
            ip_gst_no.setError("please enter GST No");
            ip_legalname.setError(null);
            ip_company_name.setError(null);
            ip_pan_number.setError(null);
            return;
        }
        if (gstImagePath.isEmpty()) {
            ToastUtils.show(context, "Plese upload GST photo.");
            return;
        }
        if (gstFileArray != null) {
            addCompanyRequest.setGstDoc(gstFileArray.getName().toString());
        }
        if (et_cin_no.getText().toString().isEmpty()) {
            ip_cin_no.setError("please enter CIN No");
            ip_gst_no.setError(null);
            ip_legalname.setError(null);
            ip_company_name.setError(null);
            ip_pan_number.setError(null);
            return;
        }
        if (cinImagePath.isEmpty()) {
            ToastUtils.show(context, "Plese upload CIN photo.");
            return;
        }
        if (cinFileArray != null) {
            addCompanyRequest.setCinDoc(cinFileArray.getName().toString());
        }
        addCompanyRequest.setProprietor(et_legel_name.getText().toString());
        addCompanyRequest.setPan(et_pan_number.getText().toString());
        addCompanyRequest.setUserId(MyApp.user.getUserId());
        addCompanyRequest.setCompanyId(companyId);
        hitAddCompanyDetailApi(addCompanyRequest);

    }

    private void hitAddCompanyDetailApi(AddCompanyRequest addCompanyRequest) {
        loder = DialogUtils.showLoader(this);
        Call<ResponseModel<String>> call = RestServiceFactory.createServiceUser().addCompany(addCompanyRequest);
        call.enqueue(new RestCallBack<ResponseModel<String>>(context) {
            @Override
            public void onFailure(Call<ResponseModel<String>> call, String message) {
                loder.dismiss();
                ToastUtils.show(context, message);
            }

            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> restResponse, ResponseModel<String> response) {
                loder.dismiss();
                if (response.isSuccess) {
                    Intent i = new Intent(context, UnderVerificationActivity.class);
                    startActivity(i);
                    finishAffinity();

                } else {
                    ToastUtils.show(context, response.message);
                }
            }
        });
    }


    public void generatePanImageName() {
        String uniqueId = UUID.randomUUID().toString();
        String fileName = uniqueId + ".jpg";
        panFileArray = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM) + "/" + fileName);
        Log.i("uniqueId", "==>" + uniqueId);
        Log.i("File path", "==>" + panFileArray.getAbsolutePath());
    }

    public void goforItPan(String string) {

        generatePanImageName();
        FTPClient con = null;

        try {
            con = new FTPClient();
            con.connect("182.50.132.51");

            if (con.login("areftp", "9vT7e!n3")) {
                Log.e("upload", "Connect");
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);
                String data = string;
                FileInputStream in = new FileInputStream(new File(data));
                boolean result = con.storeFile("/docs" + panFileArray.getName(), in);
                if (result)
                    Log.e("upload", "succeeded");
                else
                    Log.e("upload", "FAIL");
                in.close();
                con.logout();
                con.disconnect();
            } else {
                Log.e("upload", "NOT Connect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
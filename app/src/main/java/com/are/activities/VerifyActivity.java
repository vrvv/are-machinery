package com.are.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.are.R;
import com.are.model.Company;
import com.are.rest_api.ResponseModel;
import com.are.rest_api.RestCallBack;
import com.are.rest_api.RestServiceFactory;
import com.are.ui.NoChangingBackgroundTextInputLayout;
import com.are.utils.LogHelper;
import com.are.utils.NetworkUtil;
import com.are.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class VerifyActivity extends AppCompatActivity {

    public VerifyActivity context;
    public TextInputEditText et_legel_name, et_pan_number,et_gst_no;
    public AppCompatAutoCompleteTextView act_company_name;
    List<String> listComanyName = new ArrayList<>();
    List<Company> listComany = new ArrayList<>();
    public NoChangingBackgroundTextInputLayout ip_name, ip_company_name, ip_email, ip_mobile, ip_gst_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        context = this;
        initView();
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

    private void initView() {
        et_legel_name = findViewById(R.id.et_legel_name);
        et_pan_number = findViewById(R.id.et_pan_number);
        et_gst_no = findViewById(R.id.et_gst_no);
    }

    public void goforIt(String string) {


        FTPClient con = null;

        try {
            con = new FTPClient();
            con.connect("182.50.132.51");

            if (con.login("areftp", "9vT7e!n3")) {
                con.enterLocalPassiveMode(); // important!
                con.setFileType(FTP.BINARY_FILE_TYPE);
              //  String data = seletedFileArray.get(0).getAbsolutePath();

             //   FileInputStream in = new FileInputStream(new File(data));
              //  boolean result = con.storeFile(seletedFileArray.get(0).getName(), in);
              //  in.close();
              //  if (result) Log.v("upload result", "succeeded");
                con.logout();
                con.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
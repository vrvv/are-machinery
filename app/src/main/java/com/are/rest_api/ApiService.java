package com.are.rest_api;


import com.are.model.Company;
import com.are.model.Items;
import com.are.model.MainUser;
import com.are.model.rest_request.AddDeviceRequest;
import com.are.model.rest_request.GetItemRequest;
import com.are.model.rest_request.RegisterRequest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiService {

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.VALIDATE_MOBILE)
    Call<ResponseModel<String>> validateMobile(@Path("mobile") String mobile);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.VALIDATE_EMAIL)
    Call<ResponseModel<String>> validateEmail(@QueryMap Map<String, String> params);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.GET_OTP)
    Call<ResponseModel<String>> getOtp(@Path("number") String number);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.RESEND_OTP)
    Call<ResponseModel<String>> resendOTP(@Path("number") String number);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.VALIDATE_USER)
    Call<ResponseModel<MainUser>> validateUser(@QueryMap Map<String, String> params);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.SEARCH_COMPANY)
    Call<ResponseModel<List<Company>>> searchCompany(@Path("val") String val);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.REGISTER)
    Call<ResponseModel<String>> register(@Body RegisterRequest registerRequest);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.DEVICE_ADD)
    Call<ResponseModel<String>> addDevice(@Body AddDeviceRequest registerRequest);


    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.GET_ITEM)
    Call<ResponseModel<List<Items>>> getItem(@Body GetItemRequest getItemRequest);

}

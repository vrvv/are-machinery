package com.are.rest_api;


import com.are.model.Company;
import com.are.model.Employees;
import com.are.model.Equipments;
import com.are.model.ItemDetails;
import com.are.model.Items;
import com.are.model.MainUser;
import com.are.model.MyEnquiries;
import com.are.model.MyItem;
import com.are.model.MyItemEnquiries;
import com.are.model.rest_request.AddCompanyRequest;
import com.are.model.rest_request.AddDeviceRequest;
import com.are.model.rest_request.AddEmployeeRequest;
import com.are.model.rest_request.AddEnquiryRequest;
import com.are.model.rest_request.AddItemRequest;
import com.are.model.rest_request.AddSpareItemRequest;
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
    @GET(AppConstants.Url.USER_STATUS)
    Call<ResponseModel<Boolean>> getUserStatus(@Path("userId") int userId);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.COMPANY_STATUS)
    Call<ResponseModel<String>> getCompanyStatus(@Path("companyId") int companyId);

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

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.ADD_COMPANY)
    Call<ResponseModel<String>> addCompany(@Body AddCompanyRequest addCompanyRequest);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.GET_EQUIPMENT)
    Call<ResponseModel<List<Equipments>>> getEquipmentList();

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.ITEM_DETAIL)
    Call<ResponseModel<ItemDetails>> getItemDetail(@Path("itemId") int itemId, @Path("userId") String userId);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.ADD_ENQUIRY)
    Call<ResponseModel<String>> addEnquiry(@Body AddEnquiryRequest addEnquiryRequest);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.MY_ENQUIRY)
    Call<ResponseModel<List<MyEnquiries>>> getMyEnquiriesCompany(@Path("userId") String userId);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.MY_ITEM)
    Call<ResponseModel<List<MyItem>>> getMyItem(@Path("userId") String userId);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.CLOSE_ITEM)
    Call<ResponseModel<String>> closeItem(@Path("itemId") int itemId,@Path("userId") String userId);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.MY_ITEM_ENQUIRY)
    Call<ResponseModel<List<MyItemEnquiries>>> getMyItemEnquiries(@Path("itemId") int itemId);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.ADD_ITEM)
    Call<ResponseModel<String>> addItem(@Body AddItemRequest addItemRequest);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.ADD_ITEM)
    Call<ResponseModel<String>> addSpareItem(@Body AddSpareItemRequest addItemRequest);

    @Headers("Content-type: application/json")
    @GET(AppConstants.Url.MY_EMPLOYEE)
    Call<ResponseModel<List<Employees>>> getMyEmployee(@Path("companyId") int companyId);

    @Headers("Content-type: application/json")
    @POST(AppConstants.Url.ADD_EMPLOYEE)
    Call<ResponseModel<String>> addEmployee(@Body AddEmployeeRequest addEmployeeRequest);

}

package com.are.rest_api;

/**
 * Created by Karan on 12,August,2019
 */
public class AppConstants {
    public static class General {
        public static final String INDIAN_RUPEES = "\u20B9";
    }
    public static class Auth {
        public static final String USERNAME = "areapi";
        public static final String PASSWORD = "@rE@X^q$Lo2E#1";
    }
    public class Url {
        public static final String API_URL = "http://api.aremachinery.com";

        //User
        public static final String VALIDATE_MOBILE = "/user/validate/mobile/{mobile}";
        public static final String VALIDATE_EMAIL = "/user/validate/email?";
        public static final String GET_OTP = "/mobile/otp/{number}";
        public static final String VALIDATE_USER = "/user/validate?";
        public static final String SEARCH_COMPANY = "/company/search/{val}";
        public static final String RESEND_OTP = "/mobile/otp/resend/{number}/{otp}";
        public static final String REGISTER = "/user/register";
        public static final String DEVICE_ADD = "/user/device/add";
        public static final String USER_STATUS = "/user/status/{userId}";
        public static final String COMPANY_STATUS = "/company/status/{companyId}";
        public static final String ADD_COMPANY = "/company/docs";
        public static final String FORGOT_PASS_EMAIL = "user/forgot/password?";
        public static final String FORGOT_PASS_MOBILE = "user/forgot/password/{mobile}";
        public static final String SET_PASS = "/user/{userId}/reset/password/{password}";

        //Dashboard
        public static final String ADD_ITEM = "/item/add";
        public static final String GET_ITEM = "/items/list";
        public static final String ITEM_DETAIL = "/item/details/{itemId}/{userId}";
        public static final String GET_EQUIPMENT = "/equipments/list";
        public static final String MY_ITEM = "/items/{userId}";
        public static final String CLOSE_ITEM = "item/close/{itemId}/{userId}";

        public static final String ADD_ENQUIRY = "/item/add/itemenquiry";
        public static final String MY_ITEM_ENQUIRY = "/item/enquiries/{itemId}";
        public static final String MY_ENQUIRY = "/enquiries/{userId}";

        public static final String MY_EMPLOYEE = "/employees/list/{companyId}";
        public static final String ADD_EMPLOYEE = "/employee/add";
        public static final String EDIT_EMPLOYEE = "/employee/update";
        public static final String CONTENT = "/content/{type}";

        public static final String EDIT_DETAIL = "/user/update";
    }
}

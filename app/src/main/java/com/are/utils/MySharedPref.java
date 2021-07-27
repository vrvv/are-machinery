package com.are.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.are.MyApp;
import com.are.R;


/**
 * Created by Karan-Pitroda on 13-07-2021.
 */

public class MySharedPref {
    public MyApp app;
    SharedPreferences shared;
    SharedPreferences.Editor et;
    private String MY_PREF_NAME;
    private Context context;
    public final String user_id = "userid";
    public final String companyid = "companyid";
    public final String userModel = "userModel";
    public final String locationid = "locationid";


    public MySharedPref(Context ct) {
        this.context = ct;
        MY_PREF_NAME = context.getResources().getString(R.string.app_name);
        shared = ct.getSharedPreferences(MY_PREF_NAME, 0);
        et = shared.edit();
    }


    public String getString(final String key, final String value) {

        return shared.contains(key) ? shared.getString(key, "") : "";

    }

    public void sharedPrefClear() {

        et.clear();
        et.apply();
        et.commit();

    }



    public void setLocationid(String locationId) {
        et.putString(locationid, locationId);
        et.commit();
    }

    public String getLocationid() {
        return shared.contains(locationid) ? shared.getString(locationid, "") : "";
    }
    public String getUserId() {
        return shared.contains(user_id) ? shared.getString(user_id, "") : "";
    }
    public void setUserId(String UserId) {
        et.putString(user_id, UserId);
        et.commit();
    }
    public String getCompanyId() {
        return shared.contains(companyid) ? shared.getString(companyid, "") : "";
    }
    public void setCompanyId(String compId) {
        et.putString(companyid, compId);
        et.commit();
    }
    public String getUserModel() {
        return shared.contains(userModel) ? shared.getString(userModel, "") : "";
    }

    public void setUserModel(String UserModel) {
        et.putString(userModel, UserModel);
        et.commit();
    }
    public final String isLoggedIn = "isLoggedIn";
    public boolean getIsLoggedIn() {
        return shared.contains(isLoggedIn) ? shared.getBoolean(isLoggedIn, false) : false;
    }

    public void setIsLoggedIn(boolean isfirst) {
        et.putBoolean(isLoggedIn, isfirst);
        et.commit();
    }
    public void clearApp() {
        et.clear();
        et.apply();
        et.commit();
    }
}

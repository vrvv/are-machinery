package com.are;


import android.os.StrictMode;

import com.are.model.MainUser;
import com.are.model.rest_request.RegisterRequest;
import com.are.utils.MySharedPref;


public class MyApp extends DarkThemeApplication {
    public final static boolean RETROFIT_SHOW_LOG = true;
    public static RegisterRequest registerRequest;
    public static MySharedPref mySharedPref;
    public static MyApp instance;
    public static MainUser user;

    public void onCreate() {
        super.onCreate();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            instance = this;
            mySharedPref = new MySharedPref(instance);
            Class.forName("android.os.AsyncTask");

        } catch (ClassNotFoundException e) {
        }
    }


}
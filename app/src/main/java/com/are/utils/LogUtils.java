package com.are.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogUtils {

    public static void debug(String msg, Class aClass) {
        log("VaibhavDebug", aClass.getName() + " " + msg);
    }

    public static void debug(String msg) {
        log("VaibhavDebug", msg);
    }


    public static void api(String msg) {
        log("ho", msg);
    }

    public static void response(String msg) {
        log("response", msg);
    }

    public static void exception(Exception e) {
        log("EXCEPTION: ", e.toString());
    }

    private static void log(String tag, String msg) {

    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String getTodayDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static String gstCalculation(String price, String gst) {
        double unitprice = Double.parseDouble(price);
        double mgst = Double.parseDouble(gst);
        double mprice = 0.0;
        mprice = ((unitprice * mgst) / 100);
        double totalprice = 0.0;
        totalprice = (mprice + unitprice);
        return String.valueOf(totalprice);
    }
}

package com.are.rest_api;

import android.content.Context;

import com.are.utils.LogHelper;
import com.are.utils.NetworkUtil;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RestCallBack<T> implements Callback<T> {

    public abstract void onFailure(Call<T> call, String message);

    public abstract void onResponse(Call<T> call, Response<T> restResponse, T response);

    private Context context;

    public RestCallBack(Context context) {
        this.context = context;
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

        String errorMessage = "Please try again";
        try {
            if (!NetworkUtil.getInstance(context).isConnected()) {
                errorMessage = "No internet connection";
            } else {
                LogHelper.d("response Failure :>", t.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogHelper.d("response Failure :>", t.getMessage());

        onFailure(call, errorMessage);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response.isSuccessful()) {
            Gson gson = new Gson();
            String json = gson.toJson(response.body());

            LogHelper.d("response Successful :>", json);

            onResponse(call, response, response.body());
        } else {
            LogHelper.d("response Failure :-> response Code :-> ", response.code() + " message :->  " + response.message());
            onFailure(call, response.code() + " " + response.message());
        }
    }
}

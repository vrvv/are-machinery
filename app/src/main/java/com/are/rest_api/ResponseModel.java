package com.are.rest_api;

import com.google.gson.annotations.SerializedName;

public class ResponseModel<T> {

    @SerializedName("isSuccess")
    public Boolean isSuccess;

    @SerializedName("message")
    public String message;

    @SerializedName("result")
    public T data;

}

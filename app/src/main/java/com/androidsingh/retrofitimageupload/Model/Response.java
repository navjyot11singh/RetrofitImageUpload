package com.androidsingh.retrofitimageupload.Model;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("status")
    private String status;

    @SerializedName("error")
    private String error;

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    @SerializedName("message")
    private String message;
}

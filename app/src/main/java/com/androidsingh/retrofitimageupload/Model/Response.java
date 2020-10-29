package com.androidsingh.retrofitimageupload.Model;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("status")
    private int status;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    @SerializedName("img_url")
    private String imgURL;

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getImgURL() {
        return imgURL;
    }
}

package com.androidsingh.retrofitimageupload.Interface;

import com.androidsingh.retrofitimageupload.Model.Response;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @Headers({
            "x-api-key: R9FH5BCSKP8XQGMC6OHIT",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @Multipart
    @POST("api/Portfolio")
    Call<Response> uploadImage(@Part("user_token") RequestBody user_token,@Part MultipartBody.Part img_file);
}

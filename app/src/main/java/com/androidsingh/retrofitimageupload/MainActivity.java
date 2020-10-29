package com.androidsingh.retrofitimageupload;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidsingh.retrofitimageupload.Interface.RetrofitInterface;
import com.androidsingh.retrofitimageupload.Model.Response;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static final int INTENT_REQUEST_CODE = 100;

    public static final String URL = "https://easyloanmantra.in/";

    private Button mBtImageSelect;
    private Button mBtImageShow;
    private ProgressBar mProgressBar;
    private String mImageUrl = "";
    HashMap<String,String> mParam;
    HashMap<String,MultipartBody.Part> mParam1;
    private String token="x3bwkygiechhomozar4xmdviihqndpek2e6";

    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();

    }

    private void initViews() {

        mParam=new HashMap<>();
        mParam1=new HashMap<>();
        mBtImageSelect = findViewById(R.id.btn_select_image);
        mBtImageShow = findViewById(R.id.btn_show_image);
        mProgressBar = findViewById(R.id.progress);

        mBtImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtImageShow.setVisibility(View.GONE);

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");

                try {
                    startActivityForResult(intent, INTENT_REQUEST_CODE);

                } catch (ActivityNotFoundException e) {

                    e.printStackTrace();
                }
            }
        });


//        mBtImageShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(mImageUrl));
//                startActivity(intent);
//            }
//        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == INTENT_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Uri uri = data.getData();
                String uriString = uri.toString();
                File myFile = new File(uriString);
                //                    InputStream is = getContentResolver().openInputStream(data.getData());

                String path = getFilePathFromURI(MainActivity.this,uri);

                uploadImage(path);


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getFilePathFromURI(Context context, Uri contentUri) {

        //copy file and send new file path
        String fileName = getFileName(contentUri);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName);
            // create folder if not exists

            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }
//    public byte[] getBytes(InputStream is) throws IOException {
//        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
//
//        int buffSize = 1024;
//        byte[] buff = new byte[buffSize];
//
//        int len = 0;
//        while ((len = is.read(buff)) != -1) {
//            byteBuff.write(buff, 0, len);
//        }
//
//        return byteBuff.toByteArray();
//    }

    private void uploadImage(String path) {
        String pdfname = String.valueOf(Calendar.getInstance().getTimeInMillis());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        File file = new File(path);


        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);

//        mParam.put("user_token","x3bwkygiechhomozar4xmdviihqndpek2e6");

        MultipartBody.Part body = MultipartBody.Part.createFormData("pay_slip_image", "payslip.pdf", requestBody);
//        mParam1.put("img_file")
        RequestBody token=RequestBody.create(MediaType.parse("text/plain"),"kl7a9iij895vmg5jbk6xgz4mzooupecml3w");
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<Response> call = retrofitInterface.uploadImage(token,body);
        mProgressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                mProgressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    Response responseBody = response.body();
//
                    Toast.makeText(MainActivity.this, responseBody.getMessage(),Toast.LENGTH_SHORT).show();

                } else {


                    ResponseBody errorBody = response.errorBody();

                    Gson gson = new Gson();

                    try {

                        Response errorResponse = gson.fromJson(errorBody.string(), Response.class);
                        Toast.makeText(MainActivity.this, errorResponse.getMessage(),Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

                mProgressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }
}

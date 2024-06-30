package com.dev.hazhanjalal.haxhantools.utils.imgur_upload_helper;

import static com.dev.hazhanjalal.haxhantools.utils.print.Logger.e;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UploadToImgur {
    
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String uploadURL = "https://api.imgur.com/3/upload";
    // Context Not really needed, but just in case for future use.
    Context context;
    String clientId;
    String secretId;
    Uri imageURI;
    // ---
    private OnUploadCallback callback;
    
    
    public UploadToImgur(String clientId, String secretId) {
        this.clientId = clientId;
        this.secretId = secretId;
    }
    
    public static String getUploadURL() {
        return uploadURL;
    }
    
    public static void setUploadURL(String uploadURL) {
        UploadToImgur.uploadURL = uploadURL;
    }
    
    public void upload(Context context, Uri imageURI, OnUploadCallback onUploadCallback) {
        this.imageURI = imageURI;
        this.context = context;
        this.callback = onUploadCallback;
        
        // start upload...
        performUpload();
    }
    
    public void upload(Uri imageURI, OnUploadCallback onUploadCallback) {
        this.context = Utils.activeContext;
        this.imageURI = imageURI;
        this.callback = onUploadCallback;
        
        // start upload...
        performUpload();
    }
    
    private void performUpload() {
        try {
            RequestBody bodyRq = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("client_id", clientId)
                    .addFormDataPart("client_secret", secretId)
                    .addFormDataPart("image", "image.png",
                                     RequestBody.create(new File(imageURI.getPath()),
                                                        MediaType.parse("image/*")
                                     )
                    )
                    .build();
            
            Request request = new Request.Builder()
                    .addHeader("content-type", "multipart/form-data;")
                    .url(uploadURL)
                    .post(bodyRq)
                    .build();
            
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();
            
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    
                    if (callback != null) {
                        callback.fail(e.getMessage());
                    }
                }
                
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (callback != null) {
                        
                        try {
                            assert response.body() != null;
                            
                            JSONObject jsnBody = new JSONObject(response.body().toString());
                            String url = "";
                            if (jsnBody.has("data") && jsnBody.getJSONObject("data").has("link")) {
                                url = jsnBody.getJSONObject("data").getString("link");
                            }
                            
                            callback.success(jsnBody, url);
                        } catch (JSONException e) {
                            callback.success(null, "");
                        }
                    }
                }
            });
            
        } catch (Exception e) {
            e(e);
        }
        
    }
}

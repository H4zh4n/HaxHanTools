package com.dev.hazhanjalal.haxhantools.utils.imgur_upload_helper;

import org.json.JSONObject;

public abstract class OnUploadCallback {
    public abstract void success(JSONObject jsonResponse, String imageUrl);
    
    public abstract void fail(String message);
}

package com.dev.hazhanjalal.haxhantools.utils.imgur_upload_helper;


public interface OnUploadTaskCallback {
    
    public void success(String url);
    
    public void cancel(String message);
    
    public void fail(String message);
    
}

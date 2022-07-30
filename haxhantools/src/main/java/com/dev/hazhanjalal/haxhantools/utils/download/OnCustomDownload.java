package com.dev.hazhanjalal.haxhantools.utils.download;

import com.downloader.Error;
import com.downloader.Progress;

public abstract class OnCustomDownload {
    public String inputText = "";
    public Object object = null;
    
    public abstract void onDownloadComplete();
    
    public void onProgress(Progress progress) {
    
    }
    
    public void onStartOrResume() {
    
    }
    
    public void onCancel() {
    
    }
    
    public void onPause() {
    
    }
    
    public void onError(Error error) {
    
    }
    
}

package com.dev.hazhanjalal.haxhantools.utils.imgur_upload_helper;


import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;

import com.dev.hazhanjalal.haxhantools.utils.ui.HxProgress;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class UploadTask extends AsyncTask<Uri, Integer, String> implements OnCancelListener {
    
    private Context mContext;
    private OnUploadTaskCallback mCallback;
    // private ProgressDialog dialog = null;
    private boolean isShowDialog = false;
    private String mTitle = "Please wait";
    private String mMessage = "Uploading image";
    
    /**
     * default constract
     *
     * @param context  Context;
     * @param callback Context; you need implement OnUploadTaskCallback;
     */
    public UploadTask(Context context, OnUploadTaskCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
    }
    
    /**
     * custom constract. setting dialog text;
     *
     * @param context  Context;
     * @param callback Context; you need implement OnUploadTaskCallback;
     * @param title    String; dialog title text;
     * @param message  String; dialog message text;
     */
    public UploadTask(Context context, OnUploadTaskCallback callback, String title, String message) {
        this.mContext = context;
        this.mCallback = callback;
        this.mTitle = title;
        this.mMessage = message;
    }
    
    private static int copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[8192];
        int count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    
    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.i(Const.TAG, "task onCancelled");
        closeDialog();
        mCallback.cancel("onCancelled");
    }
    
    @Override
    protected void onCancelled(String rerult) {
        super.onCancelled(rerult);
        Log.i(Const.TAG, "task onCancelled result:" + rerult);
        closeDialog();
        mCallback.cancel("onCancelled: " + rerult);
    }
    
    @Override
    public void onCancel(DialogInterface dialog) {
        Log.i(Const.TAG, "task dialog onCancel");
    }
    
    @Override
    protected void onPostExecute(String rerult) {
        super.onPostExecute(rerult);
        Log.i(Const.TAG, "task onPostExecute");
        closeDialog();
        if (!TextUtils.isEmpty(rerult)) {
            mCallback.success(rerult);
        } else {
            mCallback.fail(rerult);
        }
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i(Const.TAG, "task onPreExecute");
        
        isShowDialog = true;
    }
    
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.i(Const.TAG, "task onProgressUpdate");
        //to have upload progress, use values[0] which gives current upload process.
        
        HxProgress.setProgressText(values[0] + "/100");
        //  dialog.setProgress(values[0]);
    }
    
    @Override
    protected String doInBackground(Uri... params) {
        Log.i(Const.TAG, "task doInBackground");
        
        final HandlerThread ht = new HandlerThread("");
        ht.start();
        Handler h = new Handler(ht.getLooper());
        h.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress((i + 1) * 20);
                }
            }
        });
        
        InputStream imageIn;
        try {
            imageIn = mContext.getContentResolver().openInputStream(params[0]);
        } catch (FileNotFoundException e) {
            Log.e(Const.TAG, "could not open InputStream", e);
            return null;
        }
        
        HttpURLConnection conn = null;
        InputStream responseIn = null;
        
        try {
            conn = (HttpURLConnection) new URL(Const.UPLOAD_URL).openConnection();
            conn.setDoOutput(true);
            
            ImgurAuthorization.getInstance().addToHttpURLConnection(mContext, conn);
            
            OutputStream out = conn.getOutputStream();
            copy(imageIn, out);
            out.flush();
            out.close();
            
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                responseIn = conn.getInputStream();
                ht.quit();
                return onInput(responseIn);
            } else {
                responseIn = conn.getErrorStream();
                StringBuilder sb = new StringBuilder();
                Scanner scanner = new Scanner(responseIn);
                while (scanner.hasNext()) {
                    sb.append(scanner.next());
                }
                Log.i(Const.TAG, "error response: " + sb.toString());
                return null;
            }
        } catch (Exception ex) {
            Log.e(Const.TAG, "Error during POST", ex);
            return null;
        } finally {
            try {
                //            	publishProgress(100);
                responseIn.close();
            } catch (Exception ignore) {
            }
            try {
                conn.disconnect();
            } catch (Exception ignore) {
            }
            try {
                imageIn.close();
            } catch (Exception ignore) {
            }
        }
    }
    
    protected String onInput(InputStream in) throws Exception {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNext()) {
            sb.append(scanner.next());
        }
        
        JSONObject root = new JSONObject(sb.toString());
        String id = root.getJSONObject("data").getString("id");
        @SuppressWarnings("unused")
        String deletehash = root.getJSONObject("data").getString("deletehash");
        
        //		Log.i(Const.TAG, "new imgur url: http://imgur.com/" + id + " (delete hash: " + deletehash + ")");
        return id;
    }
    
    private void closeDialog() {
        /*if (isShowDialog) {
            try {
                Utils.getActivity(mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowPopup.closeProgressDialog();
                    }
                });
            } catch (Exception e) {
                Log.i(Const.TAG, "task closeDialog");
                e.printStackTrace();
            }
            isShowDialog = false;
        }*/
    }
    
}

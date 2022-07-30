package com.dev.hazhanjalal.haxhantools.utils.imgur_upload_helper;

import android.content.Context;
import android.net.Uri;

import com.dev.hazhanjalal.haxhantools.utils.implementations.CustomAction;

public class UploadToImgur {
    
    /**
     * Upload image to imgur.
     *
     * @param ClientId your client id. can be generated from [https://api.imgur.com/oauth2/addclient] and accessed from [https://imgur.com/account/settings/apps]
     * @param SecretId your secret id. can be generated from  [https://api.imgur.com/oauth2/addclient] and accessed from [https://imgur.com/account/settings/apps]
     * @param imageURI URI to this image.
     * @param action   what to do when upload is success/failed.
     *                 [positiveButtonPressed] gets called upon success. [negativeButtonPressed] gets called upon fail.
     */
    
    public static void upload(Context context, String ClientId, String SecretId, Uri imageURI, CustomAction action) {
        OnUploadHelper helper = new OnUploadHelper(context, new OnUploadTaskCallback() {
            @Override
            public void success(String url) {
                
                if (action != null) {
                    action.inputText = url;
                    action.positiveButtonPressed();
                }
                
            }
            
            @Override
            public void cancel(String message) {
                //
            }
            
            @Override
            public void fail(String message) {
                if (action != null) {
                    action.inputText = message;
                    action.negativeButtonPressed();
                }
            }
        });
        
        helper.setClientId(ClientId);
        helper.setSecretId(SecretId);
        helper.uploadData(imageURI);
    }
    
}

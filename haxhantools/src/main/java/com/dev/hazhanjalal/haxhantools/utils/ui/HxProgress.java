package com.dev.hazhanjalal.haxhantools.utils.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dev.hazhanjalal.haxhantools.R;
import com.dev.hazhanjalal.haxhantools.utils.implementations.CustomAction;
import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;
import com.github.ybq.android.spinkit.sprite.Sprite;

import java.util.Random;

public class HxProgress {
    
    static Dialog prDialog;
    static Context context;
    
    //START - Progress Dialog
    public static void showProgressDialog(String title) {
        showProgressDialog(Utils.activeContext, title, Utils.getString(R.string.please_wait), Utils.getDrawable(R.drawable.ic_info), null);
    }
    
    public static void showProgressDialog(String title, String message) {
        showProgressDialog(Utils.activeContext, title, message, Utils.getDrawable(R.drawable.ic_info), null);
    }
    
    public static void showProgressDialog(Context context, String title, String message) {
        showProgressDialog(context, title, message, Utils.getDrawable(context, R.drawable.ic_info), null);
    }
    
    
    public static void showProgressDialog(String title, String message, CustomAction onDismiss) {
        showProgressDialog(Utils.activeContext, title, message, Utils.getDrawable(R.drawable.ic_info), onDismiss);
    }
    
    public static void showProgressDialog(Context context, String title, String message, CustomAction onDismiss) {
        showProgressDialog(context, title, message, Utils.getDrawable(context, R.drawable.ic_info), onDismiss);
    }
    
    public static void showProgressDialog(String title, String message, Drawable icon, CustomAction onDismiss) {
        showProgressDialog(Utils.activeContext, title, message, icon, onDismiss);
    }
    
    public static void showProgressDialog(Context context, String title, Drawable icon, String message, CustomAction onDismiss) {
        showProgressDialog(context, title, message, icon, onDismiss);
    }
    
    public static void showProgressDialog(Context context, String title) {
        showProgressDialog(context, title, Utils.getString(context, R.string.please_wait), Utils.getDrawable(context, R.drawable.ic_info), null);
    }
    
    public static void showProgressDialog(Context ctx, String title, String message, Drawable icon, CustomAction onDismiss) {
        context = ctx;
        
        if ((prDialog != null && prDialog.isShowing()) || ctx == null) {
            return;
        }
        
        prDialog = customProgressDialog(context, title, message, icon, onDismiss);
        
        Utils.getActivity(context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                prDialog.show();
            }
        });
    }
    
    public static void setProgressText(final String message) {
        if (prDialog != null && prDialog.isShowing() && context != null) {
            Utils.getActivity(context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView) prDialog.findViewById(R.id.lblMessage)).setText(message);
                }
            });
        }
    }
    
    public static void closeProgressDialog() {
        closeProgressDialog(context);
    }
    
    public static void closeProgressDialog(Context context) {
        if (prDialog != null && prDialog.isShowing() && context != null) {
            Utils.getActivity(context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    prDialog.dismiss();
                }
            });
        }
    }
    
    private static Dialog customProgressDialog(Context context, String title, String message, Drawable icon, CustomAction onDismiss) {
        prDialog = new Dialog(context, R.style.alert);
        prDialog.setTitle(title);
        prDialog.setContentView(R.layout.show_progress_layout);
        TextView lblTitle = prDialog.findViewById(R.id.lblTitle);
        TextView lblMessage = prDialog.findViewById(R.id.lblMessage);
        prDialog.setCancelable(false);
        ImageView imgIcon = prDialog.findViewById(R.id.imgIcon);
        View btnClose = prDialog.findViewById(R.id.btnClose);
        
        if (onDismiss != null) {
            btnClose.setVisibility(View.VISIBLE);
            
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDismiss.positiveButtonPressed();
                    closeProgressDialog();
                }
            });
        } else {
            btnClose.setVisibility(View.GONE);
        }
        
        ProgressBar progressBar = (ProgressBar) prDialog.findViewById(R.id.spin_kit);
        
        Sprite loading = Utils.randomSprite();
        
        switch (new Random().nextInt(5)) {
            case 0:
                loading.setColor(Utils.getColor(context, R.color.colorYellowLight));
                break;
            case 1:
                loading.setColor(Utils.getColor(context, R.color.colorGreenLight));
                break;
            case 2:
                loading.setColor(Utils.getColor(context, R.color.colorBlueLight));
                break;
            case 3:
                loading.setColor(Utils.getColor(context, R.color.colorTurquoiseLight));
                break;
            case 4:
                loading.setColor(Utils.getColor(context, R.color.colorRedLight));
                break;
        }
        
        progressBar.setIndeterminateDrawable(loading);
        
        lblMessage.setText(message);
        
        if (title.isEmpty()) {
            lblTitle.setVisibility(View.GONE);
        } else {
            lblTitle.setText(title);
        }
        
        if (icon == null) {
            imgIcon.setVisibility(View.GONE);
        } else {
            imgIcon.setImageDrawable(icon);
        }
        
        return prDialog;
    }
    //END - ProgressLayout
    
}

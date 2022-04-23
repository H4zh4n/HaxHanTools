package com.dev.hazhanjalal.haxhantools.utils.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.dev.hazhanjalal.haxhantools.R;
import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;


public class ShowToast {
    //START - Custom Toast
    public static void showToast(Context ctx, final String text, final Drawable icon, final int backColor) {
        Utils.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = Utils.getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.show_custom_toast,
                                               (ViewGroup) Utils.getActivity().findViewById(R.id.toast_layout_root));
                
                LinearLayout loToast = layout.findViewById(R.id.toast_layout_root);
                //Change tint
                loToast.getBackground().setColorFilter(backColor, PorterDuff.Mode.SRC_IN);
                
                ImageView image = (ImageView) layout.findViewById(R.id.image);
                image.setImageDrawable(icon);
                
                TextView lblText = (TextView) layout.findViewById(R.id.text);
                lblText.setText(" " + text);
                
                final Toast toast = new Toast(ctx);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                
                toast.show();
            }
        });
        
    }
    
    public static void showToast(String text, int backColor) {
        showToast(Utils.activeContext, text, backColor);
    }
    
    public static void showToast(Context ctx, String text, int backColor) {
        showToast(ctx, text, ContextCompat.getDrawable(ctx, R.drawable.ic_info_outline), backColor);
    }
    
    public static void showToast(String text) {
        showToast(Utils.activeContext, text);
    }
    
    public static void showToast(Context ctx, String text) {
        showToast(ctx, text, ContextCompat.getDrawable(ctx, R.drawable.ic_info_outline),
                  ContextCompat.getColor(ctx, R.color.colorBlueChosen));
    }
    
    //END - Custom Toast
    public static void showToastSuccess(String text) {
        showToastSuccess(Utils.activeContext, text);
    }
    
    public static void showToastSuccess(Context ctx, String text) {
        showToast(ctx, text, ContextCompat.getDrawable(ctx, R.drawable.ic_check),
                  ContextCompat.getColor(ctx, R.color.colorGreenChosen));
    }
    
    
    public static void showToastWarning(String text) {
        showToastWarning(Utils.activeContext, text);
    }
    
    
    public static void showToastWarning(Context ctx, String text) {
        showToast(ctx, text, ContextCompat.getDrawable(ctx, R.drawable.ic_warning),
                  ContextCompat.getColor(ctx, R.color.colorYellowChosen));
    }
    
    public static void showToastError(String text) {
        showToastError(Utils.activeContext, text);
    }
    
    public static void showToastError(Context ctx, String text) {
        showToast(ctx, text, ContextCompat.getDrawable(ctx, R.drawable.ic_warning),
                  ContextCompat.getColor(ctx, R.color.colorRedChosen));
    }
    
}

package com.dev.hazhanjalal.haxhantools.utils.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.dev.hazhanjalal.haxhantools.R;
import com.dev.hazhanjalal.haxhantools.utils.implementations.CustomAction;
import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.ortiz.touchview.TouchImageView;

import java.util.Random;
import java.util.regex.Pattern;

public class ShowPopup {
    
    public static Context activeContext;
    
    public static Dialog prDialog;
    int number;
    
    private static int getRandomNumber() {
        return new Random().nextInt(900000) + 100000;
    }
    
    // START - showInputDialog
    public static void showInputDialog(Context context, String title, String message, String hint, Drawable icon,
                                       final CustomAction action, boolean numberOnly) {
        showInputDialog(context, title, message, "", hint, icon, action, numberOnly);
    }
    
    public static void showInputDialog(Context context, String title, String message, String hint, Drawable icon,
                                       final CustomAction action) {
        showInputDialog(context, title, message, "", hint, icon, action, false);
    }
    
    public static void showInputDialog(Context context, String title, String message, String hint,
                                       final CustomAction action, boolean isNumberOnly) {
        showInputDialog(context, title, message, "", hint, context.getDrawable(R.drawable.ic_goto_page), action, isNumberOnly);
    }
    
    public static void showInputDialog(Context context, String title, String message, final CustomAction action) {
        showInputDialog(context, title, message, "", "", context.getDrawable(R.drawable.ic_goto_page), action, false);
    }
    
    public static void showInputDialog(Context context, String title, String message, Drawable icon,
                                       final CustomAction action) {
        showInputDialog(context, title, message, "", "", icon, action, false);
    }
    //END - customInputDialog
    
    // START - showConfirmDialog
    
    public static void showInputDialog(Context context, String title, String message, String input, String hint,
                                       Drawable icon,
                                       final CustomAction action, boolean numberOnly) {
        final Dialog shInput = customInputDialog(context, title, message, input, hint, icon);
        final EditText txtInput = shInput.findViewById(R.id.txtInput);
        
        if (numberOnly) {
            txtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        
        if (title.equalsIgnoreCase("Password")) {
            txtInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        
        txtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }
            
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!txtInput.getText().toString().isEmpty()) {
                    txtInput.setError(null);
                }
            }
            
            @Override
            public void afterTextChanged(Editable editable) {
                
            }
        });
        
        Button btnPositive = shInput.findViewById(R.id.btnConfirm);
        btnPositive.setText(context.getString(R.string.confirm_button));
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action == null) {
                    shInput.dismiss();
                } else {
                    if (!txtInput.getText().toString().isEmpty()) {
                        action.inputText = txtInput.getText().toString().trim();
                        action.positiveButtonPressed();
                        shInput.dismiss();
                    } else {
                        txtInput.setError("Can't have empty value !");
                    }
                }
            }
        });
        
        Button btnNegative = (Button) shInput.findViewById(R.id.btnNegative);
        btnNegative.setText(context.getString(R.string.close_button));
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (action == null) {
                    shInput.dismiss();
                } else {
                    action.inputText = txtInput.getText().toString().trim();
                    action.negativeButtonPressed();
                    shInput.dismiss();
                }
            }
        });
        
        shInput.show();
    }
    
    public static void showError(Context context, String title, String message, final CustomAction action) {
        showError(context, title, message, ContextCompat.getDrawable(Utils.activeContext, R.drawable.ic_error), action, true);
        
    }
    
    public static void showError(Context context, String title, String message, Drawable icon, final CustomAction action, boolean isCancellable) {
        showMessage(context, title, message, icon, action, context.getString(R.string.ok_button), isCancellable,
                    ContextCompat.getColor(context, R.color.colorRedChosen));
    }
    
    public static void showConfirmDialog(String title, String message, CustomAction action, boolean isCancellable) {
        showConfirmDialog(Utils.activeContext,
                          title,
                          message,
                          ContextCompat.getDrawable(Utils.activeContext, R.drawable.ic_question),
                          action,
                          isCancellable,
                          Utils.activeContext.getResources().getString(R.string.yes_button),
                          Utils.activeContext.getResources().getString(R.string.no_button)
        );
    }
    
    public static void showConfirmDialog(String title, String message, CustomAction action, String txtButtonPositive, String txtButtonNegative) {
        showConfirmDialog(title,
                          message,
                          ContextCompat.getDrawable(Utils.activeContext, R.drawable.ic_question),
                          action,
                          txtButtonPositive,
                          txtButtonNegative);
    }
    
    public static void showConfirmDialog(String title, String message, Drawable icon, CustomAction action, String txtButtonPositive, String txtButtonNegative) {
        showConfirmDialog(Utils.activeContext,
                          title,
                          message,
                          icon,
                          action,
                          true,
                          txtButtonPositive,
                          txtButtonNegative);
    }
    
    public static void showConfirmDialog(String title, String message, CustomAction action) {
        showConfirmDialog(Utils.activeContext,
                          title,
                          message,
                          ContextCompat.getDrawable(Utils.activeContext, R.drawable.ic_question),
                          action,
                          true,
                          Utils.activeContext.getResources().getString(R.string.yes_button),
                          Utils.activeContext.getResources().getString(R.string.no_button));
    }
    
    public static void showConfirmDialog(Context context, String title, String message, CustomAction action) {
        showConfirmDialog(context,
                          title,
                          message,
                          ContextCompat.getDrawable(context, R.drawable.ic_question),
                          action,
                          true,
                          Utils.activeContext.getResources().getString(R.string.yes_button),
                          Utils.activeContext.getResources().getString(R.string.no_button));
    }
    
    public static void showConfirmDialog(Context context, String message, CustomAction action) {
        showConfirmDialog(context,
                          context.getResources().getString(R.string.alert_string),
                          message,
                          ContextCompat.getDrawable(context, R.drawable.ic_question),
                          action,
                          true,
                          Utils.activeContext.getResources().getString(R.string.yes_button),
                          Utils.activeContext.getResources().getString(R.string.no_button));
    }
    
    public static void showConfirmDialog(String title, String message, Drawable icon, boolean isCancellable, final CustomAction action) {
        showConfirmDialog(Utils.activeContext,
                          title,
                          message,
                          icon,
                          action,
                          true,
                          Utils.activeContext.getResources().getString(R.string.yes_button),
                          Utils.activeContext.getResources().getString(R.string.no_button));
    }
    
    // END - showConfirmDialog
    
    public static void showConfirmDialog(Context context, String title, String message, Drawable icon, final CustomAction action, boolean isCancellable
            , String txtButtonPositive, String txtButtonNegative) {
        final Dialog shConfirm = customMessageDialog(context, title, message, icon);
        ((LinearLayout) shConfirm.findViewById(R.id.loTitle)).setBackgroundColor(ContextCompat.getColor(context,
                                                                                                        R.color.colorGreenChosen));
        shConfirm.setCancelable(isCancellable);
        Button btnPositive = (Button) shConfirm.findViewById(R.id.btnConfirm);
        
        btnPositive.setText(txtButtonPositive);
        //btnPositive.setText(context.getResources().getString(R.string.yes_button));
        
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.positiveButtonPressed();
                shConfirm.dismiss();
            }
        });
        
        Button btnNegative = (Button) shConfirm.findViewById(R.id.btnNegative);
        btnNegative.setText(txtButtonNegative);
        //btnNegative.setText(context.getResources().getString(R.string.no_button));
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.negativeButtonPressed();
                shConfirm.dismiss();
            }
        });
        
        shConfirm.show();
    }
    
    // START - showMessage
    public static void showMessage(String message) {
        showMessage(Utils.activeContext.getString(R.string.alert_string), message);
    }
    
    public static void showMessage(String title, String message) {
        showMessage(Utils.activeContext, title, message, ContextCompat.getDrawable(Utils.activeContext, R.drawable.ic_info));
    }
    
    public static void showMessage(String title, String message, Drawable icon) {
        showMessage(Utils.activeContext, title, message, icon, null, Utils.activeContext.getResources().getString(R.string.ok_button), true);
    }
    
    public static void showMessage(String title, String message, final CustomAction action) {
        showMessage(Utils.activeContext, title, message, ContextCompat.getDrawable(Utils.activeContext, R.drawable.ic_info),
                    action, Utils.activeContext.getResources().getString(R.string.ok_button), true);
    }
    
    public static void showMessage(String title, String message, final CustomAction action, boolean isCancellable) {
        showMessage(Utils.activeContext, title, message, ContextCompat.getDrawable(Utils.activeContext, R.drawable.ic_info),
                    action, Utils.activeContext.getResources().getString(R.string.ok_button), isCancellable);
    }
    
    public static void showMessage(Context context, String message) {
        showMessage(context, context.getString(R.string.alert_string), message, ContextCompat.getDrawable(context, R.drawable.ic_info));
    }
    
    public static void showMessage(Context context, String title, String message, Drawable icon) {
        showMessage(context, title, message, icon, null, context.getResources().getString(R.string.ok_button), true);
    }
    
    // END - showMessage
    
    public static void showMessage(Context context, String title, String message, final CustomAction action) {
        showMessage(context, title, message, ContextCompat.getDrawable(Utils.activeContext, R.drawable.ic_info), action, context.getResources().getString(R.string.ok_button),
                    true);
    }
    
    public static void showMessage(Context context, String title, String message, Drawable icon,
                                   final CustomAction action, String ok_text, boolean isCancellable,
                                   int titleBackground) {
        
        final Dialog shMessage = customMessageDialog(context, title, message, icon);
        ((LinearLayout) shMessage.findViewById(R.id.loTitle)).setBackgroundColor(titleBackground);
        shMessage.setCancelable(isCancellable);
        
        View btnDismiss = shMessage.findViewById(R.id.btnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (action != null) {
                    action.negativeButtonPressed();
                }
                
                shMessage.dismiss();
            }
        });
        
        Button btnYes = shMessage.findViewById(R.id.btnConfirm);
        btnYes.setText(ok_text);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action != null) {
                    action.positiveButtonPressed();
                }
                shMessage.dismiss();
            }
        });
        
        Button btnNo = (Button) shMessage.findViewById(R.id.btnNegative);
        btnNo.setVisibility(View.GONE);
        
        shMessage.show();
    }
    
    public static void showMessage(Context context, String title, String message, Drawable icon,
                                   final CustomAction action, String ok_text, boolean isCancellable) {
        showMessage(context, title, message, icon, action, context.getResources().getString(R.string.ok_button), true, ContextCompat.getColor(context, R.color.colorBlueChosen));
        
    }
    
    // START - CUSTOM MESSAGE DIALOG
    public static Dialog customMessageDialog(Context context, String title, String message, Drawable icon) {
        final Dialog dialog = new Dialog(context, R.style.alert);
        dialog.setTitle(title);
        dialog.setContentView(R.layout.show_message_layout);
        final TextView txtTitle = dialog.findViewById(R.id.lblTitle);
        TextView lblMessage = dialog.findViewById(R.id.lblMessage);
        //txtMessage.setMovementMethod(new ScrollingMovementMethod());
        
        ImageView imgIcon = dialog.findViewById(R.id.imgIcon);
        
        View btnDismiss = dialog.findViewById(R.id.btnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        
        //If it contains html tags
        if (Pattern.compile("<.*>").matcher(message).find()
                || Pattern.compile("</.*>").matcher(message).find()) {
            //Format as html
            lblMessage.setText(Html.fromHtml(message));
        } else {
            //set message as normal text
            lblMessage.setText(message);
        }
        
        if (title.isEmpty()) {
            txtTitle.setVisibility(View.GONE);
        } else {
            txtTitle.setText(title);
        }
    
        /*
        //Dominant color in image
        Palette.generateAsync(drawableToBitmap(icon), new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                ((LinearLayout) dialog.findViewById(R.id.loTitle)).setBackgroundColor(palette.getDominantColor(0));
            }
        });
        
        //change icon color to white
        icon.setColorFilter(ContextCompat.getColor(context, R.color.colorWhiteLight),
                android.graphics.PorterDuff.Mode.SRC_IN);
         */
        
        if (icon == null) {
            imgIcon.setVisibility(View.GONE);
        } else {
            imgIcon.setImageDrawable(icon);
        }
        
        return dialog;
    }
    
    //
    public static Dialog customInputDialog(Context context, String title, String message, String input, String hint,
                                           Drawable icon) {
        final Dialog dialog = new Dialog(context, R.style.alert);
        dialog.setTitle(title);
        dialog.setContentView(R.layout.show_input_layout);
        TextView txtTitle = dialog.findViewById(R.id.lblTitle);
        TextView lblMessage = dialog.findViewById(R.id.lblMessage);
        EditText txtInput = dialog.findViewById(R.id.txtInput);
        txtInput.setHint(hint);
        
        View btnDismiss = dialog.findViewById(R.id.btnDismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        
        ImageView imgIcon = dialog.findViewById(R.id.imgIcon);
        
        //If it contains html tags
        if (Pattern.compile("<.*>").matcher(message).find()
                && Pattern.compile("</.*>").matcher(message).find()) {
            //Format as html
            lblMessage.setText(Html.fromHtml(message));
        } else {
            //set message as normal text
            lblMessage.setText(message);
        }
        
        txtInput.setText(input);
        
        if (title.isEmpty()) {
            txtTitle.setVisibility(View.GONE);
        } else {
            txtTitle.setText(title);
        }
        
        if (icon == null) {
            imgIcon.setVisibility(View.GONE);
        } else {
            imgIcon.setImageDrawable(icon);
        }
        
        return dialog;
    }
    
    //START - Progress Dialog
    public static void showProgressDialog(String title) {
        showProgressDialog(Utils.activeContext, title, Utils.activeContext.getDrawable(R.drawable.ic_info));
    }
    
    public static void showProgressDialog(Context context, String title) {
        showProgressDialog(context, title, context.getDrawable(R.drawable.ic_info));
    }
    
    public static void showProgressDialog(Context context, String title, Drawable icon) {
        if (prDialog != null && prDialog.isShowing()) {
            return;
        }
        
        prDialog = customProgressDialog(context, title, Utils.getString(context, R.string.please_wait), icon);
        
        Utils.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                prDialog.show();
            }
        });
    }
    
    public static void setProgressText(final String message) {
        if (prDialog != null && prDialog.isShowing()) {
            Utils.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView) prDialog.findViewById(R.id.lblMessage)).setText(message);
                }
            });
        }
    }
    
    public static void closeProgressDialog() {
        if (prDialog != null && prDialog.isShowing()) {
            Utils.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    prDialog.dismiss();
                }
            });
        }
    }
    
    
    private static Dialog customProgressDialog(Context context, String title, String message, Drawable icon) {
        prDialog = new Dialog(context, R.style.alert);
        prDialog.setTitle(title);
        prDialog.setContentView(R.layout.show_progress_layout);
        TextView lblTitle = prDialog.findViewById(R.id.lblTitle);
        TextView lblMessage = prDialog.findViewById(R.id.lblMessage);
        prDialog.setCancelable(false);
        ImageView imgIcon = prDialog.findViewById(R.id.imgIcon);
        
        ProgressBar progressBar = (ProgressBar) prDialog.findViewById(R.id.spin_kit);
        
        Sprite loading = Utils.randomSprite();
        
        switch (new Random().nextInt(5)) {
            case 0:
                loading.setColor(Utils.getColor(R.color.colorYellowLight));
                break;
            case 1:
                loading.setColor(Utils.getColor(R.color.colorGreenLight));
                break;
            case 2:
                loading.setColor(Utils.getColor(R.color.colorBlueLight));
                break;
            case 3:
                loading.setColor(Utils.getColor(R.color.colorTurquoiseLight));
                break;
            case 4:
                loading.setColor(Utils.getColor(R.color.colorRedLight));
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
    
    
    public static void showImageFullScreen(Object icon) {
        showImageFullScreen(icon, "");
    }
    
    public static void showImageFullScreen(Object icon, String text) {
        final Dialog dialog = new Dialog(Utils.activeContext, R.style.alert_transparent);
        dialog.setContentView(R.layout.show_popup_message);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TouchImageView imgIcon = dialog.findViewById(R.id.imgURL);
        
        TextView tvText = dialog.findViewById(R.id.tvText);
        
        tvText.setText(text);
        if (text.isEmpty()) {
            tvText.setVisibility(View.GONE);
        }
        
        View btnDismiss = dialog.findViewById(R.id.btnClose);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        
        if (icon == null) {
            imgIcon.setVisibility(View.GONE);
        } else {
            
            if (icon instanceof Drawable) {
                imgIcon.setImageDrawable((Drawable) icon);
            } else if (icon instanceof Bitmap) {
                imgIcon.setImageBitmap((Bitmap) icon);
            }
            
        }
        
        dialog.show();
    }
    
}

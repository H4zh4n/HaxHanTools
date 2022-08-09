package com.dev.hazhanjalal.haxhantools.utils.ui;

import static com.dev.hazhanjalal.haxhantools.utils.print.Logger.v;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.dev.hazhanjalal.haxhantools.R;
import com.dev.hazhanjalal.haxhantools.utils.implementations.ClassPopup;
import com.dev.hazhanjalal.haxhantools.utils.implementations.CustomAction;
import com.dev.hazhanjalal.haxhantools.utils.print.Logger;
import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;
import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;

import java.util.Random;
import java.util.regex.Pattern;

public class HxPopup {
    
    public static Context activeContext;
    
    public static void showCustomPopup(String imageURL) {
        ClassPopup pop = new ClassPopup();
        pop.popupTime = System.currentTimeMillis();
        pop.popupImageURL = imageURL;
        showCustomPopup(pop);
    }
    
    public static void showCustomPopup(ClassPopup pop) {
        showCustomPopup(Utils.activeContext, pop);
    }
    
    public static void showCustomPopup(final Context context, ClassPopup pop) {
        final Dialog dialog = new Dialog(context, R.style.alert_transparent);
        dialog.setContentView(R.layout.hx_show_popup_message);
        
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        
        final ImageView ivIMGURL = dialog.findViewById(R.id.imgURL);
        
        final CardView mainLayout = dialog.findViewById(R.id.mainLayout);
        final RelativeLayout relativeLayout = dialog.findViewById(R.id.relativeLayout);
        
        ImageView btnClose = dialog.findViewById(R.id.btnClose);
        //ImageView btnShare = dialog.findViewById(R.id.btnShare);
        
        final String imageURL = Utils.replaceEasternNumbers(pop.popupImageURL);
        final String popupVisitURL = Utils.replaceEasternNumbers(pop.popupVisitURL);
        
        //not needed
        //final String fileName = new File(imageURL).getName().toString();
        
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        TextView tvText = dialog.findViewById(R.id.tvText);
        
        if (pop.popupText.contains("<") &&
                pop.popupText.contains(">")) {
            tvText.setText(Html.fromHtml(pop.popupText));
        } else {
            tvText.setText(pop.popupText);
        }
        
        if (pop.popupTextIsAyah) {
            tvText.setTypeface(ResourcesCompat.getFont(context, R.font.me_quran));
        } else {
            //tvText.setTypeface(ResourcesCompat.getFont(context, R.font.bahij_regular));
        }
        
        tvText.setTextColor(pop.colorText);
        Utils.setBackgroundTintForce(pop.colorBackground, relativeLayout);
        
        if (!popupVisitURL.isEmpty()) {
            tvText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_link_24, 0, 0, 0);
            tvText.setBackground(Utils.getDrawable(R.drawable.onclick_highlight_solid_background));
            Utils.setBackgroundTintForce(Utils.getColor(R.color.colorBlueChosen), tvText);
            
            tvText.setTextColor(Utils.getColor(android.R.color.white));
            tvText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.openURL(popupVisitURL);
                }
            });
        }
        
        if (pop.popupText.isEmpty()) {
            tvText.setVisibility(View.GONE);
        } else {
            tvText.setVisibility(View.VISIBLE);
        }
        
        if (!imageURL.isEmpty()) {
            /*btnShare.setVisibility(View.VISIBLE);
            
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.shareBitmapToApps(Utils.drawableToBitmap(ivIMGURL.getDrawable()));
                }
            });*/
            
            ivIMGURL.setVisibility(View.VISIBLE);
            
            if (pop.popupCanTapImage && !popupVisitURL.isEmpty()) {
                //btnShare.setVisibility(View.GONE);
                
                ivIMGURL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.openURL(popupVisitURL);
                    }
                });
            }
            
            v("-- loading image with glide");
            try {
                Utils.getActivity(context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        
                        //in-case custom libraries didn't work. take it to your own hands.
                        //220404 - you can make it so much more efficient,
                        // much better than Glide, tho don't want to work on it now.
                        // you can load image at startup if exists, then force download.
                        // also check if an image is older than some time period delete them.
                        // possibly check for old files in MainActivity.
                       /* Downloader.startDownload(imageURL, "popups",
                                                 fileName,
                                                 false,
                                                 false,
                                                 false,
                                                 new OnCustomDownload() {
                                                     @Override
                                                     public void onDownloadComplete() {
                                                         ivIMGURL.setImageURI(Uri.fromFile(UtilsFile.getFileFullPath("popups", fileName)));
                                                         dialog.show();
                                                     }
                                                 });*/
                        
                        Picasso
                                .get()
                                .load(Utils.replaceEasternNumbers(imageURL).trim())
                                .into(ivIMGURL);
                                
                                /* .listener(new RequestListener<Drawable>() {
                                     @Override
                                     public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                         v("|| couldn't load image.");
                                         e(e);
                                         //dialog.dismiss();
                                         return false;
                                     }
                                     
                                     @Override
                                     public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                         v("onResourceReady");
                                         ivIMGURL.setImageDrawable(resource);
                                         dialog.show();
                                         return true;
                                     }
                                 })*/
                        // [DiskCacheStrategy.ALL] helps cahceh the images,
                        // change to NONE if you didn't want to use cache.
                        //ALL  : replacing images always returns previous cached version.
                                /*.diskCacheStrategy(DiskCacheStrategy.NONE)
                                //.placeholder(Utils.getDrawable(R.drawable.img_loading))
                                .into(ivIMGURL);*/
                        
                        dialog.show();
                        
                    }
                });
            } catch (Exception e) {
                Logger.e(e);
            }
            
            
            
           /* Picasso.get()
                    .load(imageURL)
                    *//*.transform(new RoundedCornersTransformation(10, 0))*//*
                    .into(imgURL, new Callback() {
                        @Override
                        public void onSuccess() {
                        
                        }
                        
                        @Override
                        public void onError(Exception e) {
                        
                        }
                    });*/
        } else {
            // btnShare.setVisibility(View.GONE);
            ivIMGURL.setVisibility(View.GONE);
            dialog.show();
        }
        
    }
    
    
    // START - showInputDialog
    public static void showInputDialog(Context context, String title, String message, String hint, Drawable icon,
                                       final CustomAction action, boolean numberOnly) {
        showInputDialog(context, title, message, "", hint, icon, action, numberOnly,
                        context.getString(R.string.confirm_button), context.getString(R.string.close_button));
    }
    
    public static void showInputDialog(Context context, String title, String message, String hint, Drawable icon,
                                       final CustomAction action) {
        showInputDialog(context, title, message, "", hint, icon, action, false,
                        context.getString(R.string.confirm_button), context.getString(R.string.close_button));
    }
    
    public static void showInputDialog(Context context, String title, String message, String hint,
                                       final CustomAction action, boolean isNumberOnly) {
        showInputDialog(context, title, message, "", hint, context.getDrawable(R.drawable.ic_goto_page), action, isNumberOnly,
                        context.getString(R.string.confirm_button), context.getString(R.string.close_button));
    }
    
    public static void showInputDialog(Context context, String title, String message, final CustomAction action) {
        showInputDialog(context, title, message, "", "", context.getDrawable(R.drawable.ic_goto_page), action, false,
                        context.getString(R.string.confirm_button), context.getString(R.string.close_button));
    }
    
    public static void showInputDialog(Context context, String title, String message, Drawable icon,
                                       final CustomAction action) {
        showInputDialog(context, title, message, "", "", icon, action, false,
                        context.getString(R.string.confirm_button), context.getString(R.string.close_button));
    }
    
    public static void showInputDialog(String title, String message,
                                       final CustomAction action, boolean isNumberOnly, String txtPositiveButton, String txtNegativeButton) {
        
        showInputDialog(Utils.activeContext, title, message, "", "", Utils.getDrawable(R.drawable.ic_edit), action, isNumberOnly,
                        txtPositiveButton, txtNegativeButton);
    }
    
    public static void showInputDialog(String message,
                                       final CustomAction action, boolean isNumberOnly, String txtPositiveButton, String txtNegativeButton) {
        
        showInputDialog(Utils.activeContext, "", message, "", "", Utils.getDrawable(R.drawable.ic_edit), action, isNumberOnly,
                        txtPositiveButton, txtNegativeButton);
    }
    
    public static void showInputDialog(String title, String message, String hint,
                                       final CustomAction action, boolean isNumberOnly, String txtPositiveButton, String txtNegativeButton) {
        
        showInputDialog(Utils.activeContext, title, message, "", hint, Utils.getDrawable(R.drawable.ic_edit), action, isNumberOnly,
                        txtPositiveButton, txtNegativeButton);
    }
    
    public static void showInputDialog(String title, String message,
                                       final CustomAction action, String txtPositiveButton, String txtNegativeButton) {
        
        showInputDialog(Utils.activeContext, title, message, "", "", Utils.getDrawable(R.drawable.ic_edit), action, false,
                        txtPositiveButton, txtNegativeButton);
    }
    
    public static void showInputDialog(String title, String message, String hint,
                                       final CustomAction action, String txtPositiveButton, String txtNegativeButton) {
        
        showInputDialog(Utils.activeContext, title, message, "", hint, Utils.getDrawable(R.drawable.ic_edit), action, false,
                        txtPositiveButton, txtNegativeButton);
    }
    
    //END - customInputDialog
    
    // START - showConfirmDialog
    public static void showInputDialog(Context context, String title, String message, String input, String hint,
                                       Drawable icon,
                                       final CustomAction action, boolean numberOnly, String txtPositiveButton, String txtNegativeButton) {
        
        final Dialog shInput = customInputDialog(context, title, message, input, hint, icon);
        final EditText txtInput = shInput.findViewById(R.id.txtInput);
        
        if (numberOnly) {
            txtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            
            txtInput.setTextSize(24);
            txtInput.setGravity(Gravity.CENTER);
            
            txtInput.setPadding(Utils.dpToPx(20),
                                Utils.dpToPx(20),
                                Utils.dpToPx(20),
                                Utils.dpToPx(20));
            
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
        btnPositive.setText(txtPositiveButton);
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
        btnNegative.setText(txtNegativeButton);
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
                if (action != null) {
                    action.positiveButtonPressed();
                }
                shConfirm.dismiss();
            }
        });
        
        Button btnNegative = (Button) shConfirm.findViewById(R.id.btnNegative);
        btnNegative.setText(txtButtonNegative);
        //btnNegative.setText(context.getResources().getString(R.string.no_button));
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action != null) {
                    action.negativeButtonPressed();
                }
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
        dialog.setContentView(R.layout.hx_show_message_layout);
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
        dialog.setContentView(R.layout.hx_show_input_layout);
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
    
    
    public static void showImageFullScreen(Object icon) {
        showImageFullScreen(icon, "");
    }
    
    public static void showImageFullScreen(Object icon, String text) {
        final Dialog dialog = new Dialog(Utils.activeContext, R.style.alert_transparent);
        dialog.setContentView(R.layout.hx_show_popup_message);
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
    
    private static int getRandomNumber() {
        return new Random().nextInt(900000) + 100000;
    }
    
    public static void showCustomPopupWithText(String text) {
        showCustomPopup(text, null);
    }
    
    
    public static void showCustomPopup(String text, Bitmap img) {
        showCustomPopup(System.currentTimeMillis(), text, img, false, "");
    }
    
    public static void showCustomPopup(long time, String text, Bitmap img) {
        showCustomPopup(time, text, img, false, "");
    }
    
    public static void showCustomPopup(long time, String text, Bitmap img, boolean canTapImage, String popupVisitURL) {
        showCustomPopup(Utils.activeContext, time, text, img, canTapImage, popupVisitURL);
    }
    
    public static void showCustomPopup(final Context context, long time, String text, Bitmap img, boolean canTapImage, String xPopupVisitURL) {
        final Dialog dialog = new Dialog(context, R.style.alert_transparent);
        dialog.setContentView(R.layout.hx_show_popup_message);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        
        final ImageView ivIMGURL = dialog.findViewById(R.id.imgURL);
        ImageView btnClose = dialog.findViewById(R.id.btnClose);
        //  ImageView btnShare = dialog.findViewById(R.id.btnShare);
        
        final String popupVisitURL = Utils.replaceEasternNumbers(xPopupVisitURL);
        
        //not needed
        //final String fileName = new File(imageURL).getName().toString();
        
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        TextView tvText = dialog.findViewById(R.id.tvText);
        
        if (text.contains("<") &&
                text.contains(">")) {
            tvText.setText(Html.fromHtml(text));
        } else {
            tvText.setText(text);
        }
        
        if (!popupVisitURL.isEmpty()) {
            tvText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_link_24, 0, 0, 0);
            tvText.setBackground(Utils.getDrawable(R.drawable.onclick_highlight_solid_background));
            Utils.setBackgroundTintForce(Utils.getColor(R.color.colorBlueChosen), tvText);
            
            tvText.setTextColor(Utils.getColor(android.R.color.white));
            tvText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.openURL(popupVisitURL);
                }
            });
        }
        
        if (text.isEmpty()) {
            tvText.setVisibility(View.GONE);
        } else {
            tvText.setVisibility(View.VISIBLE);
        }
        
        if (img != null) {
            /*btnShare.setVisibility(View.VISIBLE);
            
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.shareBitmapToApps(Utils.drawableToBitmap(ivIMGURL.getDrawable()));
                }
            });*/
            
            ivIMGURL.setVisibility(View.VISIBLE);
            
            if (canTapImage && !popupVisitURL.isEmpty()) {
                // btnShare.setVisibility(View.GONE);
                
                ivIMGURL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.openURL(popupVisitURL);
                    }
                });
            }
            
            try {
                Utils.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        
                        //in-case custom libraries didn't work. take it to your own hands.
                        //220404 - you can make it so much more efficient,
                        // much better than Glide, tho don't want to work on it now.
                        // you can load image at startup if exists, then force download.
                        // also check if an image is older than some time period delete them.
                        // possibly check for old files in MainActivity.
                       /* Downloader.startDownload(imageURL, "popups",
                                                 fileName,
                                                 false,
                                                 false,
                                                 false,
                                                 new OnCustomDownload() {
                                                     @Override
                                                     public void onDownloadComplete() {
                                                         ivIMGURL.setImageURI(Uri.fromFile(UtilsFile.getFileFullPath("popups", fileName)));
                                                         dialog.show();
                                                     }
                                                 });*/
                        
                        ivIMGURL.setImageBitmap(img);
                        
                        dialog.show();
                        
                    }
                });
            } catch (Exception e) {
            
            }
        } else {
            //btnShare.setVisibility(View.GONE);
            ivIMGURL.setVisibility(View.GONE);
            dialog.show();
        }
        
    }
    
}

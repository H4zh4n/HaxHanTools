package com.dev.hazhanjalal.haxhantools.utils.utils;


import static com.dev.hazhanjalal.haxhantools.utils.print.Logger.e;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.dev.hazhanjalal.haxhantools.R;
import com.dev.hazhanjalal.haxhantools.utils.implementations.CustomAction;
import com.dev.hazhanjalal.haxhantools.utils.print.Logger;
import com.dev.hazhanjalal.haxhantools.utils.ui.HxToast;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.RotatingCircle;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.github.ybq.android.spinkit.style.Wave;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;


/*
libs needed to be imported :
* //TapTarget
 implementation 'com.getkeepsafe.taptargetview:taptargetview:1.12.0'

*   //Loading lib
    implementation 'com.github.ybq:Android-SpinKit:1.4.0'
* My Custom Imports :
    HxToast
    HxPopup
 */

public class Utils {
    public static SharedPreferences prefs;
    public static Context activeContext;
    public static int[] colors;
    
    public Utils(Context con) {
        activeContext = con;
    
        Logger.setEnabled(con);
    
        colors = new int[]{
                Utils.getColor(R.color.colorBlueChosen),
                Utils.getColor(R.color.colorBlueDark),
                Utils.getColor(R.color.colorBlueLight),
            
                Utils.getColor(R.color.colorOrangeDark),
                Utils.getColor(R.color.colorOrangeLight),
            
                Utils.getColor(R.color.colorGreenChosen),
                Utils.getColor(R.color.colorGreenDark),
                Utils.getColor(R.color.colorGreenLight),
                
                Utils.getColor(R.color.colorGrayDark),
                Utils.getColor(R.color.colorGrayLight),
                
                Utils.getColor(R.color.colorPinkChosen),
                Utils.getColor(R.color.colorPinkDark),
                Utils.getColor(R.color.colorPinkLight),
                
                Utils.getColor(R.color.colorRedChosen),
                Utils.getColor(R.color.colorRedDark),
                Utils.getColor(R.color.colorRedLight),
                
                Utils.getColor(R.color.colorPurpleChosen),
                Utils.getColor(R.color.colorPurpleDark),
                Utils.getColor(R.color.colorPurpleLight),
                
                Utils.getColor(R.color.colorTurquoiseChosen),
                Utils.getColor(R.color.colorTurquoiseDark),
                Utils.getColor(R.color.colorTurquoiseLight),
                
                Utils.getColor(R.color.colorYellowChosen),
                Utils.getColor(R.color.colorYellowDark),
                Utils.getColor(R.color.colorYellowLight),
                
                Utils.getColor(R.color.colorBackgroundLighter),
                Utils.getColor(R.color.colorBlackDark),
                
                Utils.getColor(R.color.white),
                Utils.getColor(R.color.colorWhiteDark),
                Utils.getColor(R.color.colorWhiteLight),
                
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)),
                Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255))
        };
        
        if (prefs == null) {
            registerPrefs(con);
        }
    }
    
    public static int toDP(int value) {
        return toDP(activeContext, value);
    }
    
    public static int toDP(Context context, int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                               (float) value, context.getResources().getDisplayMetrics());
    }
    
    public static boolean keepScreen() {
        return Utils.getBooleanPref("always_keep_screen", true);
    }
    
    public static String getString(int id) {
        return getString(activeContext, id);
    }
    
    public static String getString(Context ctx, int id) {
        return ctx.getString(id);
    }
    
    public static int getColor(int id) {
        return getColor(Utils.activeContext, id);
    }
    
    public static int getColor(Context context, int id) {
        return ContextCompat.getColor(context, id);
    }
    
    public static String colorIntToHex(int color) {
        String hexColor = "#" + Integer.toHexString(color).substring(2);
        return hexColor;
    }
    
    
    public static Typeface getTypeFace(int id) {
        return ResourcesCompat.getFont(Utils.activeContext, id);
    }
    
    public static Drawable getDrawable(int id) {
        return getDrawable(Utils.activeContext, id);
    }
    
    public static Drawable getDrawable(Context context, int id) {
        return ContextCompat.getDrawable(context, id);
    }
    
    
    public static int getDrawableByName(String name) {
        try {
            return getResourceIdByName("drawable", name);
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static int getResourceIdByName(String resource, String name) {
        try {
            return Utils.activeContext
                    .getResources()
                    .getIdentifier(Utils.activeContext.getPackageName() +
                                           ":" + resource + "/" + name, null, null);
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
    
    public static int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    
    public static void restartThis() {
        restartThis(activeContext, -1);
    }
    
    public static void restartThis(Context ctx) {
        restartThis(ctx, -1);
    }
    
    
    /*public static String getString(String number) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US")); //or "nb","No" - for Norway
        return nf.format(number);
    }*/
    
    /*public static int getInt(int number) {
        return Integer.parseInt(getString(number + ""));
    }*/
    
    public static void restartThis(Context con, int ayah) {
        Intent in = ((Activity) con).getIntent();
        in.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        
        ((Activity) con).overridePendingTransition(0, 0);
        ((Activity) con).finish();
        ((Activity) con).overridePendingTransition(0, 0);
        (con).startActivity(in);
    }
    
    public static void openURL(String url) {
        activeContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
    
    
    /*public static void sendEmail(String title) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"haxhan@null.net"});
        
        try {
            getActivity().startActivity(Intent.createChooser(emailIntent, "Send Email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            HxToast.showToastError("There are no email clients installed.");
        }
    }*/
    
    public static void put(String key, Object val) {
        if (prefs != null) {
            
            if (val instanceof Integer) {
                prefs.edit().putInt(key, (int) val).commit();
            } else if (val instanceof String) {
                prefs.edit().putString(key, val.toString()).commit();
            } else if (val instanceof Long) {
                prefs.edit().putLong(key, (Long) val).commit();
            } else if (val instanceof Boolean) {
                prefs.edit().putBoolean(key, (Boolean) val).commit();
            } else if (val instanceof Float) {
                prefs.edit().putFloat(key, (Float) val).commit();
            }
        } else {
            registerPrefs(Utils.activeContext);
            put(key, val);
        }
        
    }
    
    public static float getFloatPref(String key, float def) {
        if (prefs != null) {
            return prefs.getFloat(key, def);
        }
        
        return -1;
    }
    
    public static int getIntegerPref(String key, int def) {
        if (prefs != null) {
            return prefs.getInt(key, def);
        }
        
        return -1;
    }
    
    public static long getLongPref(String key, long def) {
        if (prefs != null) {
            return prefs.getLong(key, def);
        }
        
        return -1;
    }
    
    public static String getStringPref(String key, String def) {
        if (prefs != null) {
            return prefs.getString(key, def);
        }
    
        return null;
    }
    
    public static void removeKeyPrefs(String key) {
        if (prefs.contains(key)) {
            prefs.edit().remove(key).commit();
        }
    }
    
    public static boolean prefsContains(String key) {
        if (prefs != null) {
            if (prefs.contains(key)) {
                return true;
            } else {
                return false;
            }
        } else {
            e("SharedPreferences not initialized.");
        }
        return false;
    }
    
    
    public static boolean getBooleanPref(String key, boolean def) {
        if (prefs != null) {
            return prefs.getBoolean(key, def);
        }
        
        return false;
    }
    
    public static void registerPrefs(Context con) {
        if (con == null) {
            con = activeContext;
        }
        
        prefs = PreferenceManager.getDefaultSharedPreferences(con);
    }
    
    public static void shareBitmapToApps(Bitmap bmp) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("image/*");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // i.putExtra(Intent.EXTRA_TEXT, "#تەفسیری_نوور" + "\n" + Utils.MAIN_LINK);
            i.putExtra(Intent.EXTRA_STREAM, getImageUri(activeContext, bmp));
    
            HxToast.showToastSuccess("Picture added to gallery.");
    
            activeContext.startActivity(Intent.createChooser(i, ""));
        } catch (android.content.ActivityNotFoundException ex) {
            // e(ex);
        }
    }
    
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    
    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            
            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "TafseeriNoor_" + System.currentTimeMillis(), null);
            return Uri.parse(path);
        } catch (Exception e) {
            // e(e);
            return null;
        }
        
    }
    
    public static Bitmap getBitmapFromView(View view) {
        //method 1 -- Works-aswell
        
        //  this is the important code, Without it the view will have a dimension of 0,0 and the bitmap will be null
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b = view.getDrawingCache();
        return b;
        
        //Method 2
        /*Bitmap bitmap = Bitmap.createBitmap(
                view.getChildAt(0).getWidth(),
                view.getChildAt(0).getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.getChildAt(0).draw(c);
        return bitmap;*/
        
        //Method 3
        /*Bitmap b = null;
        try {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY),
                         View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY));
        
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        
            view.buildDrawingCache(true);
            b = view.getDrawingCache();
            view.setDrawingCacheEnabled(false); // clear drawing cache
        
            return b;
        } catch (Exception e) {
            Log.e("HAX", "", e);
            return null;
        }*/
        
    }
    
    public static boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(activeContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activeContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    
    public static boolean isPermissionGranted(String... perm) {
        boolean isGranted = true;
        for (int i = 0; i < perm.length; i++) {
            if (ContextCompat.checkSelfPermission(activeContext, perm[i]) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        
        return isGranted;
    }
    
    
    public static void showRequestPermission(String... perms) {
        ActivityCompat.requestPermissions((Activity) activeContext,
                                          perms, 20);
    }
    
    public static void showRequestPermission() {
        showRequestPermission(false);
    }
    
    public static void showRequestPermission(final boolean isRestartNeeded) {
        /* Utils.showStuff = false;*/
        /*HxPopup.showConfirmDialog("ڕێگەدان بە بەرنامە", "تكایە لە ڕووكاری دواتر ئەگەر سماح/" +
                                            "Allow دەركەوت دەستی لێ بدە تا بەرنامەكە " +
                                            "بتوانێت كاربكات بەبێ كێشە.",
                                    new CustomAction() {
                                        @Override
                                        public void positiveButtonPressed() {
                                            ActivityCompat.requestPermissions((Activity) activeContext,
                                                                              new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                                                                                      , Manifest.permission.READ_EXTERNAL_STORAGE}, 20);
                
                                            *//* Utils.showStuff = true;*//*
                                        }
            
                                        @Override
                                        public void negativeButtonPressed() {
                                            super.negativeButtonPressed();
                                            if (isRestartNeeded) {
                                                Utils.restartThis();
                                            }
                                        }
                                    }, "باشە", "لابردن");*/
    
    }
    
    
    /**
     * Set background tint for view by Color ID
     */
    public static void setBackgroundTint(int colorID, View... v) {
        setBackgroundTint(activeContext, colorID, true, v);
    }
    
    /**
     * Set background tint for view by Color int
     */
    public static void setBackgroundTintByColor(int color, View... v) {
        setBackgroundTint(activeContext, color, false, v);
    }
    
    /**
     * Set background tint for view by Color ID
     */
    public static void setBackgroundTint(Context context, int colorID, View... v) {
        setBackgroundTint(context, colorID, true, v);
    }
    
    /**
     * Set background tint for view
     */
    public static void setBackgroundTint(Context context, int colorID, boolean isID, View... v) {
        for (int i = 0; i < v.length; i++) {
            v[i].setBackgroundTintList(context.getResources().getColorStateList(colorID));
        }
    }
    
    /**
     * FORCE background tint by color
     */
    public static void setBackgroundTintForce(int color, View... v) {
        // change SRC if issues occur
        for (int i = 0; i < v.length; i++) {
            v[i].getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }
    
    /**
     * Change the tint/color of a drawable
     */
    public static Drawable setDrawableTint(Drawable d, int color) {
        d.clearColorFilter();
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }
    
    /**
     * Remove the Drawable from the sides of a view.
     *
     * @param view the view/component which to rmove the image from. allows (TextView, Button, CheckBox, RadioButton).
     */
    public static void removeDrawableFromSides(View view) {
        setDrawableSides(0, "", view);
    }
    
    /**
     * Set Drawable to the right side of a view.
     *
     * @param drawableId send the ID of the drawable in drawable folder.
     * @param view       the view/component which to put the image into. allows (TextView, Button, CheckBox, RadioButton).
     */
    public static void setDrawableSides(int drawableId, View view) {
        setDrawableSides(drawableId, "right", view);
    }
    
    /**
     * Set Drawable to side of a view.
     *
     * @param drawableId send the ID of the drawable in drawable folder.
     * @param position   where to put the drawable. send one of (l,r,t,b) or (left, right, top, bottom).
     * @param view       the view/component which to put the image into. allows (TextView, Button, CheckBox, RadioButton).
     */
    public static void setDrawableSides(int drawableId, String position, View view) {
        try {
            int left = 0, right = 0, top = 0, bottom = 0;
            switch (position.toLowerCase().substring(0, 1)) {
                case "l":
                    left = drawableId;
                    break;
                case "r":
                    right = drawableId;
                    break;
                case "t":
                    top = drawableId;
                    break;
                case "b":
                    bottom = drawableId;
                    break;
                default:
                    right = drawableId;
                    break;
            }
            
            if (view instanceof TextView) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
            } else if (view instanceof Button) {
                ((Button) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
            } else if (view instanceof CheckBox) {
                ((CheckBox) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
            } else if (view instanceof RadioButton) {
                ((RadioButton) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
            }
        } catch (Exception e) {
        
        }
    }
    
    public static void setStrokeColor(View v, int color) {
        setStrokeColor(v, color, 5);
    }
    
    public static void setStrokeColor(View v, int color, int strokeWidth) {
        GradientDrawable drawable = (GradientDrawable) v.getBackground();
        drawable.setStroke(strokeWidth, color); // set stroke width and stroke color
    }
    
    public static void deepExpandCollapse(ViewGroup parentLayout) {
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View view = parentLayout.getChildAt(i);
            
            if (view instanceof ViewGroup) {
                if (view.getTag() != null
                        && view.getTag().toString().contains("content")) {
                    if (view.getVisibility() == View.GONE) {
                        view.setVisibility(View.VISIBLE);
                    } else {
                        view.setVisibility(View.GONE);
                    }
                }
                deepExpandCollapse((ViewGroup) view);
            } else if (view instanceof ImageView) {
                if (view.getTag() != null
                        && view.getTag().toString().contains("collapse_image")) {
                    if (view.getRotation() == 180) {
                        view.setRotation(0);
                    } else {
                        view.setRotation(180);
                    }
                }
            }
        }
    }
    
    public static boolean isRightToLeft() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
    }
    
    public static Activity getActivity() {
        return getActivity(activeContext);
    }
    
    public static Activity getActivity(Context context) {
        return ((Activity) context);
    }
    
    //RAM Related methods
    public static long getRamTotal() {
        final Runtime runtime = Runtime.getRuntime();
        return runtime.maxMemory() / 1048576L;
    }
    
    public static long getRamUsed() {
        final Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
    }
    
    public static long getRamAvailable() {
        return getRamTotal() - getRamUsed();
    }
    
    public static double getMemorySizeInGB() {
        try {
            return Double.parseDouble(Utils.replaceEasternNumbers(getMemorySizeHumanized("gb")));
        } catch (Exception e) {
            //e(e);
            return 0;
        }
    }
    
    public static String getMemorySizeHumanized() {
        return Utils.replaceEasternNumbers(getMemorySizeHumanized(""));
    }
    
    public static String getMemorySizeHumanized(String spesific) {
        Context context = Utils.activeContext;
        String finalValue = "";
        
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            
            activityManager.getMemoryInfo(memoryInfo);
            
            DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
            
            long totalMemory = memoryInfo.totalMem;
            
            double kb = totalMemory / 1024.0;
            double mb = totalMemory / 1048576.0;
            double gb = totalMemory / 1073741824.0;
            double tb = totalMemory / 1099511627776.0;
            
            if (spesific.isEmpty()) {
                if (tb > 1) {
                    finalValue = twoDecimalForm.format(tb).concat(" TB");
                } else if (gb > 1) {
                    finalValue = twoDecimalForm.format(gb).concat(" GB");
                } else if (mb > 1) {
                    finalValue = twoDecimalForm.format(mb).concat(" MB");
                } else if (kb > 1) {
                    finalValue = twoDecimalForm.format(kb).concat(" KB");
                } else {
                    finalValue = twoDecimalForm.format(totalMemory).concat(" Bytes");
                }
            } else {
                //return memory in spesific format
                if (spesific.equalsIgnoreCase("byte")) {
                    finalValue = twoDecimalForm.format(totalMemory);
                } else if (spesific.equalsIgnoreCase("kb")) {
                    finalValue = twoDecimalForm.format(kb);
                } else if (spesific.equalsIgnoreCase("mb")) {
                    finalValue = twoDecimalForm.format(mb);
                } else if (spesific.equalsIgnoreCase("gb")) {
                    finalValue = twoDecimalForm.format(gb);
                } else if (spesific.equalsIgnoreCase("tb")) {
                    finalValue = twoDecimalForm.format(tb);
                }
            }
        } catch (Exception e) {
            // e(e);
            finalValue = "";
        }
        
        return finalValue;
    }
    
    public static boolean isNightEnabled() {
        return getBooleanPref("isNightEnabled", false);
    }
    
    //END RAM Methods
    
    public static long getBuildVer() {
        long build = 1;
        try {
            PackageInfo pInfo = null;
            pInfo = activeContext.getPackageManager().getPackageInfo(activeContext.getPackageName(), 0);
            
            if (Build.VERSION.SDK_INT >= 28) {
                build = pInfo.getLongVersionCode();
            } else {
                build = pInfo.versionCode;
            }
        } catch (Exception e) {
            //  e(e);
        }
        return build;
    }
    
    public static String getVerName() {
        String name = "";
        try {
            PackageInfo pInfo = null;
            
            pInfo = activeContext.getPackageManager().getPackageInfo(activeContext.getPackageName(), 0);
            
            name = pInfo.versionName;
            
        } catch (Exception e) {
            // e(e);
        }
        return name;
    }
    
    public static int dpToPx(float dips) {
        return (int) (dips * activeContext.getResources().getDisplayMetrics().density + 0.5f);
    }
    
    public static Bitmap resizeBitmap(Bitmap bmp) {
        return resizeBitmap(bmp, 500, 10);
    }
    
    public static Bitmap resizeBitmap(Bitmap bmp, double width, double margin) {
        double ratio = 1;
        
        if (bmp.getWidth() <= width + margin) {
            return bmp;
        } else {
            ratio = width / bmp.getWidth();
        }
        
        return Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() * ratio), (int) (bmp.getHeight() * ratio), true);
    }
    
    public static boolean isNetworkAvailable() {
        return isNetworkAvailable(null);
    }
    
    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable(final String textOnNoNetwork) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) activeContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        boolean isAvailable = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null
                && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null
                && connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        
        if (textOnNoNetwork != null
                && !textOnNoNetwork.isEmpty()
                && !isAvailable) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    HxToast.showToastError(textOnNoNetwork);
                }
            });
        }
        
        return isAvailable;
    }
    
    public static String entangle(String text) {
        
        String output = "Utils.disentangle(";
        
        for (int i = 0; i < text.length(); i++) {
            output += ((int) text.charAt(i)) + ",";
        }
        output += ");";
        
        return output;
    }
    
    public static String disentangle(int... nums) {
        String text = "";
        
        for (int i = 0; i < nums.length; i++) {
            text += (char) nums[i];
        }
        
        return text;
    }
    
    public static String getColoredHTMLText(String text, String color) {
        return String.format("<font color=\"%s\"> %s </font>",
                             color, text);
    }
    
    public static void doLater(int milli, final CustomAction act) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                act.positiveButtonPressed();
            }
        }, milli);
    }
    
    public static void copyToClip(String text) {
        copyToClip(text, true);
    }
    
   
    /*
    //incase TapTarget was used. [lib = com.getkeepsafe.taptargetview:taptargetview:1.12.0]
    public static TapTarget customTargetForView(View v, String title, String desc, Drawable icon) {
        return TapTarget.forView(v,
                                 title,
                                 desc)
                .titleTextSize(26)
                .titleTextColor(R.color.colorWhiteLight)
                .descriptionTextSize(16)
                .icon(icon)
                .cancelable(false)
                .descriptionTextColor(R.color.colorWhiteLight)
                .textColor(R.color.colorWhiteLight)
                .textTypeface(ResourcesCompat.getFont(activeContext, R.font.nrt_bold))
                .dimColor(R.color.colorBlackDark)
                .targetRadius(60);
    }
    
    public static TapTarget customTargetForBound(Rect v, String title, String desc, Drawable icon) {
        return TapTarget.forBounds(v,
                                   title,
                                   desc)
                .titleTextSize(26)
                .icon(icon)
                .titleTextColor(R.color.colorWhiteLight)
                .descriptionTextSize(16)
                .cancelable(false)
                .descriptionTextColor(R.color.colorWhiteLight)
                .textColor(R.color.colorWhiteLight)
                .textTypeface(ResourcesCompat.getFont(activeContext, R.font.nrt_bold))
                .dimColor(R.color.colorBlackDark)
                .targetRadius(60);
    }
    
    public static TapTarget customTargetForView(View v, String title, String desc) {
        return TapTarget.forView(v,
                                 title,
                                 desc)
                .titleTextSize(26)
                .titleTextColor(R.color.colorWhiteLight)
                .descriptionTextSize(16)
                .cancelable(false)
                .descriptionTextColor(R.color.colorWhiteLight)
                .textColor(R.color.colorWhiteLight)
                .textTypeface(ResourcesCompat.getFont(activeContext, R.font.nrt_bold))
                .dimColor(R.color.colorBlackDark)
                .targetRadius(60);
    }*/
    
    public static void copyToClip(String text, boolean showToast) {
        copyToClip(activeContext, text, showToast);
    }
    
    public static void removeGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener) {
        if (Build.VERSION.SDK_INT < 16) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(globalLayoutListener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
        }
    }
    
    public static void whenViewIsVisible(final View v, final CustomAction action) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                action.positiveButtonPressed();
                removeGlobalLayoutListener(v, this);
            }
        });
    }
    
    @SuppressWarnings("deprecation")
    public static void copyToClip(Context context, String text, boolean showToast) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text.trim());
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
        
        if (showToast) {
            HxToast.showToast(context,
                              "Copied" /*+ text*/,
                              ContextCompat.getDrawable(activeContext, R.drawable.ic_copy),
                              ContextCompat.getColor(activeContext, R.color.colorBlueChosen));
        }
    }
    
    public static String replaceEasternNumbers(Object original) {
        //for arabic
        original = original.toString().replaceAll("١", "1")
                .replaceAll("٢", "2")
                .replaceAll("٣", "3")
                .replaceAll("٤", "4")
                .replaceAll("٥", "5")
                .replaceAll("٦", "6")
                .replaceAll("٧", "7")
                .replaceAll("٨", "8")
                .replaceAll("٩", "9")
                .replaceAll("٠", "0");
        
        //for persian
        original = original.toString().replaceAll("۱", "1")
                .replaceAll("۲", "2")
                .replaceAll("۳", "3")
                .replaceAll("۴", "4")
                .replaceAll("۵", "5")
                .replaceAll("۶", "6")
                .replaceAll("۷", "7")
                .replaceAll("۸", "8")
                .replaceAll("۹", "9")
                .replaceAll("۰", "0");
        
        return original.toString();
    }
    
    public static String replaceEnglishNumbers(Object original) {
        return original.toString().replaceAll("1", "١")
                .replaceAll("2", "٢")
                .replaceAll("3", "٣")
                .replaceAll("4", "٤")
                .replaceAll("5", "٥")
                .replaceAll("6", "٦")
                .replaceAll("7", "٧")
                .replaceAll("8", "٨")
                .replaceAll("9", "٩")
                .replaceAll("0", "٠");
    }
    
    public static Sprite randomSprite() {
        switch (new Random().nextInt(12)) {
            case 0:
                return new RotatingPlane();
            case 1:
                return new DoubleBounce();
            case 2:
                return new Wave();
            case 3:
                return new WanderingCubes();
            case 4:
                return new Pulse();
            case 5:
                return new ChasingDots();
            case 6:
                return new ThreeBounce();
            case 7:
                return new Circle();
            case 8:
                return new CubeGrid();
            case 9:
                return new FadingCircle();
            case 10:
                return new FoldingCube();
            case 11:
                return new RotatingCircle();
            default:
                return new FoldingCube();
        }
    }
    
    public static String loadDataFromAsset(String fileName) {
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            fileName = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return fileName;
    }
    
    
    public static void loadDataFromURL(String url, CustomAction action) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // v("-- loading data from " + url);
                    URL u = new URL(url);
                    Scanner input = new Scanner(u.openStream());
                    StringBuilder data = new StringBuilder();
                    
                    while (input.hasNextLine()) {
                        data.append(input.nextLine() + "\n");
                    }
                    
                    if (action != null) {
                        action.inputText = data.toString();
                        action.object = data.toString();
                    }
    
                    //  v("-- data loaded from " + url);
                    
                } catch (Exception e) {
                    //  e(e);
                    if (action != null) {
                        action.inputText = e.getLocalizedMessage();
                        action.object = e;
                        action.negativeButtonPressed();
                    }
                    
                }
                
                if (action != null) {
                    action.positiveButtonPressed();
                }
                
            }
        }).start();
    }
    
    public static String formatSecondsToTimeKurdish(long seconds) {
        if (seconds <= 0) {
            return "";
        }
        long h = seconds / 3600;
        long m = seconds % 3600 / 60;
        long s = seconds % 60; // Less than 60 is the second, enough 60 is the minute
        
        String finalText = "";
        
        if (h > 0) {
            finalText += h + " ك ";
        }
        
        if (m > 0) {
            finalText += m + " خ ";
        }
        
        if (s > 0) {
            finalText += s + " چ ";
        }
        
        return finalText;
    }
    
    public static String formatDateFromMillis(String format, long time) {
        return new SimpleDateFormat(format).format(new Date(time)).toString();
    }
    
    public static byte[] getBlobFromBitmap(Bitmap proof) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        proof.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }
    
    public static Bitmap getBitmapFromBlob(byte[] blob) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        return bitmap;
    }
    
    public static void setStatusBarColor(int color) {
        getActivity().getWindow().setStatusBarColor(color);
    }
    
    public static void setActionBarColor(ActionBar supportActionBar, int colorBackground, int colorText) {
        //Don't forget to set activeContext before calling this.
        setActionBarColor(activeContext, supportActionBar, colorBackground, colorText);
    }
    
    public static void setActionBarColor(Context context, ActionBar supportActionBar, int colorBackground, int colorText) {
        Utils.getActivity(context).setTitle(Html.fromHtml("<font color='" + colorIntToHex(colorText) + "'>" + Utils.getActivity().getTitle() + "</font>"));
        supportActionBar.setBackgroundDrawable(new ColorDrawable(colorBackground));
    }
    
    public static void setBackButtonColor(ActionBar supportActionBar, int colorText) {
        //Don't forget to set activeContext before calling this.
        setBackButtonColor(activeContext, supportActionBar, colorText);
    }
    
    public static void setBackButtonColor(Context context, ActionBar supportActionBar, int colorText) {
        Drawable backArrow = ContextCompat.getDrawable(activeContext, R.drawable.ic_back);
        backArrow.setColorFilter(colorText, PorterDuff.Mode.SRC_ATOP);
        supportActionBar.setHomeAsUpIndicator(backArrow);
    }
    
    public static int getDarker(int color) {
        return getDarker(color, 20);
    }
    
    public static int getDarker(int color, float percent) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= (1 - percent / 100);
        return Color.HSVToColor(hsv);
    }
    
    public static int getLighter(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= (1 + 0.8f);
        return Color.HSVToColor(hsv);
    }
    
    public static int getLighter(int color, float percent) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= (1 + percent / 100);
        return Color.HSVToColor(hsv);
    }
    
    /**
     * Show Keyboard [use toggleKeyboard if this didn't work]
     *
     * @param view desired view to focus on.
     */
    public static void showKeyboard(View view) {
        showKeyboard(view, Utils.getActivity());
    }
    
    /**
     * Show Keyboard [use toggleKeyboard if this didn't work]
     *
     * @param view     desired view to focus on.
     * @param activity current activity
     */
    public static void showKeyboard(View view, Activity activity) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            
            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            
            boolean isShowing = imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            if (!isShowing) {
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        }
    }
    
    /**
     * Toggle Keyboard showing.
     *
     * @param view desired view to focus on.
     */
    public static void toggleKeyboard(View view) {
        toggleKeyboard(view, Utils.getActivity());
    }
    
    /**
     * Toggle Keyboard showing.
     *
     * @param view     desired view to focus on.
     * @param activity current activity
     */
    public static void toggleKeyboard(View view, Activity activity) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }
    
    public static void hideKeyboard() {
        hideKeyboard(getActivity());
    }
    
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    
    public static boolean checkTextNotNullEmpty(String... text) {
        return checkTextNotNullEmpty(true, text);
    }
    
    public static boolean checkTextNotNullEmpty(boolean allMustHaveText, String... text) {
        for (int i = 0; i < text.length; i++) {
            
            if (text[i] != null && !text[i].isEmpty()) {
                if (!allMustHaveText) {
                    return true;
                }
            } else if (text[i] == null || text[i].isEmpty()) {
                if (allMustHaveText) {
                    return false;
                }
            }
        }
        
        if (allMustHaveText) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String formatNumber(String value) {
        return formatNumber(Double.parseDouble(value), 2, false);
    }
    
    
    public static String formatNumber(String value, int decimals) {
        return formatNumber(value, decimals, false);
    }
    
    public static String formatNumber(String value, int decimals, boolean haveDollarSign) {
        if (value.contains("$")) {
            value = value.replaceAll("\\$", "");
        }
        return formatNumber(Double.parseDouble(value), decimals, haveDollarSign);
    }
    
    public static String formatNumber(double value) {
        return formatNumber(value, 2, true);
    }
    
    public static String formatNumber(double value, int decimals) {
        return formatNumber(value, decimals, true);
    }
    
    public static String formatNumber(double value, int decimals, boolean haveDollarSign) {
        String sign = "";
        
        if (haveDollarSign) {
            sign = "$";
        }
    
        return sign + String.format("%,." + decimals + "f", value);
    }
    
    public static float removeExtraFromNumber(String num) {
        num = num.trim().replaceAll("\\$", "").replaceAll("\\,", "");
        //num = num.trim().replaceAll("%D", "");
        
        return Float.parseFloat(num);
    }
    
    
    public static void shortVibrate() {
        shortVibrate(Utils.activeContext);
    }
    
    public static void shortVibrate(Context context) {
        vibrate(context, 50);
    }
    
    public static void longVibrate() {
        longVibrate(Utils.activeContext);
    }
    
    public static void longVibrate(Context context) {
        vibrate(context, 50 * 10);
    }
    
    public static void vibrate(long duration) {
        vibrate(Utils.activeContext, duration);
    }
    
    public static void vibrate(Context context, long duration) {
        try {
            ((Vibrator) context.getSystemService(context.VIBRATOR_SERVICE)).vibrate(duration);
        } catch (Exception e) {
            e(e);
        }
    }
    
    public static String removeDuplicateCharacters(String text) {
        return text.replaceAll("(.)(?=.*\\1)", "");
    }
    
    /**
     * Detect hit between two ImageViews.
     * Doesn't work well. Use hitDetectionV2()
     */
    @Deprecated
    public static boolean hitDetectionV1(ImageView net, ImageView ball) {
        Rect rc1 = new Rect();
        net.getDrawingRect(rc1);
        Rect rc2 = new Rect();
        ball.getDrawingRect(rc2);
        return Rect.intersects(rc1, rc2);
    }
    
    /**
     * Detect hit between two ImageViews
     *
     * @param first  First Views
     * @param second second Views
     */
    public static boolean hitDetectionV2(View first, View second) {
        Rect BallRect = new Rect();
        second.getHitRect(BallRect);
        Rect NetRect = new Rect();
        first.getHitRect(NetRect);
        return BallRect.intersect(NetRect);
    }
    
    
    /**
     * Give movement to view when dragging
     *
     * @param v the view you want to give the drag to
     */
    public static void giveDragToView(View v) {
        giveDragToView(v, null);
    }
    
    /**
     * Give movement to view when dragging
     *
     * @param v      the view you want to give the drag to
     * @param action what to do on drag. use positiveButtonPressed() for when dragging. and negativeButtonPressed() for on touch down.
     */
    public static void giveDragToView(View v, CustomAction action) {
        v.setOnTouchListener(new View.OnTouchListener() {
            float dX;
            float dY;
            
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        
                        if (action != null) {
                            action.negativeButtonPressed();
                        }
                        
                        break;
                    
                    case MotionEvent.ACTION_MOVE:
                        view.setY(event.getRawY() + dY);
                        view.setX(event.getRawX() + dX);
                        
                        if (action != null) {
                            action.positiveButtonPressed();
                        }
                        
                        break;
                    
                    default:
                        return false;
                }
                return true;
            }
        });
    }
    
}

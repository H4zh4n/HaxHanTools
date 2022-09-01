package com.dev.hazhanjalal.haxhantools.utils.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;

import com.dev.hazhanjalal.haxhantools.utils.implementations.ClassImageAndDominantColor;

import java.util.ArrayList;

public class UtilsImage {
    
    public static int BOTTOM_CROP_PIXELS = 50;
    public static int SIMILARITY_QUADRANTS_PER_ROW_AND_COLUMN = 10;
    public static double SIMILARITY_PASS_THRESHOLD = 0.70;
    public static int SIMILARITY_COLOR_DIFFERENTIATOR = 10;
    
    public static int getDominantColor(Bitmap bitmap) {
        return getDominantColor(bitmap, false);
    }
    
    public static ArrayList<Integer> getRegionDominantColorForBitmap(Bitmap bitmap) {
        ArrayList<Integer> arQuadrantColors = new ArrayList<>();
        
        int eachWidthIncrement = bitmap.getWidth() / SIMILARITY_QUADRANTS_PER_ROW_AND_COLUMN;
        int eachHeightIncrement = bitmap.getHeight() / SIMILARITY_QUADRANTS_PER_ROW_AND_COLUMN;
        
        for (int x = 0; x < bitmap.getWidth() - eachWidthIncrement; x += eachWidthIncrement) {
            for (int y = 0; y < bitmap.getHeight() - eachHeightIncrement; y += eachHeightIncrement) {
                
                Bitmap croppedBitmap = Bitmap.createBitmap(bitmap,
                                                           x,
                                                           y,
                                                           eachWidthIncrement,
                                                           eachHeightIncrement);
                
                arQuadrantColors.add(Palette.from(croppedBitmap).generate().getDominantColor(0));
            }
        }
        
        return arQuadrantColors;
    }
    
    public static ArrayList<ClassImageAndDominantColor> getRegionValuesForBitmap(Bitmap bitmap) {
        ArrayList<ClassImageAndDominantColor> arQuadrantColors = new ArrayList<>();
        
        int eachWidthIncrement = bitmap.getWidth() / SIMILARITY_QUADRANTS_PER_ROW_AND_COLUMN;
        int eachHeightIncrement = bitmap.getHeight() / SIMILARITY_QUADRANTS_PER_ROW_AND_COLUMN;
        
        for (int x = 0; x < bitmap.getWidth() - eachWidthIncrement; x += eachWidthIncrement) {
            for (int y = 0; y < bitmap.getHeight() - eachHeightIncrement; y += eachHeightIncrement) {
                
                Bitmap croppedBitmap = Bitmap.createBitmap(bitmap,
                                                           x,
                                                           y,
                                                           eachWidthIncrement,
                                                           eachHeightIncrement);
                
                arQuadrantColors.add(new ClassImageAndDominantColor(croppedBitmap,
                                                                    getDominantColor(croppedBitmap)));
            }
        }
        
        return arQuadrantColors;
    }
    
    public static Bitmap cropBitmap(Bitmap bitmap, int xStart, int yStart, int width, int height) {
        return Bitmap.createBitmap(bitmap, xStart, yStart,
                                   width,
                                   height);
    }
    
    public static int getDominantColor(Bitmap bitmap, boolean bottomPixelsOnly) {
        if (bottomPixelsOnly && bitmap.getHeight() >= BOTTOM_CROP_PIXELS) {
            bitmap = bottomOfBitmap(bitmap);
        }
        
        return ColorUtils.setAlphaComponent(Palette.from(bitmap).generate().getDominantColor(0xffffff), 255);
    }
    
    public static int getDominantColorFromScale(Bitmap bitmap, boolean bottomPixelsOnly) {
        if (bottomPixelsOnly && bitmap.getHeight() >= BOTTOM_CROP_PIXELS) {
            bitmap = bottomOfBitmap(bitmap);
        }
        
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        
        if (newBitmap == null) {
            return 0;
        }
        
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }
    
    public static boolean areTwoBitmapsSimilar(Bitmap first, Bitmap second) {
        ArrayList<Integer> arFirst = getRegionDominantColorForBitmap(first);
        ArrayList<Integer> arSecond = getRegionDominantColorForBitmap(second);
        
        if (SIMILARITY_PASS_THRESHOLD <= 0 || SIMILARITY_PASS_THRESHOLD > 1) {
            SIMILARITY_PASS_THRESHOLD = 0.75f;
        }
        
        if (arFirst.size() < arSecond.size()) {
            ArrayList<Integer> tmp = arFirst;
            arFirst = arSecond;
            arSecond = tmp;
        }
        
        boolean areSimilar = false;
        
        int similar_count = 0;
        for (int i = 0; i < arFirst.size(); i++) {
            for (int j = -SIMILARITY_COLOR_DIFFERENTIATOR; j <= SIMILARITY_COLOR_DIFFERENTIATOR; j++) {
                
                if (arSecond.contains(arFirst.get(i) + j)) {
                    similar_count++;
                    break;
                }
            }
            
            /*if (arFirst.get(i) <= arSecond.get(i) + SIMILARITY_COLOR_PASS_THRESHOLD
                    && arFirst.get(i) >= arSecond.get(i) - SIMILARITY_COLOR_PASS_THRESHOLD) {
                
                similar_count++;
            }*/
            
            //if pass threshold is reached, return true and stop checking.
            if (similar_count >= arFirst.size() * SIMILARITY_PASS_THRESHOLD) {
                areSimilar = true;
                //v("reached similar threshold");
                break;
            }
            
            //if total correct needed is greater than total remaining to be checked.
            if (arFirst.size() * SIMILARITY_PASS_THRESHOLD - similar_count > arFirst.size() - i + 1) {
                //stop checking.
                areSimilar = false;
                //v("no point in further comparison");
                break;
            }
            
        }
        
        if (areSimilar || similar_count >= arFirst.size() * SIMILARITY_PASS_THRESHOLD) {
            
            //v("arFirst=" + arFirst.size());
            //v("arSecond=" + arSecond.size());
            
            //v("similar_count=" + similar_count);
            return true;
        } else {
            //stop checking.
            return false;
        }
    }
    
    public static Bitmap bottomOfBitmap(Bitmap bitmap) {
        return Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() - BOTTOM_CROP_PIXELS,
                                   bitmap.getWidth(), BOTTOM_CROP_PIXELS);
    }
    
    public static int getContrastColor(int color) {
        // Counting the perceptive luminance - human eye favors green color...
        double a = (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return a > 0.70 ? Color.BLACK : Color.WHITE;
    }
    
    public static int getInvertedColor(int color) {
        return color ^ 0x00ffffff;
    }
    
    @ColorInt
    public static int adjustAlpha(@ColorInt int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
    
}


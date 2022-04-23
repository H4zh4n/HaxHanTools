package com.dev.hazhanjalal.haxhantools.utils.print;

import android.util.Log;

import com.dev.hazhanjalal.haxhantools.BuildConfig;

public class Logger {
    //only set as true for debugging.
    public static boolean LOGGING_ENABLED = BuildConfig.DEBUG ? true : false;
    private static int STACK_TRACE_LEVELS_UP = 6;
    
    public static void v() {
        STACK_TRACE_LEVELS_UP = 8;
        v("HAX", "", true);
    }
    
    public static void v(String message) {
        STACK_TRACE_LEVELS_UP = 7;
        v("HAX", message, true);
    }
    
    public static void e(Exception e) {
        Log.e("HAX", "", e);
    }
    
    public static void v(String tag, String message) {
        v(tag, message, true);
    }
    
    public static void v(String tag, String message, boolean isReferenceCall) {
        
        if (isReferenceCall) {
            STACK_TRACE_LEVELS_UP = 6;
        }
        
        if (LOGGING_ENABLED) {
            
            if (message.isEmpty()) {
                message = getMethodName();
            }
            
            Log.v(tag, getClassNameMethodNameAndLineNumber() + message);
        }
    }
    
    /**
     * Get the current line number. Note, this will only work as called from
     * this class as it has to go a predetermined number of steps up the stack
     * trace. In this case 5.
     *
     * @return int - Current line number.
     * @author kvarela
     */
    private static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getLineNumber();
    }
    
    /**
     * Get the current class name. Note, this will only work as called from this
     * class as it has to go a predetermined number of steps up the stack trace.
     * In this case 5.
     *
     * @return String - Current line number.
     * @author kvarela
     */
    private static String getClassName() {
        try {
            String fileName = Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getFileName();
            
            // kvarela: Removing ".java" and returning class name
            return fileName.substring(0, fileName.length() - 5);
        } catch (Exception e) {
            Log.e("HAX", "", e);
            return "";
        }
    }
    
    /**
     * Get the current method name. Note, this will only work as called from
     * this class as it has to go a predetermined number of steps up the stack
     * trace. In this case 5.
     *
     * @return String - Current line number.
     * @author kvarela
     */
    private static String getMethodName() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getMethodName();
    }
    
    /**
     * Returns the class name, method name, and line number from the currently
     * executing log call in the form .()-
     *
     * @return String - String representing class name, method name, and line
     * number.
     * @author kvarela
     */
    private static String getClassNameMethodNameAndLineNumber() {
        return "(" + getClassName() + ".java" + ":" + getLineNumber() + ")\t: ";
    }
}
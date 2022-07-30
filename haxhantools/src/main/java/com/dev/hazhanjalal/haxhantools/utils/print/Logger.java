package com.dev.hazhanjalal.haxhantools.utils.print;

import android.content.Context;
import android.util.Log;

public class Logger {
    //only set as true for debugging.
    //disabled for library, in normal project use :
    //LOGGING_ENABLED = BuildConfig.DEBUG ? true :
    public static boolean LOGGING_ENABLED = false;
    private static int STACK_TRACE_LEVELS_UP = 6;
    
    
    /**
     * Call this method once, preferably in very first activity.
     */
    public static void setEnabled(Context context) {
        LOGGING_ENABLED = (0 != (context.getApplicationInfo().flags & context.getApplicationInfo().FLAG_DEBUGGABLE));
    }
    
    /**
     * It's best best to send [BuildConfig.DEBUG] to this method.
     */
    public static void setEnabled(boolean isEnabled) {
        LOGGING_ENABLED = isEnabled;
    }
    
    /**
     * Make sure Filter is -HAX-
     */
    public static void v() {
        STACK_TRACE_LEVELS_UP = 8;
        v("-HAX-", "", true);
    }
    
    /**
     * Make sure Filter is -HAX-
     * Make sure you set [LOGGING_ENABLED] to [BuildConfig.DEBUG] somewhere in your project (preferably first activity).
     */
    public static void v(String message) {
        STACK_TRACE_LEVELS_UP = 7;
        v("-HAX-", message, true);
    }
    
    /**
     * Make sure Filter is -HAX-
     */
    public static void e(Exception e) {
        Log.e("-HAX-", "", e);
    }
    
    /**
     * Make sure Filter is -HAX-
     */
    public static void v(String tag, String message) {
        v(tag, message, true);
    }
    
    /**
     * Make sure Filter is -HAX-
     */
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
            Log.e("-HAX-", "", e);
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
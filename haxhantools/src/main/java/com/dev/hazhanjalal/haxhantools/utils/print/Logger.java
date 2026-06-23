package com.dev.hazhanjalal.haxhantools.utils.print;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

public class Logger {
    // Disabled by default for library builds.
    // In your app, call setEnabled(BuildConfig.DEBUG) or setEnabled(context).
    public static boolean LOGGING_ENABLED = false;

    private static final String DEFAULT_TAG = "-HAX-";
    private static String sCustomTag = null;

    /**
     * Override the default tag ("-HAX-") used by all logging methods.
     * Set to null to reset to the default.
     */
    public static void setTag(String customTag) {
        sCustomTag = (customTag != null && !customTag.isEmpty()) ? customTag : null;
    }

    /**
     * Returns the currently active tag.
     */
    private static String resolveTag(String providedTag) {
        if (providedTag != null && !providedTag.isEmpty()) {
            return providedTag;
        }
        return sCustomTag != null ? sCustomTag : DEFAULT_TAG;
    }

    /**
     * Call once, preferably in your first Activity.
     * Auto-detects whether the app is debuggable.
     */
    public static void setEnabled(Context context) {
        LOGGING_ENABLED = (0 != (context.getApplicationInfo().flags
                & ApplicationInfo.FLAG_DEBUGGABLE));
    }

    /**
     * Manually enable/disable logging.
     * Typical usage: {@code Logger.setEnabled(BuildConfig.DEBUG);}
     */
    public static void setEnabled(boolean isEnabled) {
        LOGGING_ENABLED = isEnabled;
    }

    // ─── Verbose ────────────────────────────────────

    public static void v() {
        log(Log.VERBOSE, DEFAULT_TAG, null, null);
    }

    public static void v(String message) {
        log(Log.VERBOSE, DEFAULT_TAG, message, null);
    }

    public static void v(String tag, String message) {
        log(Log.VERBOSE, tag, message, null);
    }

    public static void v(String tag, String message, Throwable tr) {
        log(Log.VERBOSE, tag, message, tr);
    }

    // ─── Debug ──────────────────────────────────────

    public static void d(String message) {
        log(Log.DEBUG, DEFAULT_TAG, message, null);
    }

    public static void d(String tag, String message) {
        log(Log.DEBUG, tag, message, null);
    }

    public static void d(String tag, String message, Throwable tr) {
        log(Log.DEBUG, tag, message, tr);
    }

    // ─── Info ───────────────────────────────────────

    public static void i(String message) {
        log(Log.INFO, DEFAULT_TAG, message, null);
    }

    public static void i(String tag, String message) {
        log(Log.INFO, tag, message, null);
    }

    public static void i(String tag, String message, Throwable tr) {
        log(Log.INFO, tag, message, tr);
    }

    // ─── Warning ────────────────────────────────────

    public static void w(String message) {
        log(Log.WARN, DEFAULT_TAG, message, null);
    }

    public static void w(String tag, String message) {
        log(Log.WARN, tag, message, null);
    }

    public static void w(String tag, String message, Throwable tr) {
        log(Log.WARN, tag, message, tr);
    }

    // ─── Error ──────────────────────────────────────

    public static void e(Throwable tr) {
        log(Log.ERROR, DEFAULT_TAG, null, tr);
    }

    public static void e(String message, Throwable tr) {
        log(Log.ERROR, DEFAULT_TAG, message, tr);
    }

    public static void e(String message) {
        log(Log.ERROR, DEFAULT_TAG, message, null);
    }

    public static void e(String tag, String message) {
        log(Log.ERROR, tag, message, null);
    }

    public static void e(String tag, String message, Throwable tr) {
        log(Log.ERROR, tag, message, tr);
    }

    // ─── Core ───────────────────────────────────────

    /**
     * Formats and outputs the log entry with caller location info.
     * <p>
     * The prefix pattern {@code (FileName:LineNumber)} is what Android Studio
     * recognizes to make the output clickable in the Logcat window.
     */
    private static void log(int priority, String tag, String message, Throwable tr) {
        if (!LOGGING_ENABLED) {
            return;
        }

        StackTraceElement element = getCallerElement();
        if (element == null) {
            return;
        }

        String resolvedTag = resolveTag(tag);
        String finalMessage = (message == null || message.isEmpty())
                ? element.getMethodName() : message;

        // (FileName:LineNumber) makes the entry clickable in Android Studio Logcat.
        String output = "(" + element.getFileName() + ":" + element.getLineNumber() + ")\t: "
                + finalMessage;

        if (tr != null) {
            if (priority == Log.ERROR || priority == Log.ASSERT) {
                // Native Throwable support only on ERROR / ASSERT.
                Log.println(priority, resolvedTag, output);
                Log.println(priority, resolvedTag, Log.getStackTraceString(tr));
            } else {
                // Append stack trace for non-error levels.
                output += "\n" + Log.getStackTraceString(tr);
                Log.println(priority, resolvedTag, output);
            }
        } else {
            Log.println(priority, resolvedTag, output);
        }
    }

    /**
     * Walks the stack trace to find the first frame outside this Logger class.
     */
    private static StackTraceElement getCallerElement() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        boolean foundLogger = false;
        for (StackTraceElement element : stackTrace) {
            String className = element.getClassName();
            if (className.equals(Logger.class.getName())) {
                foundLogger = true;
                continue;
            }
            if (foundLogger) {
                return element;
            }
        }
        return null;
    }
}
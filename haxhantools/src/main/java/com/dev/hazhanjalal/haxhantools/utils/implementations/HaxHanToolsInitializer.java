package com.dev.hazhanjalal.haxhantools.utils.implementations;

import android.app.Activity;
import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dev.hazhanjalal.haxhantools.utils.utils.Utils;

/**
 * Auto-initializer that registers ActivityLifecycleCallbacks so that
 * Utils.activeContext is always updated to the latest active activity.
 * <p>
 * No changes to the host app's Application class are needed.
 * This ContentProvider runs at app startup automatically.
 */
public class HaxHanToolsInitializer extends ContentProvider {

    @Override
    public boolean onCreate() {
        if (getContext() == null) return false;

        Application app = (Application) getContext().getApplicationContext();

        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                Utils.activeContext = activity;
                Utils.registerPrefs(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                Utils.activeContext = activity;

                View root = activity.findViewById(android.R.id.content);
                if (root != null) {
                    ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
                        androidx.core.graphics.Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                        return WindowInsetsCompat.CONSUMED;
                    });
                    ViewCompat.requestApplyInsets(root);
                }
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                Utils.activeContext = activity;
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                if (Utils.activeContext == activity) {
                    // Last visible activity was destroyed — keep reference to avoid
                    // ClassCastException in Utils.getActivity(). The host app is likely
                    // shutting down anyway.
                }
            }
        });

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
# Setup & Initialization

HaxHanTools requires a small amount of initialization to function correctly. The library tracks the currently active Activity so that UI components (dialogs, toasts) can be displayed on the right screen.

## Recommended: Auto-Initialization (v1.0.15+)

Add the `HaxHanToolsInitializer` provider to your `AndroidManifest.xml`:

```xml
<application ...>

    <provider
        android:name="com.dev.hazhanjalal.haxhantools.utils.implementations.HaxHanToolsInitializer"
        android:authorities="${applicationId}.haxhantools-initializer"
        android:exported="false" />

</application>
```

This `ContentProvider` runs automatically at app startup and:
- Registers `ActivityLifecycleCallbacks` to keep `Utils.activeContext` updated
- Calls `Utils.registerPrefs(activity)` to initialize SharedPreferences
- Applies edge-to-edge window insets automatically

**No code changes** are needed in your `Application` or Activity classes.

---

## Manual Initialization (legacy)

If you prefer explicit control, skip the manifest provider and do the following:

### Step 1: Initialize Utils

In your first Activity's `onCreate()`:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    new Utils(this); // Initializes SharedPreferences

    // ...
}
```

### Step 2: Update activeContext

In **every** Activity's `onResume()`:

```java
@Override
protected void onResume() {
    super.onResume();

    Utils.activeContext = this;

    // ...
}
```

This ensures dialogs and toasts appear on the correct screen.

---

## Logger Setup

To enable debug logging (recommended for debug builds only):

```java
// In your first Activity's onCreate():
Logger.setEnabled(this);        // Auto-detects FLAG_DEBUGGABLE
// or
Logger.setEnabled(BuildConfig.DEBUG);
```

See [Logger](logger.md) for full details.

---

## Gradle Setup

### `settings.gradle`

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### `app/build.gradle`

[![](https://jitpack.io/v/H4zh4n/HaxHanTools.svg)](https://jitpack.io/#H4zh4n/HaxHanTools)

```gradle
dependencies {
    implementation 'com.github.H4zh4n:HaxHanTools:1.1.0'
}
```

> 👆 Click the badge to see the latest available version on JitPack.

---

## ProGuard / R8

No special rules are needed. The library works with R8 full mode out of the box.

---

## Minimum Requirements

| Requirement | Version |
|---|---|
| minSdk | 21 |
| compileSdk | 34+ |
| Java | 8+ |
# HxProgress

A non-cancelable progress/loading dialog with a customizable title, message, icon, and Sprite animation.

## Quick Start

```java
// Show with title only (message defaults to "Please wait")
HxProgress.showProgressDialog("Loading");

// Show with title + message
HxProgress.showProgressDialog("Loading", "Fetching data...");

// ... do your work ...

// Close when done
HxProgress.closeProgressDialog();
```

---

## Update Message While Showing

You can update the message text at any time — useful for showing download progress or step-by-step status:

```java
HxProgress.showProgressDialog("Downloading", "0% complete");

// Later, from a callback or background thread:
HxProgress.setProgressText("45% complete");
HxProgress.setProgressText("100% complete");
```

Both `setProgressText` and `closeProgressDialog` are thread-safe — they post to the UI thread internally.

---

## Custom Icon

```java
Drawable myIcon = ContextCompat.getDrawable(context, R.drawable.ic_sync);

HxProgress.showProgressDialog(
    context,
    "Syncing",
    "Please wait...",
    myIcon,
    new CustomAction() {
        @Override
        public void positiveButtonPressed() {
            // Called when dialog is dismissed
            Logger.d("Progress dialog dismissed");
        }
    }
);
```

---

## Dismiss Callback

Attach a `CustomAction` to be notified when the dialog is dismissed:

```java
HxProgress.showProgressDialog(
    "Processing",
    "This may take a moment...",
    () -> HxToast.showToast("Done processing!")
);
```

---

## API Reference

| Method | Description |
|---|---|
| `showProgressDialog(String title)` | Show with title only. Message defaults to `"Please wait"` |
| `showProgressDialog(String title, String message)` | Show with title and custom message |
| `showProgressDialog(Context, String title, String message)` | Show with explicit context, title, and message |
| `showProgressDialog(String title, String message, CustomAction onDismiss)` | Show and attach a callback that fires on dismiss |
| `showProgressDialog(Context, String title, String message, CustomAction onDismiss)` | Full control: explicit context, title, message, callback |
| `showProgressDialog(Context, String title, String message, Drawable icon, CustomAction onDismiss)` | Full control with custom icon drawable |
| `setProgressText(String message)` | Updates the message text of the currently visible dialog |
| `closeProgressDialog()` | Closes the dialog. Safe to call from any thread |
| `closeProgressDialog(Context context)` | Closes with explicit context |

---

## Notes

- Only **one** progress dialog can be shown at a time — calling `showProgressDialog` while one is already visible is a no-op
- The dialog is **non-cancelable** — the user cannot dismiss it by tapping outside or pressing back
- Uses [SpinKit](https://github.com/ybq/Android-SpinKit) for loading animations
- Thread-safe — `setProgressText`, `closeProgressDialog`, and `showProgressDialog` all post to the UI thread internally
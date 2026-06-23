# HxProgress

A non-cancelable progress/loading dialog with a customizable title, message, and Sprite animation.

## Quick Start

```java
// Show with title only
HxProgress.showProgressDialog("Loading");

// Show with title + message
HxProgress.showProgressDialog("Loading", "Fetching data...");

// ... do work ...

// Close it when done
HxProgress.closeProgressDialog();
```

## Update Message While Showing

```java
HxProgress.showProgressDialog("Downloading", "0% complete");

// Later, update the message
HxProgress.setProgressText("50% complete");
```

---

## API Reference

### `showProgressDialog(String title)`
Show with a title only. Message defaults to "Please wait".

### `showProgressDialog(String title, String message)`
Show with both title and custom message.

### `showProgressDialog(Context context, String title, String message)`
Show with explicit context, title, and message.

### `showProgressDialog(String title, String message, CustomAction onDismiss)`
Show and attach a callback that fires when the dialog is dismissed.

### `showProgressDialog(Context context, String title, String message, CustomAction onDismiss)`
Full control: explicit context, title, message, and dismiss callback.

### `showProgressDialog(Context ctx, String title, String message, Drawable icon, CustomAction onDismiss)`
Full control with custom icon drawable.

### `setProgressText(String message)`
Updates the message text of the currently visible dialog. Safe to call from any thread.

### `closeProgressDialog()`
Closes the dialog. Safe to call from any thread.

### `closeProgressDialog(Context context)`
Closes with explicit context.

---

## Notes

- Only **one** progress dialog can be shown at a time — calling `showProgressDialog` while one is already visible is a no-op.
- The dialog is **non-cancelable** — the user cannot dismiss it by tapping outside or pressing back.
- Uses [SpinKit](https://github.com/ybq/Android-SpinKit) for loading animations.
- Thread-safe — `setProgressText`, `closeProgressDialog`, and `showProgressDialog` all post to the UI thread internally.
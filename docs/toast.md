# HxToast

A customizable Toast replacement with color-coded presets for info, success, warning, and error messages.

## Quick Start

```java
// Default (blue info toast)
HxToast.showToast("Item saved");

// Success (green)
HxToast.showToastSuccess("Upload complete");

// Warning (yellow)
HxToast.showToastWarning("Low battery");

// Error (red)
HxToast.showToastError("Network failed");
```

---

## Custom Toast

Full control over icon, background color, and context:

```java
HxToast.showToast(
    context,
    "Custom message",
    ContextCompat.getDrawable(context, R.drawable.my_icon),
    Color.parseColor("#FF5722")
);
```

---

## Controlling Overlap

By default, calling `showToast` cancels any previous visible toast so messages don't stack.

```java
// Disable auto-cancel (allow overlapping toasts)
HxToast.setCancelPreviousVisibleToast(false);

// Re-enable (default)
HxToast.setCancelPreviousVisibleToast(true);
```

---

## API Reference

### `showToast(String text)`
Display an info toast (blue background, info icon). Uses `Utils.activeContext`.

### `showToast(Context ctx, String text)`
Info toast with explicit context.

### `showToast(String text, int backColor)`
Toast with custom background color. Uses `Utils.activeContext`.

### `showToast(Context ctx, String text, int backColor)`
Toast with custom context and background color.

### `showToast(Context ctx, String text, Drawable icon, int backColor)`
Fully custom toast: context, text, icon drawable, and background color.

### `showToastSuccess(String text)` / `showToastSuccess(Context ctx, String text)`
Green toast with checkmark icon.

### `showToastWarning(String text)` / `showToastWarning(Context ctx, String text)`
Yellow toast with warning icon.

### `showToastError(String text)` / `showToastError(Context ctx, String text)`
Red toast with warning icon.

### `setCancelPreviousVisibleToast(boolean)`
When `true` (default), a new toast cancels the previous one.

### `isCancelPreviousVisibleToast()`
Returns the current overlap behavior.

---

## Visual Presets

| Method | Icon | Background |
|---|---|---|
| `showToast` | `ic_info_outline` | `colorBlueChosen` |
| `showToastSuccess` | `ic_check` | `colorGreenChosen` |
| `showToastWarning` | `ic_warning` | `colorYellowChosen` |
| `showToastError` | `ic_warning` | `colorRedChosen` |
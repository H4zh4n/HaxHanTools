# HxToast

A customizable Toast replacement with color-coded presets for info, success, warning, and error messages.

## Quick Start

```java
// Blue info toast
HxToast.showToast("Item saved");

// Green success toast
HxToast.showToastSuccess("Upload complete");

// Yellow warning toast
HxToast.showToastWarning("Low battery");

// Red error toast
HxToast.showToastError("Network failed");
```

Each preset uses `Utils.activeContext` — no need to pass a context.

---

## Custom Toast

For full control over icon, background color, and context:

```java
HxToast.showToast(
    context,
    "Custom message",
    ContextCompat.getDrawable(context, R.drawable.my_icon),
    Color.parseColor("#FF5722")
);
```

You can also omit the icon and just customize the background:

```java
HxToast.showToast("Hello", Color.parseColor("#333333"));
HxToast.showToast(context, "Hello", Color.parseColor("#333333"));
```

---

## Controlling Overlap

By default, calling `showToast` cancels any previous visible toast so messages don't stack. You can change this behavior:

```java
// Allow overlapping toasts
HxToast.setCancelPreviousVisibleToast(false);

// Check current setting
boolean cancels = HxToast.isCancelPreviousVisibleToast();

// Re-enable (default)
HxToast.setCancelPreviousVisibleToast(true);
```

---

## API Reference

| Method | Description |
|---|---|
| `showToast(String text)` | Info toast (blue, info icon). Uses `Utils.activeContext` |
| `showToast(Context, String text)` | Info toast with explicit context |
| `showToast(String text, int backColor)` | Toast with custom background color |
| `showToast(Context, String text, int backColor)` | Custom background with explicit context |
| `showToast(Context, String text, Drawable icon, int backColor)` | Fully custom: context, text, icon, background |
| `showToastSuccess(String text)` | Green toast with checkmark icon |
| `showToastSuccess(Context, String text)` | Green toast with explicit context |
| `showToastWarning(String text)` | Yellow toast with warning icon |
| `showToastWarning(Context, String text)` | Yellow toast with explicit context |
| `showToastError(String text)` | Red toast with warning icon |
| `showToastError(Context, String text)` | Red toast with explicit context |
| `setCancelPreviousVisibleToast(boolean)` | `true` (default) cancels previous toast before showing new one |
| `isCancelPreviousVisibleToast()` | Returns current overlap behavior |

---

## Visual Presets

| Method | Icon | Background |
|---|---|---|
| `showToast` | `ic_info_outline` | `colorBlueChosen` |
| `showToastSuccess` | `ic_check` | `colorGreenChosen` |
| `showToastWarning` | `ic_warning` | `colorYellowChosen` |
| `showToastError` | `ic_warning` | `colorRedChosen` |

---

## Notes

- The toast appears at the bottom center of the screen with a 200px bottom margin
- Duration is always `Toast.LENGTH_LONG`
- All show methods are thread-safe — they post to the UI thread internally
# HxPopup

Display a full-screen popup dialog with an image (loaded from URL via Picasso), optional HTML text, and an optional visit link.

## Quick Start

```java
// Simple image popup
HxPopup.showCustomPopup("https://example.com/image.png");

// Image + text
ClassPopup pop = new ClassPopup();
pop.popupImageURL = "https://example.com/image.png";
pop.popupText = "This is a description";
HxPopup.showCustomPopup(pop);
```

---

## ClassPopup Properties

Use the `ClassPopup` object to configure the dialog:

| Property | Type | Default | Description |
|---|---|---|---|
| `popupImageURL` | String | `""` | Image URL to load via Picasso |
| `popupText` | String | `""` | Text body (supports HTML if wrapped in `<>`) |
| `popupVisitURL` | String | `""` | URL to open when text or image is tapped |
| `popupCanTapImage` | boolean | `false` | Allow tapping the image to open `popupVisitURL` |
| `popupTextIsAyah` | boolean | `false` | Apply Quranic typeface to text |
| `colorBackground` | int | `colorDialog` | Background color of the popup |
| `colorText` | int | `white` | Text color |

---

## API Reference

### `showCustomPopup(String imageURL)`
Show a popup with just an image URL. Text defaults to empty.

### `showCustomPopup(ClassPopup pop)`
Show a fully configured popup. Uses `Utils.activeContext`.

### `showCustomPopup(Context context, ClassPopup pop)`
Show with explicit context.

---

## HTML Text Support

If the `popupText` string contains both `<` and `>`, it is rendered as HTML:

```java
ClassPopup pop = new ClassPopup();
pop.popupText = "<b>Bold title</b><br/><i>Italic subtitle</i>";
HxPopup.showCustomPopup(pop);
```

---

## Visit Link Behavior

When `popupVisitURL` is set and non-empty:
- The text becomes clickable with a link icon
- If `popupCanTapImage` is `true`, tapping the image also opens the URL
- Links open via `Utils.openURL()`

```java
ClassPopup pop = new ClassPopup();
pop.popupImageURL = "https://example.com/screenshot.png";
pop.popupText = "Tap here to learn more";
pop.popupVisitURL = "https://example.com/details";
pop.popupCanTapImage = true;
HxPopup.showCustomPopup(pop);
```

---

## Notes

- Images are loaded via [Picasso](https://github.com/square/picasso). Make sure your app has internet permission.
- The dialog uses a transparent window background and a close button in the top corner.
- Cached images may not update immediately — Picasso caching behavior applies.
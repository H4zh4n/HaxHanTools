# HxPopup

Display a full-screen popup dialog with an image (loaded from URL via Picasso), optional HTML text, and an optional clickable visit link.

## Quick Start

```java
// Simple image-only popup
HxPopup.showCustomPopup("https://example.com/banner.png");

// Image + description text
ClassPopup pop = new ClassPopup();
pop.popupImageURL = "https://example.com/promo.png";
pop.popupText = "Check out our new feature!";
HxPopup.showCustomPopup(pop);
```

---

## Image + Text + Visit Link

The most common pattern: show an image, some descriptive text, and link to an external page:

```java
ClassPopup pop = new ClassPopup();
pop.popupImageURL = "https://example.com/screenshot.png";
pop.popupText = "Tap here to visit the website";
pop.popupVisitURL = "https://example.com/details";
pop.popupCanTapImage = true;   // tapping the image also opens the URL
HxPopup.showCustomPopup(pop);
```

When `popupVisitURL` is set:
- The text gets a **link icon** and becomes clickable
- If `popupCanTapImage` is `true`, the image also opens the URL
- Links open via `Utils.openURL()` in the browser

---

## HTML Text

If the text contains both `<` and `>`, it is rendered as HTML:

```java
ClassPopup pop = new ClassPopup();
pop.popupText = "<b>New Update!</b><br/><i>Version 2.0 is here</i>";
HxPopup.showCustomPopup(pop);
```

---

## Custom Colors

```java
ClassPopup pop = new ClassPopup();
pop.popupImageURL = "https://example.com/image.png";
pop.popupText = "Welcome!";
pop.colorBackground = Color.parseColor("#1A1A2E");   // dark background
pop.colorText = Color.parseColor("#FFD700");           // gold text
HxPopup.showCustomPopup(pop);
```

---

## With Explicit Context

If you need to control which Activity hosts the dialog:

```java
HxPopup.showCustomPopup(specificActivity, pop);
```

---

## ClassPopup Properties

Use the `ClassPopup` object to configure the dialog:

| Property | Type | Default | Description |
|---|---|---|---|
| `popupImageURL` | `String` | `""` | Image URL loaded via Picasso |
| `popupText` | `String` | `""` | Text body — supports HTML if wrapped in `<>` |
| `popupVisitURL` | `String` | `""` | URL to open when text or image is tapped |
| `popupCanTapImage` | `boolean` | `false` | Allow tapping the image to open `popupVisitURL` |
| `colorBackground` | `int` | `colorDialog` | Background color of the popup |
| `colorText` | `int` | `white` | Text color |
| `popupTextIsAyah` | `boolean` | `false` | Apply Quranic typeface to text |

---

## API Reference

| Method | Description |
|---|---|
| `showCustomPopup(String imageURL)` | Show a popup with just an image URL (no text) |
| `showCustomPopup(ClassPopup pop)` | Show a fully configured popup. Uses `Utils.activeContext` |
| `showCustomPopup(Context, ClassPopup pop)` | Show with explicit context |

---

## Notes

- Images are loaded via [Picasso](https://github.com/square/picasso) — make sure your app has the Internet permission
- The dialog uses a transparent window background with a close (✕) button in the top corner
- Picasso caching applies — previously loaded images may not refresh immediately on subsequent shows
- The `ClassPopup` object has a `popupTime` field (set automatically, defaults to `0`) for timestamp tracking
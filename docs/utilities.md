# Utilities

The library provides three utility classes: `Utils` (general-purpose), `UtilsFile` (file operations), and `UtilsImage` (image analysis).

---

## Utils

General-purpose utilities covering SharedPreferences, resources, screen metrics, network checks, and more.

### SharedPreferences

```java
// Store values (type determined by Object class)
Utils.put("user_name", "Ahmed");
Utils.put("score", 100);
Utils.put("is_logged_in", true);

// Retrieve values
String name = Utils.getStringPref("user_name", "default");
int score = Utils.getIntegerPref("score", 0);
boolean loggedIn = Utils.getBooleanPref("is_logged_in", false);
float ratio = Utils.getFloatPref("ratio", 1.0f);
long timestamp = Utils.getLongPref("timestamp", 0L);

// Check / remove
boolean exists = Utils.prefsContains("user_name");
Utils.removeKeyPrefs("user_name");
```

### Resource Access

```java
// Strings & colors (uses activeContext)
String str = Utils.getString(R.string.app_name);
int color = Utils.getColor(R.color.colorBlueChosen);
Drawable drawable = Utils.getDrawable(R.drawable.ic_info);

// Look up by name at runtime
int resId = Utils.getResourceIdByName("raw", "my_file");
int drawableId = Utils.getDrawableByName("ic_launcher");

// Convert color int to hex string
String hex = Utils.colorIntToHex(Color.RED);  // → "#FF0000"
```

### Screen & Display

```java
int height = Utils.getScreenHeight();
int width = Utils.getScreenWidth();
int dp = Utils.toDP(48);                // px → dp
int px = Utils.dpToPx(16);              // dp → px
boolean isNight = Utils.isNightEnabled();
boolean isRTL = Utils.isRightToLeft();
```

### Network

```java
boolean online = Utils.isNetworkAvailable();
// Or with a callback that runs once connectivity is confirmed:
Utils.isNetworkAvailable("https://example.com");
```

### Activity & App

```java
Activity current = Utils.getActivity();

// Restart the current activity
Utils.restartThis();

// Open URL in browser
Utils.openURL("https://example.com");

// App version
String verName = Utils.getVerName();
long buildVer = Utils.getBuildVer();
```

### Keyboard & Vibration

```java
// Vibrate
Utils.vibrate(500);       // 500ms
Utils.vibratePattern(/*...*/);
Utils.cancelVibrate();

// Keyboard
Utils.showKeyboard(view);
Utils.hideKeyboard();
```

### Bitmap & Drawable Helpers

```java
Bitmap bmp = Utils.drawableToBitmap(drawable);
Uri uri = Utils.getImageUri(context, bitmap);
Uri uri2 = Utils.getURIFromBitmap(bitmap);
byte[] blob = Utils.getBlobFromBitmap(bitmap);
Bitmap fromBlob = Utils.getBitmapFromBlob(blob);
int byteSize = Utils.getByteSizeOfBitmap(bitmap);
Bitmap resized = Utils.resizeBitmap(bitmap, 0.5, 0.5);
```

### View Utilities

```java
// Tint
Utils.setBackgroundTint(Color.RED, view1, view2);
Utils.setBackgroundTintForce(Color.BLUE, view);
Drawable tinted = Utils.setDrawableTint(drawable, Color.WHITE);
Utils.setStrokeColor(view, Color.RED);
Utils.setStrokeColor(view, Color.RED, 4); // with width

// Inflate
View v = Utils.inflateView(R.layout.my_layout);

// Execute when view is visible
Utils.whenViewIsVisible(myView, () -> {
    // Do something
});

// Delayed execution
Utils.doLater(1000, () -> {
    // Runs after 1 second
});
```

### Clipboard

```java
Utils.copyToClip("Copied text");
Utils.copyToClip("Copied text", true); // show toast
```

### Status Bar & Action Bar

```java
Utils.setStatusBarColor(Color.BLUE);
Utils.setActionBarColor(actionBar, Color.WHITE, Color.BLACK);
Utils.setBackButtonColor(actionBar, Color.WHITE);
```

### Misc

```java
// Keep screen on
Utils.keepScreen();

// Darken a color
int darker = Utils.getDarker(Color.RED, 0.8f);

// Random sprite for progress dialogs
Sprite sprite = Utils.randomSprite();

// Load text from assets
String json = Utils.loadDataFromAsset("data.json");

// Load text from URL
Utils.loadDataFromURL("https://api.example.com", result -> {
    // result contains the response string
});

// Format time (Kurdish locale)
String time = Utils.formatSecondsToTimeKurdish(3661);  // → "1:01:01"

// Replace Eastern/Arabic numerals
String normalized = Utils.replaceEasternNumbers("١٢٣");  // → "123"

// Permission helpers
boolean granted = Utils.isPermissionGranted(Manifest.permission.CAMERA);
Utils.showRequestPermission(Manifest.permission.CAMERA);

// Memory info
long total = Utils.getRamTotal();
long available = Utils.getRamAvailable();
String size = Utils.getMemorySizeHumanized();
```

---

## UtilsFile

File and folder operations within the app's external files directory.

```java
// Check existence
boolean exists = UtilsFile.fileExist("downloads", "data.json");
boolean folderExists = UtilsFile.folderExists("downloads");

// Check if file exists in res/raw
boolean inRaw = UtilsFile.fileExistsInRaw("my_config");

// Delete files
UtilsFile.deleteFile("downloads", "old_data.json");
UtilsFile.deleteFolder("downloads", "temp");

// Get paths
File file = UtilsFile.getFileFullPath("downloads", "data.json");
File folder = UtilsFile.getFolderFullPath("downloads");
```

All paths resolve under `context.getExternalFilesDir(null)` by default.

---

## UtilsImage

Bitmap analysis and comparison utilities.

### Dominant Color

```java
// Get the dominant color of a bitmap
int color = UtilsImage.getDominantColor(bitmap);

// Get dominant colors for each region/quadrant of a bitmap
ArrayList<Integer> regionColors = UtilsImage.getRegionDominantColorForBitmap(bitmap);

// Get detailed region data (color + position)
ArrayList<ClassImageAndDominantColor> regions = UtilsImage.getRegionValuesForBitmap(bitmap);
```

### Image Similarity

```java
// Compare two bitmaps by their region colors
boolean similar = UtilsImage.isBitmapSimilarTo(bitmap1, bitmap2);
```

### Configurable Thresholds

```java
UtilsImage.BOTTOM_CROP_PIXELS = 50;                      // Pixels to crop from bottom
UtilsImage.SIMILARITY_QUADRANTS_PER_ROW_AND_COLUMN = 10;  // Grid resolution
UtilsImage.SIMILARITY_PASS_THRESHOLD = 0.70;              // Similarity threshold
UtilsImage.SIMILARITY_COLOR_DIFFERENTIATOR = 10;          // Color difference tolerance
```

---

## Notes

- Most `Utils` methods that need context use `Utils.activeContext` automatically
- `Utils.prefs` is initialized automatically via `HaxHanToolsInitializer` or manually via `new Utils(this)`
- `UtilsImage` uses AndroidX Palette under the hood for dominant color extraction
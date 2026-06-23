<img src="https://i.imgur.com/aHGpgQV.png"/>

[![](https://jitpack.io/v/H4zh4n/HaxHanTools.svg)](https://jitpack.io/#H4zh4n/HaxHanTools)

HaxHanTools is an Android utility library that provides ready-to-use UI components, networking
helpers, and general-purpose utilities to speed up Android development.

---

## Documentation

| Feature                                      | Description                                                         |
|----------------------------------------------|---------------------------------------------------------------------|
| [Setup & Initialization](docs/setup.md)      | Library setup, `HaxHanToolsInitializer`, `Utils` configuration      |
| [Logger](docs/logger.md)                     | Debug logging with clickable Logcat links and auto caller detection |
| [Toast](docs/toast.md)                       | Custom styled toast messages (info, success, warning, error)        |
| [Progress Dialog](docs/progress.md)          | Customizable progress/loading dialogs                               |
| [Popup Dialog](docs/popup.md)                | Image & HTML content popup dialogs                                  |
| [List Dialog](docs/list_of_items.md)         | Searchable list selection dialog with rich customization            |
| [Downloader](docs/downloader.md)             | File download manager with progress & notifications                 |
| [Imgur Upload](docs/imgur_upload.md)         | Upload images to Imgur                                              |
| [Hash & Encryption](docs/hash_encryption.md) | SHA-1/256/512, MD5 hashing & Caesar cipher                          |
| [Utilities](docs/utilities.md)               | SharedPreferences, file operations, image analysis                  |
| [AutoComplete](docs/autocomplete.md)         | AutoCompleteTextView with inline add/remove chips                   |
| [Known Issues](docs/known_issues.md)         | Common build problems and their solutions                           |

## Component Overview

| Component                   | Type      | Key Features                                                              |
|-----------------------------|-----------|---------------------------------------------------------------------------|
| `HaxHanToolsInitializer`    | Auto-init | ContentProvider-based; auto-tracks active Activity & applies edge-to-edge |
| `Utils`                     | Utility   | SharedPreferences, resources, colors, screen metrics, vibration, keyboard |
| `UtilsFile`                 | Utility   | File existence, deletion, raw resource checks                             |
| `UtilsImage`                | Utility   | Dominant color extraction, image similarity, region analysis              |
| `Logger`                    | Debug     | Clickable Logcat output with auto-generated class/method/line info        |
| `HxToast`                   | UI        | Colored toasts: info, success, warning, error                             |
| `HxProgress`                | UI        | Non-cancelable progress dialog with Sprite animations                     |
| `HxPopup`                   | UI        | Image popups with optional HTML text, URL loading via Picasso             |
| `HxListOfItems`             | UI        | Searchable RecyclerView dialog with selection, theming, long-click        |
| `Downloader`                | Network   | PRDownloader wrapper with notifications, progress dialogs, callbacks      |
| `UploadToImgur`             | Network   | OkHttp-based Imgur image upload with JSON response                        |
| `HashGenerator`             | Crypto    | SHA-1, SHA-256, SHA-512, MD5                                              |
| `EncryptionCaesar`          | Crypto    | Caesar cipher encrypt/decrypt with position-based key alternation         |
| `FrogoListWithAutoComplete` | UI        | Chip-style multi-value input with auto-complete suggestions               |

---

## Quick Start

### 1. Add JitPack to `settings.gradle`

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

### 2. Add the dependency

[![](https://jitpack.io/v/H4zh4n/HaxHanTools.svg)](https://jitpack.io/#H4zh4n/HaxHanTools)

```gradle
implementation 'com.github.H4zh4n:HaxHanTools:1.0.20'
```

> 👆 Click the badge above to see the latest available version on JitPack.

### 3. Add the auto-initializer to your manifest

```xml

<provider
    android:name="com.dev.hazhanjalal.haxhantools.utils.implementations.HaxHanToolsInitializer"
    android:authorities="${applicationId}.haxhantools-initializer" android:exported="false" />
```

That's it — the library is ready to use. No manual `Utils` initialization needed.

> **Legacy setup**: If you prefer manual control, call `new Utils(this)` in your first Activity's
> `onCreate()` and set `Utils.activeContext = this` in each Activity's `onResume()`.
> See [Setup & Initialization](docs/setup.md) for details.

---

## Usage Examples

### Logger

```java
Logger.setEnabled(BuildConfig.DEBUG);
Logger.d("Data loaded successfully");
Logger.e("Network error",exception);
```

> 📖 [Full Logger docs](docs/logger.md)

### Toast

```java
HxToast.showToastSuccess("Upload complete!");
HxToast.showToastError("Connection failed");
```

> 📖 [Full Toast docs](docs/toast.md)

### List Dialog

```java
String[] cities = {"Sulaymaniyah", "Hawler", "Kirkuk"};

new HxListOfItems<>(this)
        .setItems(cities)
        .setItemTextProvider(position ->cities[position])
        .setOnItemClickListener(new OnHxItemClickListener<>() {
            @Override
            public void onItemClicked () {
                HxToast.showToast("Selected: " + itemText);
            }
        })
        .show();
```

> 📖 [Full List Dialog docs](docs/list_of_items.md)

### Progress Dialog

```java
HxProgress.showProgressDialog("Loading","Fetching data...");
// ... work ...
HxProgress.closeProgressDialog();
```

> 📖 [Full Progress docs](docs/progress.md)

### Downloader

```java
Downloader.startDownload(url, "downloads","file.pdf",
        true,   // show notification
        true,   // show progress dialog
        new OnCustomDownload() {
            @Override
            public void onDownloadCompleted () {
                HxToast.showToastSuccess("Download finished!");
            }
        });
```

> 📖 [Full Downloader docs](docs/downloader.md)

---

## Known Issues

See [Known Issues](docs/known_issues.md) for common build problems and solutions.
For additional problems, check [GitHub Issues](https://github.com/H4zh4n/HaxHanTools/issues).
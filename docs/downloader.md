# Downloader

A file download manager built on [PRDownloader](https://github.com/MindorksOpenSource/PRDownloader) with built-in support for progress dialogs, notifications, and callbacks.

> ⚠️ If downloading over HTTP: add `android:usesCleartextTraffic="true"` to your `<application>` tag in `AndroidManifest.xml`.

## Quick Start

```java
Downloader.startDownload(
    "https://example.com/file.pdf",  // URL
    "downloads",                      // subfolder
    "file.pdf",                       // file name
    false,                            // show notification
    true,                             // show progress dialog
    new OnCustomDownload() {
        @Override
        public void onDownloadComplete() {
            HxToast.showToastSuccess("Download finished!");
        }

        @Override
        public void onError(Error error) {
            HxToast.showToastError("Download failed");
        }
    });
```

---

## API Reference

### `startDownload(url, subfolder, fileName, haveNotification, haveProgressDialog, callback)`

Basic overload. Skips download if file already exists.

| Parameter | Type | Description |
|---|---|---|
| `url` | String | Full download URL |
| `subfolder` | String | Subfolder under app's external files directory |
| `fileName` | String | Name to save the file as |
| `haveNotification` | boolean | Show a notification with progress bar |
| `haveProgressDialog` | boolean | Show `HxProgress` dialog during download |
| `callback` | OnCustomDownload | Callback for download events |

### `startDownload(url, subfolder, fileName, haveNotification, haveProgressDialog, downloadEvenIfFileExists, callback)`

Same as above, but with control over re-downloading.

| Parameter | Type | Description |
|---|---|---|
| `downloadEvenIfFileExists` | boolean | If `true`, forces re-download even if the file already exists |

### `startDownload(url, subfolder, fileName, title, haveNotification, haveProgressDialog, downloadEvenIfFileExists, callback)`

Full control. Specify a custom title for the progress dialog.

| Parameter | Type | Description |
|---|---|---|
| `title` | String | Custom title for the progress dialog |

**Returns**: `long` — the download ID from PRDownloader, or `-1` if skipped (file exists).

---

## OnCustomDownload Callbacks

```java
new OnCustomDownload() {
    @Override
    public void onStartOrResume() {
        // Called when download starts or resumes
    }

    @Override
    public void onProgress(Progress progress) {
        // Raw progress: progress.currentBytes / progress.totalBytes
    }

    @Override
    public void onPause() {
        // Called when download is paused
    }

    @Override
    public void onCancel() {
        // Called when download is cancelled
    }

    @Override
    public void onDownloadComplete() {
        // Called when download finishes successfully
    }

    @Override
    public void onError(Error error) {
        // Called when download fails
    }
}
```

---

## File Location

Files are saved to:
```
{externalFilesDir}/{subfolder}/{fileName}
```

Check if a file exists with `UtilsFile`:
```java
if (UtilsFile.fileExist("downloads", "file.pdf")) {
    // File is already downloaded
}
```

---

## Notes

- Only one progress dialog is shown at a time (managed by `HxProgress`)
- Progress updates are throttled to 100 max updates per download to avoid UI lag
- Notifications use a random ID and include a progress bar
- Arabic/Eastern numerals in URLs and file names are normalized automatically
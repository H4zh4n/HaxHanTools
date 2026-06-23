# UploadToImgur

Upload images to Imgur using their API. Built on OkHttp with multipart form data and JSON response parsing.

## Prerequisites

You need Imgur API credentials:

1. Register an application at [https://api.imgur.com/oauth2/addclient](https://api.imgur.com/oauth2/addclient)
2. Choose **"Anonymous usage without user authorization"** for simple uploads
3. Copy your **Client ID** and **Client Secret**

---

## Quick Start

```java
// 1. Create the uploader with your credentials
UploadToImgur uploader = new UploadToImgur("YOUR_CLIENT_ID", "YOUR_SECRET_ID");

// 2. Pick an image (e.g. from ImagePicker) and upload
uploader.upload(
    context,
    imageUri,
    new OnUploadCallback() {
        @Override
        public void onSuccess(String imageLink, String deleteHash) {
            // imageLink — public URL of the uploaded image
            // deleteHash — use this to delete the image later via Imgur API
            HxToast.showToastSuccess("Uploaded!");
            Utils.copyToClip(imageLink);  // copy link to clipboard
        }

        @Override
        public void onFailed(String reason) {
            HxToast.showToastError("Upload failed: " + reason);
        }
    });
```

---

## Upload Without Explicit Context

If your app uses `HaxHanToolsInitializer`, you can omit the context:

```java
uploader.upload(imageUri, new OnUploadCallback() {
    @Override
    public void onSuccess(String imageLink, String deleteHash) {
        // Uses Utils.activeContext internally
    }

    @Override
    public void onFailed(String reason) {
    }
});
```

---

## Integration with ImagePicker

The library includes [ImagePicker](https://github.com/Dhaval2404/ImagePicker), making it easy to capture or select an image and upload it:

```java
ImagePicker.with(activity)
    .crop()
    .compress(1024)
    .maxResultSize(1080, 1080)
    .createIntent(intent -> {
        startActivityForResult(intent, REQUEST_IMAGE);

        // In onActivityResult:
        Uri imageUri = intent.getData();
        uploader.upload(imageUri, callback);
        return null;
    });
```

---

## OnUploadCallback

| Method | Description |
|---|---|
| `onSuccess(String imageLink, String deleteHash)` | Upload succeeded. `imageLink` is the public URL; `deleteHash` can be used to delete the image via the Imgur API |
| `onFailed(String reason)` | Upload failed with a reason string |

---

## API Reference

| Method | Description |
|---|---|
| `new UploadToImgur(String clientId, String secretId)` | Create an uploader with your Imgur credentials |
| `upload(Uri imageUri, OnUploadCallback)` | Upload using `Utils.activeContext` |
| `upload(Context, Uri imageUri, OnUploadCallback)` | Upload with explicit context |

---

## Notes

- Uploads to `https://api.imgur.com/3/upload`
- Images are sent as multipart form data via OkHttp
- Callbacks fire on the **OkHttp background thread** — not the main thread. Use `runOnUiThread` if you need to update UI from callbacks
- Internet permission is required in your manifest
- The `clientId` / `secretId` are sent as headers (`Authorization: Client-ID ...`)
# UploadToImgur

Upload images to Imgur using their API. Built on OkHttp with JSON response parsing.

## Prerequisites

You need Imgur API credentials:

1. Register an application at [https://api.imgur.com/oauth2/addclient](https://api.imgur.com/oauth2/addclient)
2. Choose "Anonymous usage without user authorization" for simple uploads
3. Copy your **Client ID** and **Client Secret**

---

## Quick Start

```java
UploadToImgur uploader = new UploadToImgur("YOUR_CLIENT_ID", "YOUR_SECRET_ID");

uploader.upload(
    context,
    imageUri,          // Uri of the image to upload
    new OnUploadCallback() {
        @Override
        public void onSuccess(String imageLink, String deleteHash) {
            // imageLink: public URL of the uploaded image
            // deleteHash: use this to delete the image later
            HxToast.showToastSuccess("Uploaded: " + imageLink);
        }

        @Override
        public void onFailed(String reason) {
            HxToast.showToastError("Upload failed: " + reason);
        }
    });
```

---

## Constructors

### `UploadToImgur(String clientId, String secretId)`

Create an uploader with your Imgur credentials.

---

## Upload Methods

### `upload(Uri imageUri, OnUploadCallback callback)`

Upload using `Utils.activeContext`.

### `upload(Context context, Uri imageUri, OnUploadCallback callback)`

Upload with explicit context.

---

## OnUploadCallback

| Method | Description |
|---|---|
| `onSuccess(String imageLink, String deleteHash)` | Upload succeeded. `imageLink` is the public URL; `deleteHash` can be used to delete the image via Imgur API |
| `onFailed(String reason)` | Upload failed with a reason string |

---

## Notes

- Uses `https://api.imgur.com/3/upload` endpoint
- Images are sent as multipart form data
- The upload runs on a background thread — callbacks fire on the OkHttp thread (not the main thread)
- Internet permission is required in your manifest
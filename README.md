[![](https://jitpack.io/v/H4zh4n/HaxHanTools.svg)](https://jitpack.io/#H4zh4n/HaxHanTools)

> No official full documentation available currently.

- [x] Easy File Download manager [Found here](https://github.com/H4zh4n/HaxHanTools/tree/master/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/download)

- [x] Easy Imgur uploader [Found here](https://github.com/H4zh4n/HaxHanTools/tree/master/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/imgur_upload_helper)
- [x] Easy debugger [Found here](https://github.com/H4zh4n/HaxHanTools/tree/master/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/print)
- [x] Easy Ui Components creator (Confirm/Input/Progress/List dialogs, Toasts) [Found here](https://github.com/H4zh4n/HaxHanTools/tree/master/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/ui)
- [x] General App utils, File Utils, Image Utils [Found here](https://github.com/H4zh4n/HaxHanTools/tree/master/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/utils)
- [x] Easy Global SharedPreferences Setup/Usage [Found here](https://github.com/H4zh4n/HaxHanTools/blob/f60a882df13c574802598cc51c2a63941d7ef3dc/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/utils/Utils.java#L434).

    Call `new Utils(this)` once in the first activity. later use this methods
[put(key, value)](https://github.com/H4zh4n/HaxHanTools/blob/f60a882df13c574802598cc51c2a63941d7ef3dc/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/utils/Utils.java#L353),
[getFloatPref(key, default value)](https://github.com/H4zh4n/HaxHanTools/blob/f60a882df13c574802598cc51c2a63941d7ef3dc/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/utils/Utils.java#L374),
[getIntegerPref(key, default value)](https://github.com/H4zh4n/HaxHanTools/blob/f60a882df13c574802598cc51c2a63941d7ef3dc/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/utils/Utils.java#L382),
[getLongPref(key, default value)](https://github.com/H4zh4n/HaxHanTools/blob/f60a882df13c574802598cc51c2a63941d7ef3dc/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/utils/Utils.java#L390),
[getStringPref(key, default value)](https://github.com/H4zh4n/HaxHanTools/blob/f60a882df13c574802598cc51c2a63941d7ef3dc/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/utils/Utils.java#L398),
[getBooleanPref(key, default value)](https://github.com/H4zh4n/HaxHanTools/blob/f60a882df13c574802598cc51c2a63941d7ef3dc/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/utils/Utils.java#L426),
[removeKeyPrefs(key)](https://github.com/H4zh4n/HaxHanTools/blob/f60a882df13c574802598cc51c2a63941d7ef3dc/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/utils/Utils.java#L406),
[prefsContains(key)](https://github.com/H4zh4n/HaxHanTools/blob/f60a882df13c574802598cc51c2a63941d7ef3dc/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/utils/Utils.java#L412),


___

# Implementation
### Jitpack required in `settings.gradle`
```gradle
repositories {
  //...
  maven { url 'https://jitpack.io' }

}
```

### Implementation in `build.gradle` Module :
```gradle
implementation 'com.github.H4zh4n:HaxHanTools:1.0.10.3'
```
___

# Usage 
For the whole process to work properly, `Utils()` must be initialized before any of the ui/ context based components to be used.

I Recommend that you write below line in first activities `onCreate`
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    new Utils(this); // <--- have this

    // ... other codes
}

```

For the dialogs/toast and such to work and display data properly, make sure that for each activity you have below line in it's `onResume`

```java
@Override
protected void onResume() {
    super.onResume();

    Utils.activeContext = this;

    // ... other codes
}

```
This is so that the app knows on which screen the dialogs should be shown.



___

# Known Errors

### -- Duplicate Class Found
- If you faced duplicate Class found for `lifecycle-viewmodel` or  `androidx.lifecycle` or `lifecycle-viewmodel-ktx` then implement as following

```gradle
implementation('com.github.H4zh4n:HaxHanTools:1.0.10.3') {
        exclude group: 'androidx.lifecycle', module: 'lifecycle-viewmodel'
        exclude group: 'androidx.lifecycle', module: 'lifecycle-viewmodel-ktx'
}
```

### -- More than one file was found

- below code might be needed in `build.grade` Module to avoid `More than one file was found with OS independent path 'META-INF/DEPENDENCIES'`


```gradle
android {
 // ...
    packagingOptions {
        exclude 'META-INF/*'
    }
   //...
}
```



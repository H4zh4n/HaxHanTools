[![](https://jitpack.io/v/H4zh4n/HaxHanTools.svg)](https://jitpack.io/#H4zh4n/HaxHanTools)

> No official full documentation available currently.

- [x] Easy File Download manager [Found here](https://github.com/H4zh4n/HaxHanTools/tree/master/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/download)

- [x] Easy Imgur uploader [Found here](https://github.com/H4zh4n/HaxHanTools/tree/master/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/imgur_upload_helper)
- [x] Easy debugger [Found here](https://github.com/H4zh4n/HaxHanTools/tree/master/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/print)
- [x] Easy Ui Components creator (Confirm/Input/Progress/List dialogs, Toasts) [Found here](https://github.com/H4zh4n/HaxHanTools/tree/master/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/ui)
- [x] General App utils, File Utils, Image Utils [Found here](https://github.com/H4zh4n/HaxHanTools/tree/master/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/utils)
- [x] Easy Global SharedPreferences Setup/Usage [Found here](https://github.com/H4zh4n/HaxHanTools/blob/master/haxhantools/src/main/java/com/dev/hazhanjalal/haxhantools/utils/utils/Utils.java)

___

# Implementation
### Jitpack required in `settings.gradle`
```
repositories {
  //...
  maven { url 'https://jitpack.io' }

}
```

### Implementation in `build.gradle` Module :
```
implementation 'com.github.H4zh4n:HaxHanTools:$LatestVersion'
```
be sure to change `$LatestVersion` to the latest version in [Releases](https://github.com/H4zh4n/HaxHanTools/releases) (without the v)

Latest version : [![](https://jitpack.io/v/H4zh4n/HaxHanTools.svg)](https://jitpack.io/#H4zh4n/HaxHanTools)

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

```
implementation('com.github.H4zh4n:HaxHanTools:$LatestVersion') {
        exclude group: 'androidx.lifecycle', module: 'lifecycle-viewmodel'
        exclude group: 'androidx.lifecycle', module: 'lifecycle-viewmodel-ktx'
}
```

### -- More than one file was found

- below code might be needed in `build.grade` Module to avoid `More than one file was found with OS independent path 'META-INF/DEPENDENCIES'`


```
android {
 // ...
   packagingOptions {
          exclude 'META-INF/DEPENDENCIES'
          exclude 'META-INF/LICENSE'
          exclude 'META-INF/LICENSE.txt'
          exclude 'META-INF/license.txt'
          exclude 'META-INF/NOTICE'
          exclude 'META-INF/NOTICE.txt'
          exclude 'META-INF/notice.txt'
          exclude 'META-INF/ASL2.0'
          exclude("META-INF/*.kotlin_module")
      }
   //...
}
```



[![](https://jitpack.io/v/H4zh4n/HaxHanTools.svg)](https://jitpack.io/#H4zh4n/HaxHanTools)

No documentation available currently.

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



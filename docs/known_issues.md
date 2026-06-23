# Known Issues

Common problems and their solutions when integrating HaxHanTools.

---

## Duplicate Class Found

If you encounter a duplicate class error related to `lifecycle-viewmodel` or `androidx.lifecycle` or `lifecycle-viewmodel-ktx`:

```
Duplicate class found ...
```

**Solution** — exclude the conflicting modules in your `app/build.gradle`:

```gradle
implementation('com.github.H4zh4n:HaxHanTools:1.1.0') {
    exclude group: 'androidx.lifecycle', module: 'lifecycle-viewmodel'
    exclude group: 'androidx.lifecycle', module: 'lifecycle-viewmodel-ktx'
}
```

---

## More Than One File Was Found

If the build fails with:

```
More than one file was found with OS independent path 'META-INF/DEPENDENCIES'
```

**Solution** — add `packagingOptions` inside the `android` block of your `app/build.gradle`:

```gradle
android {
    // ...
    packagingOptions {
        exclude 'META-INF/*'
    }
    // ...
}
```

---

## Still Having Issues?

Search or open a new issue on [GitHub Issues](https://github.com/H4zh4n/HaxHanTools/issues).

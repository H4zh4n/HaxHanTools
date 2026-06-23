# Logger

A debug logging utility that prints **clickable** class name, method name, and line number in Android Studio's Logcat window. All default output uses the `-HAX-` tag for easy filtering.

## Features

- **Clickable Logcat links** — `(FileName:LineNumber)` format recognized by Android Studio
- **Auto caller detection** — class name, method name, and line number captured automatically
- **All priority levels** — Verbose, Debug, Info, Warning, Error
- **Customizable default tag** — change the global tag from `-HAX-` to anything
- **Zero overhead when disabled** — set `LOGGING_ENABLED = false` (default) for production
- **Throwable support at all levels** — stack traces work on Verbose through Error

---

## Enabling Logging

```java
// Auto-detect debuggable flag (recommended — call once in first Activity)
Logger.setEnabled(context);

// Or manually
Logger.setEnabled(BuildConfig.DEBUG);
```

When `LOGGING_ENABLED` is `false` (default in library mode), all log calls are no-ops — safe to leave in production.

---

## Changing the Default Tag

```java
// Override the default "-HAX-" tag globally:
Logger.setTag("MY_APP");

// All subsequent calls without an explicit tag will use "MY_APP":
Logger.d("Data loaded");   // → D/MY_APP: (MainActivity.java:42)	: Data loaded

// Reset to default:
Logger.setTag(null);
```

---

## Usage by Priority Level

### Verbose — `v()`

```java
// Just log location (method name becomes the message)
Logger.v();

// Simple message
Logger.v("Entering setup phase");

// Custom tag
Logger.v("NETWORK", "Sending ping");

// With exception (stack trace appended)
Logger.v("NETWORK", "Ping trace", exception);
```

### Debug — `d()`

```java
Logger.d("onBindViewHolder called");
Logger.d("DB", "Query: " + sql);
Logger.d("DB", "Query failed", exception);
```

### Info — `i()`

```java
Logger.i("User session started");
Logger.i("AUTH", "Token refreshed");
Logger.i("AUTH", "Token refresh failed", exception);
```

### Warning — `w()`

```java
Logger.w("Memory usage above threshold");
Logger.w("CACHE", "Cache miss for key: " + key);
Logger.w("CACHE", "Cache write failed", exception);
```

### Error — `e()`

```java
// Just the exception
Logger.e(exception);

// Message with exception
Logger.e("Failed to parse JSON", exception);

// Just a message
Logger.e("Unreachable code reached");

// Custom tag
Logger.e("API", "Status 500");
Logger.e("API", "Request failed", exception);
```

---

## Sample Logcat Output

```
V/-HAX-: (MainActivity.java:42)	: onCreate
D/-HAX-: (ApiClient.java:128)	: fetchData
I/-HAX-: (SessionManager.java:55)	: Token refreshed
W/-HAX-: (CacheManager.java:33)	: Cache miss for key: user_123
E/-HAX-: (JsonParser.java:90)	: Failed to parse JSON
E/-HAX-: java.lang.IllegalStateException: ...
```

**The `(FileName:LineNumber)` prefix is clickable** — tap it in Logcat to jump directly to that line in Android Studio.

---

## API Reference

| Method | Description |
|---|---|
| `setEnabled(Context)` | Auto-detect debuggable flag |
| `setEnabled(boolean)` | Manual on/off |
| `setTag(String)` | Override the default `-HAX-` tag globally (null to reset) |
| `v()` | Verbose — location only |
| `v(String msg)` | Verbose with message |
| `v(String tag, String msg)` | Verbose with custom tag |
| `v(String tag, String msg, Throwable tr)` | Verbose with tag, message & exception |
| `d(String msg)` | Debug with message |
| `d(String tag, String msg)` | Debug with custom tag |
| `d(String tag, String msg, Throwable tr)` | Debug with tag, message & exception |
| `i(String msg)` | Info with message |
| `i(String tag, String msg)` | Info with custom tag |
| `i(String tag, String msg, Throwable tr)` | Info with tag, message & exception |
| `w(String msg)` | Warning with message |
| `w(String tag, String msg)` | Warning with custom tag |
| `w(String tag, String msg, Throwable tr)` | Warning with tag, message & exception |
| `e(Throwable tr)` | Error — exception only |
| `e(String msg)` | Error — message only |
| `e(String msg, Throwable tr)` | Error — message & exception |
| `e(String tag, String msg)` | Error with custom tag |
| `e(String tag, String msg, Throwable tr)` | Error with tag, message & exception |

---

## How It Works

1. Each call walks the stack trace to find the caller **outside** the Logger class
2. The `(FileName:LineNumber)` prefix is extracted — this is the pattern Android Studio recognizes for clickable links
3. If no message is provided, the method name is used instead
4. For `ERROR`/`ASSERT` priority, throwables use native `Log.e()` stack trace behavior
5. For non-error levels, the stack trace string is appended inline

---

## Tips

- **Logcat filter**: Set the filter to `-HAX-` (or your custom tag) to see only your logs
- **Click to navigate**: Click any `(FileName.java:NN)` entry in Logcat to jump to that line
- **Production safety**: The default `LOGGING_ENABLED = false` makes every call a no-op with negligible overhead
- **Stack depth**: If you wrap Logger in another utility, you may need to adjust the stack walk logic
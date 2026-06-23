# Hash & Encryption

Utility classes for hashing and simple encryption.

---

## HashGenerator

Generate cryptographic hashes using standard algorithms.

### API

```java
// SHA-1
String sha1 = HashGenerator.getSHA1("hello");

// SHA-256
String sha256 = HashGenerator.getSHA256("hello");

// SHA-512
String sha512 = HashGenerator.getSHA512("hello");

// MD5
String md5 = HashGenerator.getMD5("hello");
```

### Methods

| Method | Algorithm | Typical Use |
|---|---|---|
| `getSHA1(String)` | SHA-1 | Legacy checksums |
| `getSHA256(String)` | SHA-256 | Data integrity, passwords |
| `getSHA512(String)` | SHA-512 | Stronger hashing |
| `getMD5(String)` | MD5 | Quick checksums (not for security) |

All methods return a lowercase hex string, or `null` on failure.

---

## EncryptionCaesar

A Caesar cipher variant where the key direction alternates based on character position. **Not suitable for production security** — use for simple obfuscation only.

### Basic Usage

```java
String original = "Hello World";

// Encrypt (default key length: 9)
String encrypted = EncryptionCaesar.encrypt(original);

// Decrypt (default key length: 9)
String decrypted = EncryptionCaesar.decrypt(encrypted);
```

### Custom Key Length

```java
String encrypted = EncryptionCaesar.encrypt("secret", 12);
String decrypted = EncryptionCaesar.decrypt(encrypted, 12);
```

### Methods

| Method | Description |
|---|---|
| `encrypt(String text)` | Encrypt with default key length (9) |
| `encrypt(String text, int keyLength)` | Encrypt with custom key length |
| `decrypt(String text)` | Decrypt with default key length (9) |
| `decrypt(String text, int keyLength)` | Decrypt with custom key length |

### How It Works

For each character at position `i`, the algorithm computes:
```
value = i + text.length()
```
- If `value` is **even**: shifts the character **up** by `keyLength`
- If `value` is **odd**: shifts the character **down** by `keyLength`

Decryption reverses this logic.

---

## Notes

- `HashGenerator` returns `null` if the algorithm is not available (shouldn't happen on Android)
- `EncryptionCaesar` is **not** cryptographically secure — do not use for sensitive data
- Both classes are stateless and thread-safe
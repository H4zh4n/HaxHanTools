package com.dev.hazhanjalal.haxhantools.utils.hash_encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
    
    public static String getSHA1(String message) {
        return getHash(message, "SHA-1");
    }
    
    public static String getSHA256(String message) {
        return getHash(message, "SHA-256");
    }
    
    
    public static String getSHA512(String message) {
        return getHash(message, "SHA-512");
    }
    
    public static String getMD5(String message) {
        return getHash(message, "MD5");
    }
    
    private static String getHash(String message, String shaAlgo) {
        try {
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance(shaAlgo);
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            }
            digest.reset();
            return bin2hex(digest.digest(message.getBytes()));
        } catch (Exception ignored) {
            return null;
        }
    }
    
    private static String bin2hex(byte[] data) {
        StringBuilder hex = new StringBuilder(data.length * 2);
        for (byte b : data)
            hex.append(String.format("%02x", b & 0xFF));
        return hex.toString();
    }
    
}

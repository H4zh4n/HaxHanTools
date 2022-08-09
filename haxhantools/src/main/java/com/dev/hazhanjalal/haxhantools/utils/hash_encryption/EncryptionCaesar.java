package com.dev.hazhanjalal.haxhantools.utils.hash_encryption;

public class EncryptionCaesar {
    
    public static String encrypt(String text) {
        return encrypt(text, 9);
    }
    
    public static String encrypt(String text, int keyLength) {
        String result = "";
        
        for (int i = 0; i < text.length(); i++) {
            int value = i /*+ text.charAt(i)*/ + text.length();
            
            // result += ((char) (text.charAt(i) + keyLength)) + "";
            
            if (value % 2 == 0) {
                result += ((char) (text.charAt(i) + keyLength)) + "";
            } else {
                result += ((char) (text.charAt(i) - keyLength)) + "";
            }
        }
        
        return result;
    }
    
    public static String decrypt(String text) {
        return decrypt(text, 9);
    }
    
    public static String decrypt(String text, int keyLength) {
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            int value = i + /*text.charAt(i) +*/ text.length();
            
            //result += ((char) (text.charAt(i) - keyLength)) + "";
            
            if (value % 2 == 0) {
                result += ((char) (text.charAt(i) - keyLength)) + "";
            } else {
                result += ((char) (text.charAt(i) + keyLength)) + "";
            }
        }
        
        return result;
    }
    
}

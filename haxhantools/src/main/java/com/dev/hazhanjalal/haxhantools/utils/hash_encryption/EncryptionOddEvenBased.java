package com.dev.hazhanjalal.haxhantools.utils.hash_encryption;

public class EncryptionOddEvenBased {
    
    public static String encrypt(String text) {
        return encrypt(text, 3);
    }
    
    public static String encrypt(String text, int keyLength) {
        String result = "";
        
        for (int i = 0; i < text.length(); i++) {
            int value = i + text.charAt(i) + text.length();
            
            result += value % 2 == 0 ? ((char) (text.charAt(i) + keyLength)) + "" : ((char) (text.charAt(i) - keyLength)) + "";
            /*
            if (value % 2 == 0) {
                result += ((char) (text.charAt(i) + length)) + "";
            } else {
                result += ((char) (text.charAt(i) - length)) + "";
            }
             */
        }
        
        return result;
    }
    
    public static String decrypt(String text) {
        return decrypt(text, 3);
    }
    
    public static String decrypt(String text, int keyLength) {
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            int value = i + text.charAt(i) + text.length();
            
            result += value % 2 == 0 ? ((char) (text.charAt(i) - keyLength)) + "" : ((char) (text.charAt(i) + keyLength)) + "";

            /*
             if (value % 2 == 0) {
                result += ((char) (text.charAt(i) - length)) + "";
            } else {
                result += ((char) (text.charAt(i) + length)) + "";
            }
             */
        }
        
        return result;
    }
    
}

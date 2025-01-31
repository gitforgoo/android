package com.goodtechsystem.mypwd.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncDecUtil {

    private static final String aseKey = "ab2025c0131d2357";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static String IV = "";

    public EncDecUtil() {
        IV = aseKey.substring(0,16);
    }

    public String encryptString(String normalString) {

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            byte[] keyData = aseKey.getBytes();
            SecretKey secretKey = new SecretKeySpec(keyData, ALGORITHM);

            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            byte[] encrypted = cipher.doFinal(normalString.getBytes(StandardCharsets.UTF_8));
            byte[] combined = new byte[IV.length() + encrypted.length];

            System.arraycopy(IV.getBytes(StandardCharsets.UTF_8), 0, combined, 0, IV.length());
            System.arraycopy(encrypted, 0, combined, IV.length(), encrypted.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("Aes - encryptString", e);
        }
    }

    public String decryptString(String encodedStr) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedStr);

            byte[] iv = new byte[16];
            byte[] encrypted = new byte[decodedBytes.length - 16];

            System.arraycopy(decodedBytes, 0, iv, 0, 16);
            System.arraycopy(decodedBytes, 16, encrypted, 0, encrypted.length);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            byte[] keyData = aseKey.getBytes();
            SecretKey secretKey = new SecretKeySpec(keyData, ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Aes - decryptString", e);
        }
    }

}

package com.http.las.minsides.server.tools;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class Cryptor {

    public static String encrypt(String value, byte[] key) {
        TextEncryptor encryptor = getEncryptor(key);
        String cipherText = encryptor.encrypt(value);
        return cipherText;
    }

    public static String decrypt(String value, byte[] key) {
        TextEncryptor encryptor = getEncryptor(key);
        String decrypted = encryptor.decrypt(value);
        return decrypted;
    }

    private static TextEncryptor getEncryptor(byte[] key) {
        String salt = generateSalt(key);
        StringBuilder builder = new StringBuilder();
        while (builder.length() < 16) {
            for (byte b : key) {
                builder.append(b).append('c');
            }
        }
        String textFromBytes = builder.toString();
        TextEncryptor encryptor = Encryptors.text(textFromBytes, salt);
        return encryptor;
    }

    private static String generateSalt(byte[] key) {
        StringBuilder builder = new StringBuilder();
        for (int i = key.length - 1; i >= 0; i--) {
            byte b = key[i];
            builder.append(charFromByte(b, 48, 57)).append(charFromByte(b, 97, 102));
        }
        if (builder.length() % 2 == 1) {
            builder.append(charFromByte(key[0], 48, 57));
        }
        return builder.toString();
    }

    private static char charFromByte(byte b, int min, int max) {
        int shift = max - min + 1;
        while (b < min) {
            b += shift;
        }
        while (b > max) {
            b -= shift;
        }
        return (char) b;
    }

}

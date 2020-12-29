package com.gtmarshall.EncryptionAlgorithms;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES extends EncryptionAlgorithm {

  private static String salt = " ";

  @Override
  String encrypt(String plaintext, String key) {
    try {
      Cipher cipher = genCipher(key, Cipher.ENCRYPT_MODE);
      return Base64.getEncoder()
          .encodeToString(cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e) {
    }
    return null;
  }

  @Override
  String decrypt(String ciphertext, String key) {
    try {
      Cipher cipher = genCipher(key, Cipher.DECRYPT_MODE);
      return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)));
    } catch (Exception e) {
    }
    return null;
  }

  @Override
  void setSalt(String str) {
    salt = str;
  }

  private Cipher genCipher(String key, int mode) throws Exception {
    byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    IvParameterSpec ivspec = new IvParameterSpec(iv);

    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
    SecretKey tmp = factory.generateSecret(spec);
    SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
    cipher.init(mode, secretKey, ivspec);
    return cipher;
  }
}

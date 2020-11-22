package edu.wpi.gtmarshall.EncryptionAlgorithms;

public abstract class EncryptionAlgorithm {
  public abstract String encrypt(String plaintext, String key);

  public abstract String decrypt(String ciphertext, String key);

  public abstract void setSalt(String str);
}

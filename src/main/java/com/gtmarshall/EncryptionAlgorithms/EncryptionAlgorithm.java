package com.gtmarshall.EncryptionAlgorithms;

import com.gtmarshall.FileManagement.User;

public abstract class EncryptionAlgorithm {
  private static EncryptionAlgorithm selected;
  private static User user;

  public static final String encrypt(String str) {
    return selected.encrypt(str, user.getPassWord());
  }

  public static User getUser() {
    return user;
  }

  abstract String encrypt(String plaintext, String key);

  abstract String decrypt(String ciphertext, String key);

  public static final String decrypt(String str) {
    return selected.decrypt(str, user.getPassWord());
  }

  abstract void setSalt(String str);

  public static void setUser(User user) {
    if (selected == null) return;
    System.out.println("User Selected");
    selected.user = user;
    if (user != null) selected.setSalt(user.getSalt());
  }

  public static EncryptionAlgorithm getSelected() {
    return selected;
  }

  public final void select() {
    System.out.println("Algorithm Selected");
    EncryptionAlgorithm.selected = this;
  }
}

package edu.wpi.gtmarshall.FileManagement;

import java.io.File;
import java.util.Random;

public class User {
  private String userName, passWord;

  public User(String userName, String passWord) {
    this.userName = userName;
    this.passWord = passWord;
  }

  public String getUserName() {
    return userName;
  }

  public String getPassWord() {
    return passWord;
  }

  public String getIdentifier() {
    Random r = new Random();
    String ret = userName;
    return ret;
  }

  public String getPath() {
    String ret = ""; // ".\\folder\\";
    ret += Integer.toHexString(Integer.hashCode(userName.hashCode()));
    ret += ".info";
    return ret;
  }

  public boolean hasFile() {
    File f = new File(getPath());
    return f.exists();
  }
}

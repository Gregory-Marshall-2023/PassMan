package edu.wpi.gtmarshall.FileManagement;

import java.io.File;
import java.util.Random;

public class User {
  private String userName, passWord;
  private static String dir = "folder";

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

  public File getPath() {
    String fileName = "";
    fileName = getSalt();
    fileName += ".info";
    File dirFile = new File(dir);
    if (!dirFile.exists()) dirFile.mkdirs();
    return new File(dir, fileName);
  }

  public boolean hasFile() {
    File f = getPath();
    return f.exists();
  }

  // to be replaced with a smarter algorithm to create similarly large numbers with modulo
  public String getSalt() {
    double d = (double) userName.hashCode();
    while (d < 1e9) d *= Math.PI;
    while (d > 1e11) d /= Math.PI;
    return Double.toHexString(d);
  }
}

package edu.wpi.gtmarshall.FileManagement;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public class DataSaver {
  public void write(String text) {
    String binText = toHex(text);
  }

  private String toHex(String str) {
    String ret = "";
    byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
    for (byte b : bytes) {
      ret += Integer.toHexString(b);
    }
    return ret;
  }

  public void save(String string) {
    try {
      FileWriter writer = new FileWriter("EncryptedData.file");
      writer.write(toHex(string));
      System.out.println("Data Saved");
      writer.flush();
      writer.close();
    } catch (Exception e) {
      System.out.println("Save Failed");
      e.printStackTrace();
    }
  }
}

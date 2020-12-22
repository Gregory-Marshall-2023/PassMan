package edu.wpi.gtmarshall.FileManagement;

import edu.wpi.gtmarshall.EncryptionAlgorithms.EncryptionAlgorithm;
import java.io.*;
import java.util.stream.Stream;

public class DataParser {

  public static String load() {
    String str = "";
    try {
      String path = EncryptionAlgorithm.getUser().getPath();
      File f = new File(path);
      if (!f.exists()) {
        f.createNewFile();
        return "";
      }
      InputStream in = new FileInputStream(f);
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      while (reader.ready()) str += reader.readLine();
      System.out.println("Data Loaded");
      reader.close();
    } catch (Exception e) {
      System.out.println("Load Failed");
      e.printStackTrace();
    }
    return str;
  }

  public static Stream<String> getEntries() {
    String str = load();
    System.out.println("GETTING ENTRIES:");
    System.out.println(str);
    if (str == null || str == "") return "".lines();
    str = EncryptionAlgorithm.decrypt(str);
    System.out.println(str);
    if (str == null) return null;
    String headLine = str.lines().findFirst().get();
    if (!headLine.startsWith(EncryptionAlgorithm.getUser().getUserName())) {
      throw new RuntimeException("ILLEGAL FILE INPUT");
    }
    return str.lines().skip(1);
  }
}

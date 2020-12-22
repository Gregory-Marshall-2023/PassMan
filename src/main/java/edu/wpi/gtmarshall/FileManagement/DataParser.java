package edu.wpi.gtmarshall.FileManagement;

import edu.wpi.gtmarshall.EncryptionAlgorithms.EncryptionAlgorithm;
import java.io.*;
import java.util.Optional;
import java.util.stream.Stream;

public class DataParser {

  public static String load() {
    StringBuilder str = new StringBuilder();
    try {
      File f = EncryptionAlgorithm.getUser().getPath();
      if (!f.exists()) {
        boolean created = f.createNewFile();
        return "";
      }
      InputStream in = new FileInputStream(f);
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      while (reader.ready()) str.append(reader.readLine());
      System.out.println("Data Loaded");
      reader.close();
    } catch (Exception e) {
      System.out.println("Load Failed");
      e.printStackTrace();
    }
    return str.toString();
  }

  public static Stream<String> getEntries() {
    String str = load();
    if (str.equals("")) return "".lines();
    str = EncryptionAlgorithm.decrypt(str);
    if (str == null) return null;
    Optional<String> first = str.lines().findFirst();
    String headLine;
    if(first.isPresent())
     headLine = first.get();
    else return "".lines();
    if (!headLine.startsWith(EncryptionAlgorithm.getUser().getUserName())) {
      throw new RuntimeException("ILLEGAL FILE INPUT");
    }
    return str.lines().skip(1);
  }
}

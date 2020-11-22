package edu.wpi.gtmarshall.FileManagement;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataParser {

  public String load() {
    String s = "";
    try {
      File f = new File("EncryptedData.file");
      InputStream in = new FileInputStream(f);
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      while (reader.ready()) s += reader.readLine();
      System.out.println("Data Loaded");
      reader.close();
    } catch (Exception e) {
      System.out.println("Load Failed");
      e.printStackTrace();
    }
    String[] byteStrings = s.split(String.format("(?<=\\G.{%1$d})", 2));
    s = "";
    List<Byte> bytes =
        Arrays.stream(byteStrings)
            .map(
                (snip) ->
                    new Byte(
                        (byte)
                            ((Character.digit(snip.charAt(0), 16) << 4)
                                + Character.digit(snip.charAt(1), 16))))
            .collect(Collectors.toList());
    byte[] byteArray = new byte[bytes.size()];
    for (int i = 0; i < bytes.size(); i++) {
      byteArray[i] = bytes.get(i);
    }
    s = new String(byteArray, StandardCharsets.UTF_8);
    return s;
  }
}

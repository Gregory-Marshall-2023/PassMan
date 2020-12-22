package edu.wpi.gtmarshall.FileManagement;

import edu.wpi.gtmarshall.EncryptionAlgorithms.EncryptionAlgorithm;
import edu.wpi.gtmarshall.pages.Entry;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;

public class DataSaver {
  public static void save(LinkedList<Entry> entries) {
    System.out.println("SAVING===========================================");
    try {
      File f = EncryptionAlgorithm.getUser().getPath();
      FileWriter writer = new FileWriter(f);
      String str = EncryptionAlgorithm.getUser().getIdentifier() + "\n";
      ;
      System.out.println(entries.toString());
      for (Entry e : entries) {
        str = str + e.toString() + "\n";
      }
      System.out.println("UNENCRYPTED");
      System.out.println(str);
      str = EncryptionAlgorithm.encrypt(str);
      System.out.println("ENCRYPTED");
      System.out.println(str);
      writer.write(str);
      writer.flush();
      writer.close();
      System.out.println("Data Saved");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

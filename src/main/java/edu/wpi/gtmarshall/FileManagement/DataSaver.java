package edu.wpi.gtmarshall.FileManagement;

import edu.wpi.gtmarshall.EncryptionAlgorithms.EncryptionAlgorithm;
import edu.wpi.gtmarshall.pages.Entry;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;

public class DataSaver {
  public static void save(LinkedList<Entry> entries) {
    try {
      File f = EncryptionAlgorithm.getUser().getPath();
      FileWriter writer = new FileWriter(f);
      StringBuilder str = new StringBuilder(EncryptionAlgorithm.getUser().getIdentifier() + "\n");
      System.out.println(entries.toString());
      for (Entry e : entries) {
        str.append(e.toString()).append("\n");
      }
      str = new StringBuilder(EncryptionAlgorithm.encrypt(str.toString()));
      writer.write(str.toString());
      writer.flush();
      writer.close();
      System.out.println("Data Saved");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

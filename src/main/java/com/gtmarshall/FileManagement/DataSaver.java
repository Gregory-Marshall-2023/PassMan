package com.gtmarshall.FileManagement;

import com.gtmarshall.EncryptionAlgorithms.EncryptionAlgorithm;
import com.gtmarshall.pages.Entry;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;

public class DataSaver {
  public static void save(LinkedList<Entry> entries) {
    if (!EncryptionAlgorithm.getUser().isTrusted()) return;
    try {
      File f = EncryptionAlgorithm.getUser().getPath();
      FileWriter writer = new FileWriter(f);
      StringBuilder str = new StringBuilder(EncryptionAlgorithm.getUser().getIdentifier() + "\n");
      for (Entry e : entries) {
        str.append(e.toString()).append("\n");
      }
      str = new StringBuilder(EncryptionAlgorithm.encrypt(str.toString()));
      writer.write(str.toString());
      writer.flush();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

package com.gtmarshall;

import java.io.File;

public class Main {
  public static void main(String[] args) {
    File theDir = new File("/folder");
    if (!theDir.exists()) {
      theDir.mkdirs();
    }
    App.launch(App.class, args);
  }
}

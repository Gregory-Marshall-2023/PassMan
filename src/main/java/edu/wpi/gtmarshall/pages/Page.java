package edu.wpi.gtmarshall.pages;

import javafx.fxml.Initializable;
import javafx.util.Pair;

public abstract class Page implements Initializable {
  protected abstract String getPath();

  protected Pair<Double, Double> getDims() {
    return new Pair<Double, Double>(0d, 0d);
  }

  void onClose() {}
}

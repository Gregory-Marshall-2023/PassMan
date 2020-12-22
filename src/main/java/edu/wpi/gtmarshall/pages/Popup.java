package edu.wpi.gtmarshall.pages;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class Popup extends Page {
  Stage stage;

  final void setStage(Stage stage) {
    this.stage = stage;
  }

  @FXML
  final void onClose(WindowEvent windowEvent) {
    onClose();
  }

  void onClose() {}

  final void close() {
    stage.close();
  }
}

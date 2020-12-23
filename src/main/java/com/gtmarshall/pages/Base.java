package com.gtmarshall.pages;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Base extends Page {

  @FXML AnchorPane basePane;

  private Runnable onCLose;
  private Parent child = null;

  public void setChild(Parent pane, Pair<Double, Double> dims) {
    if (child != null) basePane.getChildren().remove(child);
    basePane.setMinWidth(dims.getKey());
    basePane.setMinHeight(dims.getValue());
    basePane.getChildren().add(pane);
    child = pane;
  }

  @Override
  protected String getPath() {
    return "Base.fxml";
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    onCLose =
        new Runnable() {
          @Override
          public void run() {}
        };
  }

  @FXML
  public void exit(ActionEvent event) {
    onCLose.run();
    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    stage.close();
  }

  public void setOnClose(Runnable onClose) {
    this.onCLose = onClose;
  }
}

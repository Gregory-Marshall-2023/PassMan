package com.gtmarshall.pages;

import com.gtmarshall.FileManagement.Password;
import com.gtmarshall.FileManagement.PasswordGenerator;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class PasswordGen extends Popup {
  @FXML Spinner<Integer> sizeSelector;
  @FXML ChoiceBox<PasswordGenerator.PasswordType> typeSelector;
  @FXML TextField viewer;

  Password password;

  PasswordGen(Password password) {
    this.password = password;
  }

  @Override
  protected String getPath() {
    return "PasswordGen.fxml";
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    typeSelector
        .getItems()
        .addAll(
            PasswordGenerator.PasswordType.AlphaNumeric,
            PasswordGenerator.PasswordType.AlphaNumeric_No_caps,
            PasswordGenerator.PasswordType.AlphaNumeric_With_Special);
    typeSelector.valueProperty().addListener(this::onChange);
    typeSelector.setValue(PasswordGenerator.PasswordType.AlphaNumeric);
  }

  private void onChange(Observable observable) {
    int min = 2;
    int val = sizeSelector.getValue();
    switch (typeSelector.getValue()) {
      case AlphaNumeric:
        min = 3;
        break;
      case AlphaNumeric_No_caps:
        min = 2;
        break;
      case AlphaNumeric_With_Special:
        min = 4;
        break;
    }
    if (val < min) val = min;
    sizeSelector.setValueFactory(
        new SpinnerValueFactory.IntegerSpinnerValueFactory(min, Integer.MAX_VALUE, val));
  }

  @FXML
  void copyPass(ActionEvent event) {
    copyString(viewer.getText());
  }

  @FXML
  void done(ActionEvent event) {
    if (!viewer.getText().isBlank()) {
      password.setPassword(viewer.getText());
    }
    close();
  }

  @FXML
  void cancel(ActionEvent event) {
    close();
  }

  @FXML
  void generate(ActionEvent event) {
    viewer.setText(PasswordGenerator.generate(sizeSelector.getValue(), typeSelector.getValue()));
  }

  private void copyString(String str) {
    StringSelection selection = new StringSelection(str);
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
  }
}

package com.gtmarshall.pages;

import com.gtmarshall.FileManagement.Password;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;

public class NewEntry extends Popup {
  Viewer parent;
  @FXML TextInputControl appName, userName, passWord, description;
  @FXML AnchorPane root;
  Password p;

  public NewEntry(Viewer viewer) {
    parent = viewer;
  }

  @Override
  protected String getPath() {
    return "NewEntry.fxml";
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    p = new Password("", "", "", "");
    p.bindPassString(passWord);
    p.togglePass();
    ChangeListener listener =
        new ChangeListener() {
          @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            root.requestFocus();
            appName.focusedProperty().removeListener(this);
          }
        };
    appName.focusedProperty().addListener(listener);
  }

  @FXML
  void OpenGenerateWindow(ActionEvent event) {
    PageManager.getInstance().showPopup(new PasswordGen(p));
  }

  @FXML
  void onAdd(ActionEvent event) {
    String aN = appName.getText();
    String uN = userName.getText();
    String pW = passWord.getText();
    String d = description.getText();
    if (aN.isBlank() || uN.isBlank() || pW.isBlank()) return;
    parent.addEntry(new Password(aN, uN, pW, d));
    close();
  }

  @FXML
  void onCancel(ActionEvent event) {
    close();
  }
}

package com.gtmarshall.pages;

import com.gtmarshall.EncryptionAlgorithms.EncryptionAlgorithm;
import com.gtmarshall.FileManagement.DataSaver;
import com.gtmarshall.FileManagement.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class NewUser extends Popup {

  @FXML TextField userName, passWord;
  @FXML AnchorPane root;

  @Override
  protected String getPath() {
    return "NewUser.fxml";
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ChangeListener listener =
        new ChangeListener() {
          @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            root.requestFocus();
            userName.focusedProperty().removeListener(this);
          }
        };
    userName.focusedProperty().addListener(listener);
  }

  @FXML
  void onAdd(ActionEvent event) {
    String username = userName.getText();
    String password = passWord.getText();
    User user = new User(username, password);
    if (user.hasFile()) {
      close();
      return;
    }
    File f = user.getPath();
    try {
      f.createNewFile();
      EncryptionAlgorithm.setUser(user);
      DataSaver.save(new LinkedList<Entry>());
    } catch (IOException e) {
      e.printStackTrace();
    }
    close();
  }

  @FXML
  void onCancel(ActionEvent event) {
    close();
  }
}

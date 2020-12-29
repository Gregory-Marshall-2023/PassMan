package com.gtmarshall.pages;

import com.gtmarshall.EncryptionAlgorithms.AES;
import com.gtmarshall.EncryptionAlgorithms.EncryptionAlgorithm;
import com.gtmarshall.FileManagement.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class Login extends Page {

  @FXML TextField userNameField;
  @FXML TextField passField;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    EncryptionAlgorithm aes = new AES();
    aes.select();
    EncryptionAlgorithm.setUser(null);
  }

  @FXML
  void login(ActionEvent event) {
    User user = new User(userNameField.getText(), passField.getText());
    login(user);
  }

  private void login(User user) {
    if (user == null) return;
    if (!user.hasFile()) return;
    EncryptionAlgorithm.setUser(user);
    PageManager.getInstance().show(new Viewer());
  }

  @FXML
  void newUser(ActionEvent event) {
    PageManager.getInstance().showPopup(new NewUser());
    if (EncryptionAlgorithm.getUser() != null) login(EncryptionAlgorithm.getUser());
  }

  @Override
  protected String getPath() {
    return "Login.fxml";
  }

  @Override
  protected Pair<Double, Double> getDims() {
    return new Pair<Double, Double>(600d, 400d);
  }
}

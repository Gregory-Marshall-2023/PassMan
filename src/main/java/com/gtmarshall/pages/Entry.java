package com.gtmarshall.pages;

import com.gtmarshall.FileManagement.Password;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class Entry extends Page {
  @FXML private Label userName, appName;
  @FXML private RadioButton selector;
  private Password password;
  private Viewer parent;

  private Entry() {}

  protected Password getPassword() {
    return password;
  }

  protected static Entry entryFactory(Password password, Viewer viewer) {
    Entry e = new Entry();
    e.password = password;
    e.parent = viewer;
    return e;
  }

  @Override
  protected String getPath() {
    return "Entry.fxml";
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (password == null) throw new NullPointerException("Entries cannot exist without passwords");
    password.bindUserName(userName);
    password.bindAppName(appName);
    selector.setSelected(false);
  }

  @FXML
  void select(ActionEvent event) {
    parent.select(this);
  }

  void select() {
    selector.setSelected(true);
  }

  public void deselect() {
    selector.setSelected(false);
    password.unbindAppName(parent.appName);
    password.unbindUserName(parent.userName);
    password.unbindPassword(parent.password);
    password.unbindDescription(parent.description);
  }

  public void togglePass() {
    password.togglePass();
  }

  public void toggleDesc() {
    password.toggleDesc();
  }

  @Override
  public String toString() {
    return password.toString();
  }
}

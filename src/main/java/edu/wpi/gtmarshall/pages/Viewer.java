package edu.wpi.gtmarshall.pages;

import edu.wpi.gtmarshall.FileManagement.DataParser;
import edu.wpi.gtmarshall.FileManagement.DataSaver;
import edu.wpi.gtmarshall.FileManagement.Password;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class Viewer extends Page {
  @FXML VBox entryList;
  @FXML Label appName;
  @FXML TextField userName, password;
  @FXML TextArea description;
  private Entry selected;
  private LinkedList<Entry> entries;

  @Override
  protected String getPath() {
    return "Viewer.fxml";
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    entries = new LinkedList<>();
    LinkedList<Password> passwords = new LinkedList<>();
    Stream<String> entryStrings = DataParser.getEntries();
    if (entryStrings == null) {
      PageManager.getInstance().show(new Login());
      return;
    }
    entryStrings.forEach(string -> passwords.add(Password.parse(string)));
    addEntries(passwords);
  }

  @Override
  public void onClose() {
    DataSaver.save(entries);
  }

  protected void select(Entry e) {
    if (selected != null) selected.deselect();
    selected = e;
    Password p = e.getPassword();
    p.bindUserName(userName);
    p.bindAppName(appName);
    p.bindPassString(password);
    p.bindDescription(description);
    e.select();
  }

  private void addEntries(LinkedList<Password> passwords) {
    addEntries(passwords, true);
  }

  private void addEntries(LinkedList<Password> passwords, @SuppressWarnings("SameParameterValue") boolean allowThreading) {
    if (allowThreading && passwords.size() > 25) addEntriesThreaded(passwords);
    else {
      passwords.forEach(this::addEntry);
    }
  }

  private void addEntriesThreaded(LinkedList<Password> passwords) {
    Task<Void> task =
        new Task<>() {
          Password p;

          @Override
          public Void call() {
            p = passwords.remove();
            addEntry(p);
            try {
              Thread.sleep(Math.min((long) Math.floor(passwords.size() / 100f), 20), 0);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            if (passwords.size() > 0) addEntriesThreaded(passwords);
            return null;
          }

          @Override
          protected void failed() {
            System.out.println("FAILED TO ADD ENTRY");
          }
        };
    Platform.runLater(task);
  }

  protected void addEntry(Password p) {
    addEntry(Entry.entryFactory(p, this));
  }

  private void addEntry(Entry entry) {
    entryList.getChildren().add(PageManager.getInstance().load(entry));
    if (selected == null) select(entry);
    entries.add(entry);
  }

  @Override
  protected Pair<Double, Double> getDims() {
    return new Pair<>(1050d, 700d);
  }

  @FXML
  void togglePass(ActionEvent event) {
    if (selected == null) return;
    selected.togglePass();
  }

  @FXML
  void toggleDesc(ActionEvent event) {
    if (selected == null) return;
    selected.toggleDesc();
  }

  @FXML
  void newPassword(ActionEvent event) {
    PageManager.getInstance().showPopup(new NewEntry(this));
    DataSaver.save(entries);
  }
}

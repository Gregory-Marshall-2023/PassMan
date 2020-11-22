package edu.wpi.gtmarshall.pages;

import edu.wpi.gtmarshall.EncryptionAlgorithms.AES;
import edu.wpi.gtmarshall.EncryptionAlgorithms.EncryptionAlgorithm;
import edu.wpi.gtmarshall.FileManagement.DataParser;
import edu.wpi.gtmarshall.FileManagement.DataSaver;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExamplePage implements Initializable {
  public TextArea outText;
  public TextArea inText;
  public TextField keyText;
  private EncryptionAlgorithm algorithm;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    algorithm = new AES();
  }

  public void encrypt(ActionEvent event) {
    String plainText = inText.getText();
    String key = keyText.getText();
    String cipherText = algorithm.encrypt(plainText, key);
    outText.setText(cipherText);
  }

  public void decrypt(ActionEvent event) {
    String cipherText = inText.getText();
    String key = keyText.getText();
    String plainText = algorithm.decrypt(cipherText, key);
    outText.setText(plainText);
  }

  public void save(ActionEvent event) {
    DataSaver writer = new DataSaver();
    writer.save(outText.getText());
  }

  public void load(ActionEvent event) {
    DataParser parser = new DataParser();
    inText.setText(parser.load());
  }
}

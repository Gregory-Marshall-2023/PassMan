package edu.wpi.gtmarshall;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void init() {}

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("ExamplePage.fxml"));
    primaryStage.setTitle("Example Page");
    Scene s = new Scene(root);
    primaryStage.setScene(s);
    primaryStage.show();
  }
}

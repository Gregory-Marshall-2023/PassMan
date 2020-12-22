package edu.wpi.gtmarshall;

import edu.wpi.gtmarshall.pages.Login;
import edu.wpi.gtmarshall.pages.PageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

  @Override
  public void init() {}

  @Override
  public void start(Stage primaryStage) throws Exception {
    PageManager.getInstance().initialize(primaryStage, new Login());
  }
}

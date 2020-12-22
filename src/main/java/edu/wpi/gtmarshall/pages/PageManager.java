package edu.wpi.gtmarshall.pages;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

public class PageManager {

  Page currentPage;
  Stage stage;
  Scene scene;
  Base root;

  private PageManager() {}

  public void show(Page page) {
    Pair<Double, Double> size = page.getDims();
    if (size.equals(new Pair<>(0d, 0d)))
      throw new RuntimeException("Cannot show page without dimensions");
    currentPage = page;
    Parent parent = load(currentPage);
    scene = new Scene(load(root));
    root.setChild(parent, size);
    root.setOnClose(currentPage::onClose);
    stage.setScene(scene);
    stage.centerOnScreen();
    parent.toBack();
  }

  public Parent load(Page page) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(page.getPath()));
    loader.setController(page);
    try {
      return loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void initialize(Stage primaryStage, Page startingPage) {
    stage = primaryStage;
    scene = new Scene(new AnchorPane());
    root = new Base();
    scene.setRoot(load(root));
    show(startingPage);
    primaryStage.setTitle("Example Page");
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void showPopup(Popup popup) {
    Stage popupwindow = new Stage();
    popup.setStage(popupwindow);
    popupwindow.initModality(Modality.APPLICATION_MODAL);
    popupwindow.setTitle("This is a pop up window");
    Parent parent = load(popup);
    Scene scene = new Scene(parent);
    popupwindow.setScene(scene);
    popupwindow.showAndWait();
    popupwindow.setOnCloseRequest(popup::onClose);
  }

  private static class Singleton {
    private static final PageManager INSTANCE = new PageManager();
  }

  public static PageManager getInstance() {
    return Singleton.INSTANCE;
  }
}

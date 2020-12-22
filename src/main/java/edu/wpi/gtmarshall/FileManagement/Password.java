package edu.wpi.gtmarshall.FileManagement;

import java.util.Random;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;

public class Password {
  private StringProperty appName;
  private StringProperty userName;
  private HiddenString passString;
  private HiddenString description;

  public Password(String appName, String userName, String password, String description) {
    this.appName = new SimpleStringProperty(appName);
    this.userName = new SimpleStringProperty(userName);
    this.passString = new HiddenString(password);
    this.description = new HiddenString(description);
  }

  public static Password parse(String str) {
    if (str.endsWith(":")) str += Character.MIN_VALUE;
    String[] arr = str.split(":");
    if (arr.length != 4) throw new RuntimeException("Input not a valid password:\t" + str);
    if (arr[3].equals(Character.MIN_VALUE)) arr[3] = "";
    return new Password(arr[0], arr[1], arr[2], arr[3]);
  }

  public void bindAppName(Label label) {
    label.textProperty().bind(appName);
  }

  public void bindUserName(TextInputControl control) {
    control.textProperty().bindBidirectional(userName);
  }

  public void bindUserName(Label label) {
    label.textProperty().bind(userName);
  }

  public void bindPassString(TextInputControl control) {
    passString.bind(control);
  }

  public void bindDescription(TextInputControl control) {
    description.bind(control);
  }

  public void togglePass() {
    if (passString.visible.get()) passString.Hide();
    else passString.Show();
  }

  public void toggleDesc() {
    if (description.visible.get()) description.Hide();
    else description.Show();
  }

  public void unbindAppName(Label label) {
    appName.unbindBidirectional(label.textProperty());
  }

  public void unbindUserName(TextInputControl control) {
    userName.unbindBidirectional(control.textProperty());
  }

  public void unbindPassword(TextInputControl control) {
    passString.Hide();
    passString.stringProperty.unbindBidirectional(control.textProperty());
    control.editableProperty().unbind();
  }

  public void unbindDescription(TextInputControl control) {
    description.Hide();
    description.stringProperty.unbindBidirectional(control.textProperty());
    control.editableProperty().unbind();
  }

  public class HiddenString {
    StringProperty stringProperty;
    String actualString;
    BooleanProperty visible;

    private HiddenString(String string) {

      actualString = string;
      stringProperty = new SimpleStringProperty();
      stringProperty.addListener(
          observable -> {
            if (visible.get()) actualString = stringProperty.getValue();
          });
      visible = new SimpleBooleanProperty(false);
      Hide();
    }

    public void Show() {
      stringProperty.setValue(actualString);
      visible.set(true);
    }

    public void Hide() {
      visible.set(false);
      Random r = new Random();
      r.setSeed(System.currentTimeMillis());
      int rand = r.nextInt(1 + (int) Math.ceil((double) actualString.length() / 2d));
      stringProperty.setValue("*".repeat(actualString.length() + rand));
    }

    public void bind(TextInputControl field) {
      field.textProperty().bindBidirectional(stringProperty);
      field.editableProperty().bind(visible);
    }

    protected String get() {
      return actualString;
    }
  }

  @Override
  public String toString() {
    return appName.getValue()
        + ":"
        + userName.getValue()
        + ":"
        + passString.get()
        + ":"
        + description.get();
  }
}

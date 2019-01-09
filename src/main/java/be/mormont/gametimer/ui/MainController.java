package be.mormont.gametimer.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Date: 23-12-18
 * By  : Mormont Romain
 */
public class MainController implements Initializable {
    @FXML public Menu timerMenu;
    @FXML public MenuItem newTimerMenuItem;
    @FXML public MenuItem openTimerMenuItem;
    @FXML public MenuItem saveTimerMenuItem;
    @FXML public MenuItem exitMenuItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timerMenu.setText("Timer");
        newTimerMenuItem.setText("New");
        openTimerMenuItem.setText("Open");
        saveTimerMenuItem.setText("Save");
        exitMenuItem.setText("Exit");
        exitMenuItem.setOnAction(event -> Platform.exit());
    }
}

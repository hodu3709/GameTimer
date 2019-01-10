package be.mormont.gametimer.ui;

import be.mormont.gametimer.Main;
import be.mormont.gametimer.data.Player;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Date: 23-12-18
 * By  : Mormont Romain
 */
public class MainController implements Initializable {
    @FXML public MenuBar bar;
    @FXML public Menu timerMenu;
    @FXML public MenuItem newTimerMenuItem;
    @FXML public MenuItem openTimerMenuItem;
    @FXML public MenuItem saveTimerMenuItem;
    @FXML public MenuItem exitMenuItem;
    @FXML public HBox timersBox;

    private ObservableList<Player> players;
    private ObservableList<TimerViewController> controllers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timerMenu.setText("Timer");
        newTimerMenuItem.setText("New");
        newTimerMenuItem.setOnAction(event -> {
            Pair<Parent, CreateTimersFormController> pc = FXMLModalHelper.popModal("/be/mormont/gametimer/ui/create-timer-form.fxml", bar.getScene().getWindow());
            pc.getValue().setHandler(players -> this.players.setAll(players));
        });
        openTimerMenuItem.setText("Open");
        saveTimerMenuItem.setText("Save");
        exitMenuItem.setText("Exit");
        exitMenuItem.setOnAction(event -> Platform.exit());

        players = FXCollections.observableArrayList();
        players.addListener((ListChangeListener<Player>) c -> {
            timersBox.getChildren().clear();
            ArrayList<TimerViewController> controllers = new ArrayList<>();
            for (Player p: players) {
                Pair<Parent, TimerViewController> pc = FXMLBuilder.build("/be/mormont/gametimer/ui/timer-view.fxml");
                pc.getKey().getStylesheets().add(Main.getCssPath());
                timersBox.getChildren().add(pc.getKey());
                controllers.add(pc.getValue());
                pc.getValue().setPlayer(p);
            }
            this.controllers = FXCollections.observableArrayList(controllers);
        });
    }
}

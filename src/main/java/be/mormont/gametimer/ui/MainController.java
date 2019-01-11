package be.mormont.gametimer.ui;

import be.mormont.gametimer.Main;
import be.mormont.gametimer.data.Player;
import be.mormont.gametimer.timer.MultiTimer;
import be.mormont.gametimer.timer.Timer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    @FXML public Button pauseAllButton;
    @FXML public Button startAllButton;

    private ObservableList<Player> players;
    private ObservableList<TimerViewController> controllers;
    private MultiTimer multiTimer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timerMenu.setText("Timer");
        newTimerMenuItem.setText("New");
        newTimerMenuItem.setOnAction(event -> {
            Pair<Parent, CreateTimersFormController> pc = FXMLModalHelper.popModal("/be/mormont/gametimer/ui/create-timer-form.fxml", bar.getScene().getWindow());
            pc.getValue().setHandler(players -> {
                this.players.setAll(players);
                resetPlayers();
            });
        });
        openTimerMenuItem.setText("Open");
        saveTimerMenuItem.setText("Save");
        exitMenuItem.setText("Exit");
        exitMenuItem.setOnAction(event -> Platform.exit());

        players = FXCollections.observableArrayList();
        controllers = FXCollections.observableArrayList();

    }

    private void resetView() {
        timersBox.getChildren().clear();
        controllers.clear();
        pauseAllButton.setDisable(true);
        startAllButton.setDisable(false);
    }

    private void resetPlayers() {
        if (players.size() == 0) {
            return;
        }
        resetView();

        // create multi timer
        Timer[] timers = new Timer[players.size()];
        int selected = new Random().nextInt(players.size());
        players.stream().map(Player::getTimer).collect(Collectors.toList()).toArray(timers);
        multiTimer = new MultiTimer(selected, timers);

        for (int i = 0; i < players.size(); ++i) {
            Pair<Parent, TimerViewController> pc = FXMLBuilder.build("/be/mormont/gametimer/ui/timer-view.fxml");
            Parent parent = pc.getKey();
            TimerViewController controller = pc.getValue();

            // attache component
            parent.getStylesheets().add(Main.getCssPath());
            timersBox.getChildren().add(parent);

            // set up controller
            controllers.add(controller);
            controller.setPlayer(players.get(i));
            controller.disabledProperty().bind(multiTimer.selectedProperty().isNotEqualTo(i));
            timers[i] = players.get(i).getTimer();
        }
        // to make sure components are update
        controllers = FXCollections.observableArrayList(controllers);
    }
}

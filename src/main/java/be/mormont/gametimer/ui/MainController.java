package be.mormont.gametimer.ui;

import be.mormont.gametimer.Main;
import be.mormont.gametimer.data.Player;
import be.mormont.gametimer.timer.MultiTimer;
import be.mormont.gametimer.timer.Timer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;
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
    @FXML public Button nextButton;
    @FXML public Label currentPlayerLabel;
    @FXML public AnchorPane root;

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

        pauseAllButton.setText("Pause");
        pauseAllButton.setOnMouseClicked(event -> { if (multiTimer != null) multiTimer.stop(); });
        startAllButton.setText("Play");
        startAllButton.setOnMouseClicked(event -> { if (multiTimer != null) multiTimer.start(); });
        nextButton.setText("Next");
        nextButton.setOnMouseClicked(event -> { if (multiTimer != null) multiTimer.next(); });
        nextButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

            }
        });
    }

    private void resetView() {
        timersBox.getChildren().clear();
        controllers.clear();
    }

    private void resetPlayers() {
        if (players.size() == 0) {
            return;
        }
        resetView();

        // create multi timer
        List<Timer> timersList = players.stream().map(Player::getTimer).collect(Collectors.toList());
        Timer[] timers = timersList.toArray(new Timer[players.size()]);
        int selected = 0;
        multiTimer = new MultiTimer(selected, timers);
        currentPlayerLabel.setText(players.get(selected).getPlayerName());
        multiTimer.selectedProperty().addListener((observable, oldValue, newValue) -> {
            currentPlayerLabel.setText(players.get(newValue.intValue()).getPlayerName());
        });

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
            final int currIndex = i;
            controller.getMoveLeftButton().setDisable(i == 0);
            controller.getMoveLeftButton().setOnMouseClicked(event -> swapPlayers(currIndex, currIndex-1));
            controller.getMoveRightButton().setDisable(i == players.size() - 1);
            controller.getMoveRightButton().setOnMouseClicked(event -> swapPlayers(currIndex, currIndex+1));
            controller.disabledProperty().bind(multiTimer.selectedProperty().isNotEqualTo(i));
            timers[i] = players.get(i).getTimer();
        }
        // to make sure components are update
        controllers = FXCollections.observableArrayList(controllers);

        BooleanExpression startableExpression = timers[0].stoppedProperty();
        BooleanExpression stoppableExpression = timers[0].stoppedProperty().not();

        for (int i = 1; i < timers.length; ++i) {
            startableExpression = startableExpression.and(timers[i].stoppedProperty());
            stoppableExpression = stoppableExpression.or(timers[i].stoppedProperty().not());
        }

        startAllButton.disableProperty().bind(startableExpression.not());
        pauseAllButton.disableProperty().bind(stoppableExpression.not());
    }

    public void swapPlayers(int index1, int index2) {
        Player tmp = players.get(index1);
        players.set(index1, players.get(index2));
        players.set(index2, tmp);
        resetPlayers();
    }

    public EventHandler<KeyEvent> getKeyPressedEvent() {
        return event -> {
            if (multiTimer == null) {
                return;
            }
            if (event.getCode() == KeyCode.RIGHT) {
                multiTimer.next();
                event.consume();
            } else if (event.getCode() == KeyCode.LEFT) {
                multiTimer.prev();
                event.consume();
            } else if (event.getCode() == KeyCode.SPACE) {
                multiTimer.invertMode();
                event.consume();
            }
        };
    }

}

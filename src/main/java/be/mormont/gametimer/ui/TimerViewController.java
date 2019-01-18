package be.mormont.gametimer.ui;

import be.mormont.gametimer.ColorUtil;
import be.mormont.gametimer.data.Player;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Date: 09-01-19
 * By  : Mormont Romain
 */
public class TimerViewController implements Initializable {
    @FXML public Button playButton;
    @FXML public Button pauseButton;
    @FXML public Label timerLabel;
    //@FXML public Rectangle colorRectangle;
    @FXML public AnchorPane pane;
    @FXML public Label timerNameLabel;
    @FXML public Button moveLeftButton;
    @FXML public Button moveRightButton;

    private BooleanProperty disabled;
    private AnimationTimer refreshTimer;
    private Player player;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (player == null) {
            player = new Player();
        }
        disabled = new SimpleBooleanProperty(false);
        updateButtonsState(player.getTimer().isStopped());
        changePlayer();
    }

    public void setPlayer(Player player) {
        this.player = player;
        changePlayer();
    }

    private void changePlayer() {
        // color
        updateColor(player.getColor());
        player.colorProperty().addListener((observable, oldValue, newValue) -> {
            updateColor(player.getColor());
        });

        // timer name
        timerNameLabel.textProperty().bindBidirectional(player.playerNameProperty());

        // buttons
        playButton.setText("Play");
        pauseButton.setText("Pause");
        playButton.disableProperty().bind(disabled.or(player.getTimer().stoppedProperty().not()));
        pauseButton.disableProperty().bind(disabled.or(player.getTimer().stoppedProperty()));
        playButton.setOnMouseClicked(event -> { if (!player.getTimer().isStopped()) return; player.getTimer().start(); });
        pauseButton.setOnMouseClicked(event -> { if (player.getTimer().isStopped()) return; player.getTimer().stop(); });

        // display time
        refreshTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timerLabel.setText(player.getTimer().format());
            }
        };
        refreshTimer.start();
    }

    private void updateButtonsState(boolean timerStopped) {
        playButton.setDisable(!timerStopped || disabled.getValue());
        pauseButton.setDisable(timerStopped || disabled.getValue());
    }

    public BooleanProperty disabledProperty() {
        return disabled;
    }

    private void updateColor(Color newColor) {
        String hexColor = ColorUtil.hexCode(newColor);
        String hexFill = ColorUtil.hexCode(ColorUtil.getTextFill(newColor));
        String textFillStyle = "-fx-text-fill: " + hexFill + ";";
        pane.setStyle("-fx-background-color: " + hexColor + ";");
        timerLabel.setStyle(textFillStyle);
        timerNameLabel.setStyle(textFillStyle);
        //colorRectangle.setFill(newColor);
    }

    public Button getMoveLeftButton() {
        return moveLeftButton;
    }

    public Button getMoveRightButton() {
        return moveRightButton;
    }
}

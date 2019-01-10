package be.mormont.gametimer.ui;

import be.mormont.gametimer.data.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

/**
 * Date: 10-01-19
 * By  : Mormont Romain
 */
public class PlayerListViewCellController {

    @FXML public Rectangle colorRectangle;
    @FXML public Label playerNameLabel;

    public void setPlayer(Player player) {
        colorRectangle.setFill(player.getColor());
        player.colorProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                colorRectangle.setFill(newValue);
            }
        });
        playerNameLabel.textProperty().bind(player.playerNameProperty());
    }
}

package be.mormont.gametimer.data;

import be.mormont.gametimer.timer.CountUpTimer;
import be.mormont.gametimer.timer.Timer;
import javafx.beans.property.*;
import javafx.scene.paint.Color;


/**
 * Date: 10-01-19
 * By  : Mormont Romain
 */
public class Player {
    private Property<Color> color;
    private Timer timer;
    private StringProperty playerName;

    public Player(Timer timer, String name, Color color) {
        this.timer = timer;
        this.playerName = new SimpleStringProperty(name);
        this.color = new SimpleObjectProperty<>(color);
    }

    public Player(String name) {
        this(new CountUpTimer(), name, Color.WHITE);
    }

    public Player() {
        this("player");
    }

    public Color getColor() {
        return colorProperty().getValue();
    }

    public void setColor(Color color) {
        colorProperty().setValue(color);
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public String getPlayerName() {
        return playerName.get();
    }

    public StringProperty playerNameProperty() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }

    public Property<Color> colorProperty() {
        return color;
    }
}

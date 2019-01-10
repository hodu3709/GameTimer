package be.mormont.gametimer.data;

import be.mormont.gametimer.timer.CountUpTimer;
import be.mormont.gametimer.timer.Timer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;


/**
 * Date: 10-01-19
 * By  : Mormont Romain
 */
public class Player {
    private Color color;
    private Timer timer;
    private StringProperty playerName;

    public Player(Timer timer, String name, Color color) {
        this.timer = timer;
        this.playerName = new SimpleStringProperty(name);
        this.color = color;
    }

    public Player() {
        this(new CountUpTimer(), "player", Color.WHITE);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
}

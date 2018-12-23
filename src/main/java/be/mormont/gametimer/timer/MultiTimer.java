package be.mormont.gametimer.timer;

import be.mormont.gametimer.exceptions.TimerStateException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Date: 23-12-18
 * By  : Mormont Romain
 */
public class MultiTimer {
    private ObservableList<Timer> timers;
    private SimpleIntegerProperty selected;

    public MultiTimer(Timer ... timers) {
        this(0, timers);
    }

    public MultiTimer(int selected, Timer ... timers) {
        this.timers = FXCollections.observableArrayList();
        this.timers.addAll(timers);
        this.selected = new SimpleIntegerProperty(selected);
    }

    /**
     * Starts the current timer
     */
    public void start() {
        timers.get(selected.getValue()).start();
    }

    /**
     * Select another timer (and stops the current one if it is not stopped)
     * @param index The index of the timer to select
     */
    public void select(int index) {
        if (index < 0 || index >= timers.size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        try {
            this.timers.get(index).stop();
        } catch (TimerStateException e) {
            // skip
        } finally {
            selected.setValue(index);
        }
    }

    /**
     * Stops the current timer
     */
    public void stop() {
        timers.get(selected.getValue()).stop();
    }

    /**
     * Stops current timer, and starts the next one
     */
    public void next() {
        select((selected.getValue() + 1) % timers.size());
        start();
    }
}

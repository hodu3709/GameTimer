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
        switchTo((selected.getValue() + 1) % timers.size());
    }

    /**
     * Stops current timer, and starts the prev one
     */
    public void prev() {
        int prev = selected.getValue() - 1;
        switchTo(prev < 0 ? timers.size() - 1 : prev);
    }

    /**
     * Start the multi timer if it is stopped, stops it otherwise.
     */
    public void invertMode() {
        if (selectedTimer().isStopped()) {
            start();
        } else {
            stop();
        }
    }

    /**
     * Switch from the selected timer to another
     * @param i Index of the other timer
     */
    private void switchTo(int i) {
        if (i < 0 || i >= timers.size()) {
            throw new IndexOutOfBoundsException("This timer does not exist (#" + (i + 1) + " among " + timers.size() + " timers).");
        } else if (selected.getValue() == i) {
            return;
        }
        boolean wasRunning = !selectedTimer().isStopped();
        if (wasRunning) {
            stop();
        }
        select(i);
        if (wasRunning) {
            start();
        }
    }

    public SimpleIntegerProperty selectedProperty() {
        return selected;
    }

    private Timer selectedTimer() {
        return timers.get(selected.getValue());
    }
}

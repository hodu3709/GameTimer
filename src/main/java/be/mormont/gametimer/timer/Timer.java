package be.mormont.gametimer.timer;

import be.mormont.gametimer.exceptions.TimerStateException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Date: 23-12-18
 * By  : Mormont Romain
 */
public abstract class Timer {
    protected ObjectProperty<Duration> duration;
    protected ObjectProperty<LocalDateTime> start;
    protected BooleanProperty stopped;

    /**
     * Create a stopped timer with initial duration set to 0.
     * @param initial Initial duration
     */
    public Timer(Duration initial) {
        duration = new SimpleObjectProperty<>(initial);
        start = new SimpleObjectProperty<>();
        stopped = new SimpleBooleanProperty(true);
    }

    public Timer() {
        this(Duration.ZERO);
    }

    /**
     * Starts the timer (starts counting time elapsing)
     */
    public abstract void start();

    /**
     * Stops the timer (account the time spent since the last start)
     */
    public void stop() {
        if (!isStopped()) {
            duration.setValue(getTotalDuration());
            stopped.setValue(true);
        } else {
            throw new TimerStateException("timer has already been stopped");
        }
    }

    /**
     * Return the total elapsed duration of the timer
     * @return The duration
     */
    protected Duration getTotalDuration() {
        if (isStopped()) {
            return duration.getValue();
        } else {
            return duration.getValue().plus(Duration.between(start.getValue(), LocalDateTime.now()));
        }
    }

    /**
     * Return the duration as meaningful for a human
     * @return The Duration to display
     */
    public abstract Duration humanDuration();

    /**
     * Whether or not the timer has expired
     * @return True if the timer has expired, false otherwise
     */
    public abstract boolean isExpired();

    /**
     * Format the duration to be human readable. Negative duration means that the timer has expired.
     * @return Formatted time
     */
    public String format() {
        Duration d = humanDuration();
        if (isExpired()) {
            return "0:00:00:0.000";
        } else {
            return String.format("%d:%02d:%02d.%03d", d.toHours(), d.toMinutes() % 60, d.getSeconds() % 60, d.toMillis() % 1000);
        }
    }

    public Duration getDuration() {
        return duration.get();
    }

    public ObjectProperty<Duration> durationProperty() {
        return duration;
    }

    public boolean isStopped() {
        return stopped.getValue();
    }

    public BooleanProperty stoppedProperty() {
        return stopped;
    }
}

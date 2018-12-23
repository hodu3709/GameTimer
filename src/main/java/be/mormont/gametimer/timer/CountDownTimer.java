package be.mormont.gametimer.timer;


import be.mormont.gametimer.exceptions.TimerExpiredException;
import be.mormont.gametimer.exceptions.TimerStateException;
import com.sun.corba.se.impl.naming.namingutil.CorbalocURL;
import javafx.beans.property.SimpleObjectProperty;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Date: 23-12-18
 * By  : Mormont Romain
 */
public class CountDownTimer extends Timer {

    private SimpleObjectProperty<Duration> initial;
    /**
     * Allocated time for the timer (i.e. time to spend before it runs out of time)
     * @param initial Allocated duration
     */
    public CountDownTimer(Duration initial) {
        super();
        this.initial = new SimpleObjectProperty<>(initial);
    }

    @Override
    public void start() {
        if (isStopped()) {
            if(initial.getValue().minus(duration.getValue()).isNegative()) {
                throw new TimerExpiredException();
            }
            start.setValue(LocalDateTime.now());
            stopped.setValue(false);
        } else {
            throw new TimerStateException("timer has already been started");
        }
    }

    @Override
    public Duration humanDuration() {
        return null;
    }

    @Override
    public boolean isExpired() {
        return false;
    }
}

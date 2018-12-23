package be.mormont.gametimer.timer;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Date: 23-12-18
 * By  : Mormont Romain
 */
public class CountUpTimer extends Timer {

    @Override
    public void start() {
        if (isStopped()) {
            start.setValue(LocalDateTime.now());
            stopped.setValue(false);
        }
    }

    @Override
    public Duration humanDuration() {
        return getTotalDuration();
    }

    @Override
    public boolean isExpired() {
        return false; // count up timer is not expired
    }
}

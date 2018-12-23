package be.mormont.gametimer.exceptions;

/**
 * Date: 23-12-18
 * By  : Mormont Romain
 */
public class TimerStateException extends RuntimeException {
    public TimerStateException() { super(); }
    public TimerStateException(String s) { super(s); }
    public TimerStateException(Throwable t) { super(t); }
}

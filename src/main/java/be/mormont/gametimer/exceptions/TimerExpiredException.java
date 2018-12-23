package be.mormont.gametimer.exceptions;

/**
 * Date: 23-12-18
 * By  : Mormont Romain
 */
public class TimerExpiredException extends RuntimeException {
    public TimerExpiredException() { super(); }
    public TimerExpiredException(String s) { super(s); }
    public TimerExpiredException(Throwable t) { super(t); }
}
package exceptions;

public class EmptyRideQueueException extends RuntimeException {
    public EmptyRideQueueException(String message) {
        super(message);
    }
}

package exceptions;

public class MissingOperatorException extends RuntimeException {
    public MissingOperatorException(String message) {
        super(message);
    }
}

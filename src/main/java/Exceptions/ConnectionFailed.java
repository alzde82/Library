package Exceptions;

public class ConnectionFailed extends RuntimeException {
    public ConnectionFailed(String message) {
        super(message);
    }
}

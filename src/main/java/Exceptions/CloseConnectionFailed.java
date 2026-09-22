package Exceptions;

public class CloseConnectionFailed extends RuntimeException{
    public CloseConnectionFailed (String message){
        super(message);
    }
}

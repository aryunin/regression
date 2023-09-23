package exception;

public class EmptyArrayException extends InvalidDataException {
    public EmptyArrayException(String message) {
        super(message, 2);

        handler.handle(this);
    }
}

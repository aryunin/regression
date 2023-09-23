package exception;

public class DifferentArraySizesException extends InvalidDataException {
    public DifferentArraySizesException(String message) {
        super(message, 1);

        handler.handle(this);
    }
}

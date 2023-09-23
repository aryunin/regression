package exception;

import exception.handler.GlobalExceptionHandler;

public abstract class InvalidDataException extends RuntimeException {
    protected final GlobalExceptionHandler handler = GlobalExceptionHandler.getInstance();

    protected final int STATUS_CODE;

    public InvalidDataException(String message, int STATUS_CODE) {
        super(message);
        this.STATUS_CODE = STATUS_CODE;
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}

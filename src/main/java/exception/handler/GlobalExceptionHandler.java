package exception.handler;

import exception.DifferentArraySizesException;
import exception.EmptyArrayException;

public final class GlobalExceptionHandler implements ExceptionHandler {
    private final static GlobalExceptionHandler instance = new GlobalExceptionHandler();

    public static GlobalExceptionHandler getInstance() {
        return instance;
    }

    @Override
    public synchronized void handle(EmptyArrayException e) {
        System.out.println(EmptyArrayException.class.getName() + ": " + e.getMessage());
        System.exit(e.getStatusCode());
    }

    @Override
    public synchronized void handle(DifferentArraySizesException e) {
        System.out.println(DifferentArraySizesException.class.getName() + ": " + e.getMessage());
        System.exit(e.getStatusCode());
    }
}

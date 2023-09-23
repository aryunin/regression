package exception.handler;

import exception.DifferentArraySizesException;
import exception.EmptyArrayException;

public interface ExceptionHandler {
    default void handle(EmptyArrayException e) {}
    default void handle(DifferentArraySizesException e) {}
}

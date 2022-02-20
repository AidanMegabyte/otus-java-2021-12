package ru.atm.exception;

/**
 * Ошибка банкомата
 */
public class AtmException extends RuntimeException {
    public AtmException(String message) {
        super(message);
    }
}

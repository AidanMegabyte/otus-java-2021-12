package ru.atm.exception;

/**
 * Ошибка "недостаточно средств в банкомате"
 */
public class NotEnoughCashException extends RuntimeException {
    public NotEnoughCashException(String message) {
        super(message);
    }
}

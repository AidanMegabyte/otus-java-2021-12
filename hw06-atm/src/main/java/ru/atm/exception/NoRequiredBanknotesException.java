package ru.atm.exception;

/**
 * Ошибка "нет подходящих купюр в банкомате"
 */
public class NoRequiredBanknotesException extends RuntimeException {
    public NoRequiredBanknotesException(String message) {
        super(message);
    }
}
